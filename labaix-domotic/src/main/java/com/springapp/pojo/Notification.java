package com.springapp.pojo;

/**
 * Created by nicolas on 02/03/15.
 */
public class Notification {

    private String content;

    public Notification(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        if (this.content != null ? !this.content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.content != null ? this.content.hashCode() : 0;
    }

    public String getContent() {
        return content;
    }
}
