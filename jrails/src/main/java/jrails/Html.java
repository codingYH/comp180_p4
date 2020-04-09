package jrails;

public class Html {
    private String html;

    public Html(){
        html = "";
    }

    public Html(String h){
        html = h;
    }

    public String toString() {
        return html;
    }

    public Html empty() {
        return new Html();
    }

    public Html seq(Html h) {
        StringBuffer s = new StringBuffer(this.html);
        s.append(h.html);
        return new Html(new String(s));
    }

    public Html br() {
        StringBuffer s = new StringBuffer(this.html);
        s.append("<br/>");
        return new Html(new String(s));
    }

    public Html t(Object o) {
        StringBuffer s = new StringBuffer(this.html);
        s.append(o.toString());
        return new Html(new String(s));
    }

    public Html p(Html child) {
        return new Html(selfClose("p", child.html));
    }

    public Html div(Html child) {
        return new Html(selfClose("div", child.html));
    }

    public Html strong(Html child) {
        return new Html(selfClose("strong", child.html));
    }

    public Html h1(Html child) {
        return new Html(selfClose("h1", child.html));
    }

    public Html tr(Html child) {
        return new Html(selfClose("tr", child.html));
    }

    public Html th(Html child) {
        return new Html(selfClose("th", child.html));
    }

    public Html td(Html child) {
        return new Html(selfClose("td", child.html));
    }

    public Html table(Html child) {
        return new Html(selfClose("table", child.html));
    }

    public Html thead(Html child) {
        return new Html(selfClose("thead", child.html));
    }

    public Html tbody(Html child) {
        return new Html(selfClose("tbody", child.html));
    }

    public Html textarea(String name, Html child) {
        StringBuffer s = new StringBuffer(this.html);
        s.append("<textarea name=\"" + name + "\">");
        s.append(child.html);
        s.append("</textarea>");
        return new Html(new String(s));
    }

    public Html link_to(String text, String url) {
        StringBuffer s = new StringBuffer(this.html);
        s.append("<a href=\"" + url + "\">");
        s.append(text);
        s.append("</a>");
        return new Html(new String(s));
    }

    public Html form(String action, Html child) {
        //<form action="/create" accept-charset="UTF-8" method="post">HTML</form>
        StringBuffer s = new StringBuffer(this.html);
        s.append("<form action=\"" + action + "\" accept-charset=\"UTF-8\" method=\"post\">");
        s.append(child.html);
        s.append("</form>");
        return new Html(new String(s));
    }

    public Html submit(String value) {
        //<input type="submit" value="Save"/>.
        StringBuffer s = new StringBuffer(this.html);
        s.append("<input type=\"submit\" value=\"" + value + "\"/>");
        return new Html(new String(s));
    }

    private String selfClose(String type, String content){
        StringBuffer s = new StringBuffer(this.html);
        s.append("<"+type+">");
        s.append(content);
        s.append("</"+type+">");
        return new String(s);
    }
}