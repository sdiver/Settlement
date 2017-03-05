package model;

public class forensicsInfo {
    private int forensics_id;
    private int heading;
    private String species;
    private int lost_amount;
    private int lost_weight;
    private int photo_id;
    private String photo_mini_url;
    private int amount_of_pay;

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
