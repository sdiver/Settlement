/**   
* @Title: userinfo
* @Package java.model 
* @Description: user_info.java
* @author Sdiver 18605916639_wo_cn   
* @date 2017/2/28 上午1:39 
* @version V1.0   
*/
package model;

public class user_info{

    private int user_id;

    private String user_name;

    private String user_ch_name;

    private int phone_number;

    private int user_type_id;

    private int work_area_id;

    private String work_address;

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_ch_name() {
        return user_ch_name;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public int getUser_type_id() {
        return user_type_id;
    }

    public int getWork_area_id() {
        return work_area_id;
    }

    public String getWork_address() {
        return work_address;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_ch_name(String user_ch_name) {
        this.user_ch_name = user_ch_name;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public void setUser_type_id(int user_type_id) {
        this.user_type_id = user_type_id;
    }

    public void setWork_area_id(int work_area_id) {
        this.work_area_id = work_area_id;
    }

    public void setWork_address(String work_address) {
        this.work_address = work_address;
    }

    @Override
    public String toString() {
        return "user_info{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_ch_name='" + user_ch_name + '\'' +
                ", phone_number=" + phone_number +
                ", user_type_id=" + user_type_id +
                ", work_area_id=" + work_area_id +
                ", work_address='" + work_address + '\'' +
                '}';
    }
}