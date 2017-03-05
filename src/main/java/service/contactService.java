package service;

import java.util.Map;

public interface contactService {
    Map<Object,Object> addPoliceContact(int regionId, int phoneNumber, String policeStationName);

    Map<Object,Object> modifyPoliceContact(int id, int regionId, int phoneNumber, String policeStationName);

    Map<Object,Object> deletePoliceContact(int id);

    Map<Object,Object> listContact();
}
