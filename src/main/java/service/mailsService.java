package service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface mailsService {
    Map<Object,Object> send(String title, String sendUserId, String context);

    Map<Object,Object> view(int userId);

    Map<Object, Object> mailInfo(int id);

    Map<Object,Object> deleteMail(int id);

    Map<Object,Object> addAppendix(HttpServletRequest request);

    Map<Object,Object> deleteAppendix(int id);

    Map<Object,Object> listmailRegion();
}
