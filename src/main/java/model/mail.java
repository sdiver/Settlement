package model;

import java.util.Date;
import java.util.List;

public class mail {
    private int id;
    private String mail_uuid;
    private Date create_time;
    private String title;
    private String context;
    private List<appendix> appendixList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail_uuid() {
        return mail_uuid;
    }

    public void setMail_uuid(String mail_uuid) {
        this.mail_uuid = mail_uuid;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<appendix> getAppendixList() {
        return appendixList;
    }

    public void setAppendixList(List<appendix> appendixList) {
        this.appendixList = appendixList;
    }
}
