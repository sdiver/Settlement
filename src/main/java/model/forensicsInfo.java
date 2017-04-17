package model;

public class forensicsInfo {
    private int id;
    private int forensics_id;
    private int heading;
    private int porensics_type;
    private String species;
    private int lost_amount;
    private int lost_weight;
    private int photo_id;
    private String photo_url;
    private String photo_mini_url;
    private int amount_of_pay;
    private int forensics_type;
    private String forensics_context;
    private String sent_back_reason;
    private String update_time;

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getSent_back_reason() {
        return sent_back_reason;
    }

    public void setSent_back_reason(String sent_back_reason) {
        this.sent_back_reason = sent_back_reason;
    }

    public String getForensics_context() {
        return forensics_context;
    }

    public void setForensics_context(String forensics_context) {
        this.forensics_context = forensics_context;
    }

    public int getForensics_type() {
        return forensics_type;
    }

    public void setForensics_type(int forensics_type) {
        this.forensics_type = forensics_type;
    }

    public int getPorensics_type() {
        return porensics_type;
    }

    public void setPorensics_type(int porensics_type) {
        this.porensics_type = porensics_type;
    }

    public int getForensics_id() {
        return forensics_id;
    }

    public void setForensics_id(int forensics_id) {
        this.forensics_id = forensics_id;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getLost_amount() {
        return lost_amount;
    }

    public void setLost_amount(int lost_amount) {
        this.lost_amount = lost_amount;
    }

    public int getLost_weight() {
        return lost_weight;
    }

    public void setLost_weight(int lost_weight) {
        this.lost_weight = lost_weight;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getPhoto_mini_url() {
        return photo_mini_url;
    }

    public void setPhoto_mini_url(String photo_mini_url) {
        this.photo_mini_url = photo_mini_url;
    }

    public int getAmount_of_pay() {
        return amount_of_pay;
    }

    public void setAmount_of_pay(int amount_of_pay) {
        this.amount_of_pay = amount_of_pay;
    }

    @Override
    public String toString() {
        return "forensicsInfo{" +
                "forensics_id=" + forensics_id +
                ", heading=" + heading +
                ", species='" + species + '\'' +
                ", lost_amount=" + lost_amount +
                ", lost_weight=" + lost_weight +
                ", photo_id=" + photo_id +
                ", photo_mini_url='" + photo_mini_url + '\'' +
                ", amount_of_pay=" + amount_of_pay +
                '}';
    }
}
