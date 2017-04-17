package service.serviceImpl;

import mapper.settlementManageMapper;
import mapper.settlementSearchMapper;
import mapper.userOperateMapper;
import model.caseInfo;
import model.region;
import model.user_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.settlementManageService;
import util.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
* @Title: settlementManageServiceImpl
* @Package service.serviceImpl 
* @Description: settlementManageServiceImpl.java
* @author Sdiver 18605916639_wo_cn   
* @date 2017/3/2 上午12:05 
* @version V1.0   
*/
@Service("settlementManageServiceImpl")
public class settlementManageServiceImpl implements settlementManageService {
    private encryption encrypt = new encryption();
    @Autowired
    settlementManageMapper settlementManagemapper;
    @Autowired
    userOperateMapper userOperatemapper;
    @Autowired
    settlementSearchMapper settlementSearchmapper;
    downFile ff = new downFile();
    public Map<Object, Object> listRegion() {
        Map<Object, Object> result = new HashMap<Object, Object>();
        List<region> townList = settlementManagemapper.listTown();
        List<region> villageList = settlementManagemapper.listVillage();
        result.put("townList", townList);
        result.put("villageList", villageList);
        return result;
    }
/**
     *@Title:
     *@Description: settlementManageServiceImpl.java
     *@param:
     *@return:
     *@author: Sdiver
     *@Date: 2017/3/17 上午12:19
     */
    public synchronized Map<Object, Object> setUpCase(int caseRegionId, long caseCellphoneNumber,
                                         int isHousehold, int isOwner, int case_status,
                                         String caseAddress, String caseReporter,
                                         String caseIdentity, Date caseTime, int userId) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        Map<Object, Object> map = new HashMap<Object, Object>();
        user_info userInfo = userOperatemapper.findUserByUserId(userId);
        //判断区域是否正确
        if(userInfo.getWork_area_id()/100 == caseRegionId/100) {
            //创建caseCode号
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date now = new Date();
            String code = dateFormat.format(now);
            String foreCode = String.valueOf(caseRegionId);
            String caseCode = foreCode + code;
            //搜索用到的前缀
            SimpleDateFormat dateFor = new SimpleDateFormat("yyyyMMdd");
            Date nowTime = new Date();
            long codeTime = Integer.parseInt(dateFor.format(nowTime));
            long caseCodeTime = (long) (caseRegionId * Math.pow(10, 8) + codeTime);
            //图片关联uuid
            String caseForensics = encrypt.uuidFactory();
            map.put("caseCodeTime", caseCodeTime);
            map.put("caseCode", caseCode);
            map.put("caseRegionId", caseRegionId);
            map.put("caseAddress", caseAddress);
            map.put("caseTime", caseTime);
            map.put("caseReporter", caseReporter);
            map.put("caseCellphoneNumber", caseCellphoneNumber);
            map.put("caseIdentity", caseIdentity);
            map.put("isHousehold", isHousehold);
            map.put("isOwner", isOwner);
            map.put("caseForensics", caseForensics);
            map.put("caseStatus", 0);
            map.put("caseUploadUser", userId);
            //获取创建的caseCode
            String caseRealCode = settlementManagemapper.myCaseCode(map);
            //创建案件
            settlementManagemapper.setUpCase(map);
            result.put("caseForensics", caseForensics);
            result.put("caseCode", caseRealCode);
            return result;
        }
        result.put("result", 3);
        return result;
    }
    /**
     *@Title:
     *@Description: settlementManageServiceImpl.java
     *@param:
     *@return:
     *@author: Sdiver
     *@Date: 2017/3/2 上午2:41
     */
    public synchronized Map<Object, Object> forensicsUpload(String forensics, String caseForensics, int forensicsType, String forensicsContext, int lostAmount, int lostWeight) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        Map<Object, Object> abp = new HashMap<Object, Object>();
        String[] forensicsPic = forensics.split(",");
        //add type、i、casecode
        String caseCode = settlementManagemapper.searchByForensics(caseForensics);
        String caseType = settlementManagemapper.searchByForensicsType(forensicsType);
        abp.put("caseForensics", caseForensics);
        abp.put("forensicsType", forensicsType);
        //获取最新forensicsId
        int forensicsId = settlementManagemapper.getForensicsId(abp);
        caseType = forensicsId + caseType;
        Map<Object, Object> info = new HashMap<Object, Object>();
        info.put("caseForensics", caseForensics);
        info.put("forensicsType", forensicsType);
        info.put("lostAmount", lostAmount);
        info.put("lostWeight", lostWeight);
        info.put("forensicsContext", forensicsContext);
        if(forensics != null && forensics != "") {
            for (int i = 0; i < forensicsPic.length; i++) {
                ImageUpload imageupload = new ImageUpload();
                Map<String, Object> image = new HashMap<String, Object>();
                try {
                    image = imageupload.upload(forensicsPic[i], caseCode, caseType);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String MSG = (String) image.get("MSG");
                if (MSG.equals("SUCCESS")) {
                    String photoUrl = (String) image.get("URL");
                    String photoMiniUrl = (String) image.get("miniURL");
                    info.put("photoUrl", photoUrl);
                    info.put("photoMiniUrl", photoMiniUrl);
                    settlementManagemapper.forensicsUpload(info);
                } else {
                    result.put("result", 10);
                    result.put("picone", i + 1);
                }
            }
        }else{
            String photoUrl = "";
            String photoMiniUrl = "";
            info.put("photoUrl", photoUrl);
            info.put("photoMiniUrl", photoMiniUrl);
            settlementManagemapper.forensicsUpload(info);
        }
        result.put("forensicsId", forensicsId);
        return result;
    }

    public Map<Object, Object> deleteForensics(String caseForensics, int forensicsType, int forensicsId) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        String caseCode = settlementManagemapper.searchByForensics(caseForensics);
        String caseType = settlementManagemapper.searchByForensicsType(forensicsType);
        map.put("caseForensics", caseForensics);
        map.put("forensicsId", forensicsId);
        settlementManagemapper.deleteForensics(map);
        caseType = forensicsId + caseType;
        String casePath = globalV.MainUrl+caseCode+"/"+caseType;
        String miniCasePath = globalV.miniMainUrl+caseCode+"/"+caseType;
        File casePathFile = new File(casePath);
        File caseminiPathFile = new File(miniCasePath);
        ff.deleteFile(casePathFile);
        ff.deleteFile(caseminiPathFile);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> deleteSettlement(String caseCode) {
        String caseForensics = settlementManagemapper.findByCaseCode(caseCode);
        settlementManagemapper.deleteEveryForensics(caseForensics);
        Map<Object, Object> result = new HashMap<Object, Object>();
        settlementManagemapper.deleteSettlement(caseCode);
        String casePath = globalV.MainUrl+caseCode;
        String miniCasePath = globalV.miniMainUrl+caseCode;
        File casePathFile = new File(casePath);
        File caseminiPathFile = new File(miniCasePath);
        ff.deleteFile(casePathFile);
        ff.deleteFile(caseminiPathFile);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> report(String caseCode) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        Map<Object, Object> map = new HashMap<Object, Object>();
        int status = 1;
        map.put("caseCode", caseCode);
        map.put("status", status);
        settlementManagemapper.report(map);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> sendBack(String caseCode) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        Map<Object, Object> map = new HashMap<Object, Object>();
        int status = 2;
        caseInfo caseinfo = settlementSearchmapper.settleinfo(caseCode);
        if(caseinfo.getCase_status() == 1) {
            map.put("caseCode", caseCode);
            map.put("status", status);
            settlementManagemapper.caseExpected(map);
            result.put("result", 1);
            return result;
        }
        result.put("result", 3);
        return result;
    }

    public Map<Object, Object> caseExpected(String caseCode, String amountOfPay, String id) throws IOException {
        Map<Object, Object> result = new HashMap<Object, Object>();
        Map<Object, Object> info = new HashMap<Object, Object>();
        int status = 3;
        info.put("caseCode", caseCode);
        info.put("status", status);
        String[] Pay = amountOfPay.split(",");
        String[] caseId = id.split(",");
        for (int i = 0; i < Pay.length; i++) {
            Map<Object, Object> map = new HashMap<Object, Object>();
            map.put("id", caseId[i]);
            map.put("amountOfPay", Pay[i]);
            settlementManagemapper.paySettle(map);
        }
        String sourceFilePath = globalV.MainUrl+caseCode;
        String zipFilePath = globalV.MainUrl + caseCode +".zip";
        FileToZip zipfile = new FileToZip(zipFilePath);
        zipfile.compress(sourceFilePath);
        String url = globalV.url + caseCode + ".zip";
        info.put("zip_url", url);
        settlementManagemapper.caseExpected(info);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> forensicsUpdate(String forensics, String caseForensics, int forensicsId,
                                               int forensicsType, String forensicsContext, int lostAmount,
                                               int lostWeight) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        String[] forensicsPic = forensics.split(",");
        //add type、i、casecode
        String caseCode = settlementManagemapper.searchByForensics(caseForensics);
        String caseType = settlementManagemapper.searchByForensicsType(forensicsType);
        caseType = forensicsId + caseType;
        for(int i = 0; i< forensicsPic.length; i++){
            ImageUpload imageupload = new ImageUpload();
            Map<String,Object> image = new HashMap<String,Object>();
            Map<Object, Object> info = new HashMap<Object, Object>();
            try {
                image = imageupload.upload(forensicsPic[i], caseCode, caseType);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String MSG = (String) image.get("MSG");
            if(MSG.equals("SUCCESS")){
                String photoUrl = (String) image.get("URL");
                String photoMiniUrl = (String) image.get("miniURL");
                info.put("caseForensics", caseForensics);
                info.put("forensicsId",forensicsId);
                info.put("forensicsType", forensicsType);
                info.put("lostAmount", lostAmount);
                info.put("lostWeight", lostWeight);
                info.put("photoUrl", photoUrl);
                info.put("photoMiniUrl", photoMiniUrl);
                info.put("forensicsContext", forensicsContext);
                settlementManagemapper.forensicsUpdate(info);
            }else{
                result.put("result",10);
                result.put("picone", i+1);
            }
        }
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> modifyCase(String caseCode, long caseCellphoneNumber,
                                          int isHousehold, int isOwner, String caseAddress,
                                          String caseReporter, String caseIdentity,
                                          Date caseFormatTime, int userId) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        Map<Object, Object> map = new HashMap<Object, Object>();
        caseInfo caseinfomation = settlementSearchmapper.settleinfo(caseCode);
        if(caseinfomation.getCase_upload_user() == userId){
            map.put("caseCode", caseCode);
            map.put("caseAddress", caseAddress);
            map.put("caseTime", caseFormatTime);
            map.put("caseReporter", caseReporter);
            map.put("caseCellphoneNumber", caseCellphoneNumber);
            map.put("caseIdentity", caseIdentity);
            map.put("isHousehold", isHousehold);
            map.put("isOwner", isOwner);
            settlementManagemapper.modifyCase(map);
            result.put("result", 1);
            return result;
        }
        result.put("result", 0);
        return result;
    }

    public Map<Object, Object> deletePhoto(String caseForensics, int forensicsType, int forensicsId, int photoId, String url) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        map.put("caseForensics", caseForensics);
        map.put("forensicsId", forensicsId);
        map.put("photoId", photoId);
        settlementManagemapper.deletePhoto(map);
        String splitUrl[] = url.split("/");
        String prefixUrl = splitUrl[5]+"/"+splitUrl[6]+"/"+splitUrl[7];
        String minipPrefixUrl = splitUrl[5]+"/"+splitUrl[6]+"/mini"+splitUrl[7];
        String casePath = globalV.MainUrl+prefixUrl;
        String miniCasePath = globalV.miniMainUrl+minipPrefixUrl;
        File casePathFile = new File(casePath);
        File caseminiPathFile = new File(miniCasePath);
        ff.deleteFile(casePathFile);
        ff.deleteFile(caseminiPathFile);
        result.put("result", 1);
        return result;

    }

    public Map<Object, Object> sendBackReason(int forensicsId, String caseForensics, int photoId, String sendBackReason) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> info = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        map.put("forensicsId", forensicsId);
        map.put("caseForensics", caseForensics);
        map.put("photoId", photoId);
        int id = settlementManagemapper.searchForensicsId(map);
        int i = settlementManagemapper.countSendId(id);
        info.put("id", id);
        info.put("sendBackReason", sendBackReason);
        result.put("result", 1);
        if(i == 0) {
            settlementManagemapper.sendBackReason(info);
            return result;
        }
        settlementManagemapper.UpdateSendBack(info);
        return result;
    }


}