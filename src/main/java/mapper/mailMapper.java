package mapper;

import model.appendix;
import model.contactList;
import model.mail;
import model.mailLIst;

import java.util.List;
import java.util.Map;

public interface mailMapper {
    void send(Map<Object, Object> info);

    void sendMail(Map<Object, Object> map);

    List<mailLIst> view(int userId);

    mail mailinfo(int id);

    List<appendix> appendixinfo(String mail_uuid);

    void mailDelete(int id);

    void appendixDelete(String mail_uuid);

    void personDelete(String mail_uuid);

    void addAppendix(Map<Object, Object> map);

    String getUrlAppendix(int id);

    void deleteAppendix(int id);

    List<mailLIst> viewall();

    contactList listCounty();

    List<contactList> listTown(int regionId);

    List<contactList> listVillage(int regionId);
}
