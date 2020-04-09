package jrails;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JRouter {
    HashMap<String, Route> map = new HashMap<>();

    public void addRoute(String verb, String path, Class clazz, String method) {
        map.put(verb+path, new Route(verb, path,clazz,method));
    }

    // Returns "clazz#method" corresponding to verb+URN
    // Null if no such route
    public String getRoute(String verb, String path) {
        if(map.containsKey(verb+path)){
            return map.get(verb+path).clazz.getSimpleName()+"#"+map.get(verb+path).method;
        }else
            return null;
    }

    // Call the appropriate controller method and
    // return the result
    public Html route(String verb, String path, Map<String, String> params) {
        if(map.containsKey(verb+path)){
            Class clazz =  map.get(verb+path).clazz;
            String method = map.get(verb+path).method;
            try {
                Method m = clazz.getMethod(method, Map.class);
                try {
                    Object html = m.invoke(null, params);
                    return (Html)html;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }else
            throw new UnsupportedOperationException();
        return null;
    }

    public class Route{
        String verb;
        String path;
        Class clazz;
        String method;
        public Route(String v, String p, Class c, String m){
            verb = v;
            path = p;
            clazz = c;
            method = m;
        }
    }

}
