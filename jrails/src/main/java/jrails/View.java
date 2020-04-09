package jrails;

public class View {
    public static Html empty() {
        return new Html( );
    }

    public static Html br() {
        Html h = new Html( );
        return h.br();
    }

    public static Html t(Object o) {
        Html h = new Html( );
        return h.t(o);
    }

    public static Html p(Html child) {
        Html h = new Html( );
        return h.p(child);
    }

    public static Html div(Html child) {
        Html h = new Html( );
        return h.div(child);
    }

    public static Html strong(Html child) {
        Html h = new Html( );
        return h.strong(child);
    }

    public static Html h1(Html child) {
        Html h = new Html( );
        return h.h1(child);
    }

    public static Html tr(Html child) {
        Html h = new Html( );
        return h.tr(child);
    }

    public static Html th(Html child) {
        Html h = new Html( );
        return h.th(child);
    }

    public static Html td(Html child) {
        Html h = new Html( );
        return h.td(child);
    }

    public static Html table(Html child) {
        Html h = new Html( );
        return h.table(child);
    }

    public static Html thead(Html child) {
        Html h = new Html( );
        return h.thead(child);
    }

    public static Html tbody(Html child) {
        Html h = new Html( );
        return h.tbody(child);
    }

    public static Html textarea(String name, Html child) {
        Html h = new Html( );
        return h.textarea(name, child);
    }

    public static Html link_to(String text, String url) {
        Html h = new Html( );
        return h.link_to(text, url);
    }

    public static Html form(String action, Html child) {
        Html h = new Html( );
        return h.form(action, child);
    }

    public static Html submit(String value) {
        Html h = new Html( );
        return h.submit(value);
    }
}