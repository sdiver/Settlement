package mapper;

import model.contact;
import model.contactList;

import java.util.List;
import java.util.Map;

public interface contactMapper {
    void addPoliceContact(Map<Object, Object> map);

    void modifyPoliceContact(Map<Object, Object> map);

    void deletePoliceContact( int id);

    List<contactList> listContact();

    List<contactList> listregion();

    int getPerson();

    List<contact> listuser(Map<Object, Object> map);

    List<contact> listpoliceuser(Map<Object, Object> map);
}
