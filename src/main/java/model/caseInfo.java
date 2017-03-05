package model;

import java.util.Date;
import java.util.List;

public class caseInfo {
    private int case_code;
    private String case_forensics;
    private Date case_create;
    private String cse_address;
    private Date case_time;
    private String case_reporter;
    private int case_cellphone_number;
    private int case_upload_user;
    private String user_ch_name;
    private int phone_number;
    private int case_status;
    private String caseSchedule;
    private String caseStatus;
    private int sum_pay;
    private String town;
    private String village;
    private int is_household;
    private int is_owner;
    private int ifAuthority;
    private List<forensicsInfo> forensicsInfoList;
    private String zip_url;

    public String getZip_url() {
        return zip_url;
    }

    public void setZip_url(String zip_url) {
        this.zip_url = zip_url;
    }

    public int getIfAuthority() {
        return ifAuthority;
    }

    public void setIfAuthority(int ifAuthority) {
        this.ifAuthority = ifAuthority;
    }

    public int getCase_code() {
        return case_code;
    }

    public void setCase_code(int case_code) {
        this.case_code = case_code;
    }

    public String getCase_forensics() {
        return case_forensics;
    }

    public void setCase_forensics(String case_forensics) {
        this.case_forensics = case_forensics;
    }

    public Date getCase_create() {
        return case_create;
    }

    public void setCase_create(Date case_create) {
        this.case_create = case_create;
    }

    public String getCse_address() {
        return cse_address;
    }

    public void setCse_address(String cse_address) {
        this.cse_address = cse_address;
    }

    public Date getCase_time() {
        return case_time;
    }

    public void setCase_time(Date case_time) {
        this.case_time = case_time;
    }

    public String getCase_reporter() {
        return case_reporter;
    }

    public void setCase_reporter(String case_reporter) {
        this.case_reporter = case_reporter;
    }

    public int getCase_cellphone_number() {
        return case_cellphone_number;
    }

    public void setCase_cellphone_number(int case_cellphone_number) {
        this.case_cellphone_number = case_cellphone_number;
    }

    public int getCase_upload_user() {
        return case_upload_user;
    }

    public void setCase_upload_user(int case_upload_user) {
        this.case_upload_user = case_upload_user;
    }

    public String getUser_ch_name() {
        return user_ch_name;
    }

    public void setUser_ch_name(String user_ch_name) {
        this.user_ch_name = user_ch_name;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
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

    public int getSum_pay() {
        return sum_pay;
    }

    public void setSum_pay(int sum_pay) {
        this.sum_pay = sum_pay;
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

    public int getIs_household() {
        return is_household;
    }

    public void setIs_household(int is_household) {
        this.is_household = is_household;
    }

    public int getIs_owner() {
        return is_owner;
    }

    public void setIs_owner(int is_owner) {
        this.is_owner = is_owner;
    }

    public List<forensicsInfo> getForensicsInfoList() {
        return forensicsInfoList;
    }

    public void setForensicsInfoList(List<forensicsInfo> forensicsInfoList) {
        this.forensicsInfoList = forensicsInfoList;
    }

    @Override
    public String toString() {
        return "caseInfo{" +
                "case_code=" + case_code +
                ", case_forensics='" + case_forensics + '\'' +
                ", case_create=" + case_create +
                ", cse_address='" + cse_address + '\'' +
                ", case_time=" + case_time +
                ", case_reporter='" + case_reporter + '\'' +
                ", case_cellphone_number=" + case_cellphone_number +
                ", case_upload_user=" + case_upload_user +
                ", user_ch_name='" + user_ch_name + '\'' +
                ", phone_number=" + phone_number +
                ", case_status=" + case_status +
                ", caseSchedule='" + caseSchedule + '\'' +
                ", caseStatus='" + caseStatus + '\'' +
                ", sum_pay=" + sum_pay +
                ", town='" + town + '\'' +
                ", village='" + village + '\'' +
                ", is_household=" + is_household +
                ", is_owner=" + is_owner +
                '}';
    }
}
