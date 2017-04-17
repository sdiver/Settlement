package service;

import java.util.Map;

public interface contactService {
    Map<Object,Object> addPoliceContact(int regionId, String phoneNumber, String policeStationName);

    Map<Object,Object> modifyPoliceContact(int id, int regionId, String phoneNumber, String policeStationName);

    Map<Object,Object> deletePoliceContact(int id);

    Map<Object,Object> listContact();

    Map<Object,Object> listuser(int regionId, int type);
}
