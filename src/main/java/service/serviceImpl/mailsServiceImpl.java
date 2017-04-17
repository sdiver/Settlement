package service.serviceImpl;

import mapper.mailMapper;
import model.appendix;
import model.contactList;
import model.mail;
import model.mailLIst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.mailsService;
import util.deleteFile;
import util.encryption;
import util.fileuploaduil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mailsServiceImpl")
public class mailsServiceImpl implements mailsService {
    @Autowired
    mailMapper mailmapper;
    public Map<Object, Object> send(String title, String sendUserId, String context) {
        String[] users=sendUserId.split(",");
        Map<Object, Object> map = new HashMap<Object, Object>();
        encryption encry = new encryption();
        String mailUuid = encry.uuidFactory();
        map.put("title", title);
        map.put("context", context);
        map.put("mailUuid", mailUuid);
        mailmapper.sendMail(map);
        for(int i = 0; i < users.length; i++){
            int userid = Integer.parseInt(users[i]);
            Map<Object, Object> info = new HashMap<Object, Object>();
            info.put("sendUserId", userid);
            info.put("mailUuid", mailUuid);
            mailmapper.send(info);
        }
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("result", 1);
        result.put("mailUuid",mailUuid);
        return result;
    }

    public Map<Object,Object> view(int userId) {
        List<mailLIst> map = mailmapper.view(userId);
        List<mailLIst> info = mailmapper.viewall();
        map.addAll(info);
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("mailList", map);
        return result;
    }

    public Map<Object, Object> mailInfo(int id){
        mail mailinfo = mailmapper.mailinfo(id);
        List<appendix> appendixList = mailmapper.appendixinfo(mailinfo.getMail_uuid());
        mailinfo.setAppendixList(appendixList);
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("mailInfo", mailinfo);
        return result;
    }

    public Map<Object, Object> deleteMail(int id) {
        mail mailinfo = mailmapper.mailinfo(id);
        mailmapper.mailDelete(id);
        mailmapper.appendixDelete(mailinfo.getMail_uuid());
        mailmapper.personDelete(mailinfo.getMail_uuid());
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("result", 1);
        return result;
    }
    public Map<Object, Object> addAppendix(HttpServletRequest request) {
        fileuploaduil uploadFile = new fileuploaduil();
        Map<Object, Object> map = uploadFile.fileuploaduilfuc(request);
        mailmapper.addAppendix(map);
        String id = String.valueOf(map.get("id"));
        int newId = Integer.parseInt(id);
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("result", 1);
        result.put("id", newId);
        return result;
    }

    public Map<Object, Object> deleteAppendix(int id) {
        String fileUrl = mailmapper.getUrlAppendix(id);
        mailmapper.deleteAppendix(id);
        deleteFile deleteFiles = new deleteFile();
        deleteFiles.deleteFiles(fileUrl);
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> listmailRegion() {
        List<contactList> listcounty = new ArrayList<contactList>();
        contactList countyList = mailmapper.listCounty();
        listcounty.add(countyList);
        List<contactList> townList = mailmapper.listTown(countyList.getRegionId());
        listcounty.addAll(townList);
        for(int j=0; j < townList.size(); j++){
            List<contactList> villageList = mailmapper.listVillage(townList.get(j).getRegionId()/100);
            listcounty.addAll(villageList);
        }
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("countyList", listcounty);
        return map;
    }
}
