package jrails;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Model {
    private final static String COMMA_REPLACE = "$";
    private static String DB_FILE;
    @Column
    public int id = 0;

    public Model(){
        DB_FILE =  this.getClass().getSimpleName() + ".txt" ;
    }

    public void save() {
        if (id == 0) {
            id();
            StringBuffer sb;
            sb = getFieldsValue();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(DB_FILE, true));
                writer.write(new String(sb));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            updateDB(false);
        }
    }

    public int id() {
        if (id != 0) {
            return id;
        } else {
            UUID uuid = UUID.randomUUID();
            id = uuid.hashCode();
            return id;
        }
    }

    public static <T> T find(Class<T> c, int id) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(DB_FILE));
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                int d = Integer.parseInt(columns[columns.length - 1]);
                if (d != id) {
                    continue;
                } else {
                    return createInstance(c, columns);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> all(Class<T> c) {
        try {
            List<T> objList = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(DB_FILE));
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                objList.add(createInstance(c, columns));
                }
            return objList;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void destroy() {
        updateDB(true);
    }

    public static void reset() {
        try{
        BufferedWriter writer = new BufferedWriter(new FileWriter(DB_FILE));
        writer.write("");
        writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    private StringBuffer getFieldsValue() {
        StringBuffer sb = new StringBuffer();
//        sb.append(id + ",");
        Field[] fields = this.getClass().getFields();
        for (Field f : fields) {
            if (f.getAnnotation(Column.class) != null) {
                if (f.getType() == String.class || f.getType() == int.class || f.getType() == boolean.class) {
                    try {
                        Object value = f.get(this);
                        sb.append(value == null ? "null" : value.toString().replace(",", COMMA_REPLACE));
                        sb.append(",");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else
                    throw new NoSuchElementException("The only possible types for @Column fields are String, int, and boolean.");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(System.getProperty("line.separator"));
        return sb;
    }

    private static <T> T createInstance(Class<T> c, String[] columns) {
        try {
            Constructor constructor = c.getConstructor();
            T obj = (T) constructor.newInstance();
            Field[] fields = c.getFields();
            for (int i = 0; i < fields.length; i++) {
                if(fields[i].getAnnotation(Column.class) == null)
                    continue;
                else {
                    Class type = fields[i].getType();
                    if (type == int.class) {
                        fields[i].set(obj, Integer.parseInt(columns[i]));
                    } else if (type == String.class) {
                        if(columns[i].equals("null")){
                            fields[i].set(obj, null);
                        }else
                            fields[i].set(obj, columns[i].replace(COMMA_REPLACE, ","));
                    } else {
                        fields[i].set(obj, Boolean.parseBoolean(columns[i]));
                    }
                }
            }
            return obj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void updateDB(boolean isDestroy){
        try {
            boolean discover = false;
            StringBuffer sb;
            BufferedReader br = new BufferedReader(new FileReader(DB_FILE));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                int d = Integer.parseInt(columns[columns.length - 1]);
                if (d != id) {
                    inputBuffer.append(line);
                    inputBuffer.append(System.getProperty("line.separator"));
                } else {
                    if (isDestroy == false) {
                        sb = getFieldsValue();
                        inputBuffer.append(sb);
                        discover = true;
                    }else {
                        discover = true;
                    }
                }
            }
            if(discover == false)
                throw new NoSuchElementException("This instance is not in DB!");
           else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(DB_FILE));
                writer.write(new String(inputBuffer));
                writer.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}