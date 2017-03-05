package model;

public class contact {
    private int id;
    private int region_id;
    private int phone_number;
    private String police_station_name;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getPolice_station_name() {
        return police_station_name;
    }

    public void setPolice_station_name(String police_station_name) {
        this.police_station_name = police_station_name;
    }

    @Override
    public String toString() {
        return "contact{" +
                "id=" + id +
                ", region_id=" + region_id +
                ", phone_number=" + phone_number +
                ", police_station_name='" + police_station_name + '\'' +
                '}';
    }
}
