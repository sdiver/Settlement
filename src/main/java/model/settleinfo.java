package model;

import java.util.Date;

public class settleinfo {
    private String rowno;
    private String case_code;
    private int heading;
    private String caseType;
    private String town;
    private String village;
    private Date case_create;
    private String case_reporter;
    private int case_status;
    private String caseSchedule;
    private String caseStatus;
    private String zip_url;
    private int sum_pay;
    private String headingConcat;

    public String getHeadingConcat() {
        return headingConcat;
    }

    public void setHeadingConcat(String headingConcat) {
        this.headingConcat = headingConcat;
    }

    public String getRowno() {
        return rowno;
    }

    public void setRowno(String rowno) {
        this.rowno = rowno;
    }

    public String getCase_code() {
        return case_code;
    }

    public void setCase_code(String case_code) {
        this.case_code = case_code;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public Date getCase_create() {
        return case_create;
    }

    public void setCase_create(Date case_create) {
        this.case_create = case_create;
    }

    public String getCase_reporter() {
        return case_reporter;
    }

    public void setCase_reporter(String case_reporter) {
        this.case_reporter = case_reporter;
    }

    public int getCase_status() {
        return case_status;
    }

    public void setCase_status(int case_status) {
        this.case_status = case_status;
    }

    public String getCaseSchedule() {
        return caseSchedule;
    }

    public void setCaseSchedule(String caseSchedule) {
        this.caseSchedule = caseSchedule;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getZip_url() {
        return zip_url;
    }

    public void setZip_url(String zip_url) {
        this.zip_url = zip_url;
    }

    public int getSum_pay() {
        return sum_pay;
    }

    public void setSum_pay(int sum_pay) {
        this.sum_pay = sum_pay;
    }
}
