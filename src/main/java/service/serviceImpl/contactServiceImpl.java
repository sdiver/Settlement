package service.serviceImpl;

import mapper.contactMapper;
import model.contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.contactService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("contactServiceImpl")
public class contactServiceImpl implements contactService {
    @Autowired
    contactMapper contactmapper;
    public Map<Object, Object> addPoliceContact(int regionId, int phoneNumber, String policeStationName) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        map.put("regionId", regionId);
        map.put("phoneNumber", phoneNumber);
        map.put("policeStationName", policeStationName);
        contactmapper.addPoliceContact(map);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> modifyPoliceContact(int id, int regionId, int phoneNumber, String policeStationName) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        map.put("id", id);
        map.put("regionId", regionId);
        map.put("phoneNumber", phoneNumber);
        map.put("policeStationName", policeStationName);
        contactmapper.modifyPoliceContact(map);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> deletePoliceContact(int id) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        contactmapper.deletePoliceContact(id);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> listContact() {
        Map<Object, Object> result = new HashMap<Object, Object>();
        List<contact> contactList = contactmapper.listContact();
        List<contact> noncontactList = contactmapper.listnonContact();
        List<contact> contactListArray = null;
        for (contact tact : contactList) {
            tact.setType(3);
            contactListArray.add(tact);
        }
        for(int i=0;i<noncontactList.size();i++){
            contactListArray.add(noncontactList.get(i));
        }
        result.put("contact", contactListArray);
        return result;
    }
}
