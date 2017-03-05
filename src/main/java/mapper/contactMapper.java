package mapper;

import model.contact;

import java.util.List;
import java.util.Map;

public interface contactMapper {
    void addPoliceContact(Map<Object, Object> map);

    void modifyPoliceContact(Map<Object, Object> map);

    void deletePoliceContact( int id);

    List<contact> listContact();

    List<contact> listnonContact();
}
