package service.serviceImpl;

import mapper.contactMapper;
import model.contact;
import model.contactList;
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
    public Map<Object, Object> addPoliceContact(int regionId, String phoneNumber, String policeStationName) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        map.put("regionId", regionId);
        map.put("phoneNumber", phoneNumber);
        map.put("policeStationName", policeStationName);
        contactmapper.addPoliceContact(map);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> modifyPoliceContact(int id, int regionId, String phoneNumber, String policeStationName) {
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
        List<contactList> contactZZB = contactmapper.listregion();
        contactList contactRB = new contactList();
        int num = contactmapper.getPerson();
        contactRB.setId(21000);
        contactRB.setName("惠安县人保(" + num +")");
        contactRB.setPid(0);
        contactRB.setRegionId(1000);
        contactRB.setType(2);
        contactRB.setCountPerson(num);
        contactZZB.add(contactRB);
        List<contactList> contactJC = contactmapper.listContact();
        contactZZB.addAll(contactJC);
        result.put("contact", contactZZB);
        return result;
    }

    public Map<Object, Object> listuser(int regionId, int type) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("regionId", regionId);
        map.put("type", type);
        if(type != 3) {
            List<contact> contacts = contactmapper.listuser(map);
            result.put("contact", contacts);
            return result;
        }
        List<contact> policecontacts = contactmapper.listpoliceuser(map);
        result.put("contact", policecontacts);
        return result;
    }
}
