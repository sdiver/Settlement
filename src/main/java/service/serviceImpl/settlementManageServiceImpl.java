package service.serviceImpl;

import mapper.settlementManageMapper;
import model.region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.settlementManageService;
import util.ImageUpload;
import util.encryption;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public Map<Object, Object> listRegion() {
        Map<Object, Object> result = new HashMap<Object, Object>();
        List<region> townList = settlementManagemapper.listTown();
        List<region> villageList = settlementManagemapper.listVillage();
        result.put("townList", townList);
        result.put("villageList", villageList);
        return result;
    }

    public Map<Object, Object> setUpCase(int caseRegionId, int caseCellphoneNumber,
                                         int isHousehold, int isOwner, int case_status,
                                         String caseAddress, String caseReporter,
                                         String caseIdentity, Date caseTime, int userId) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        Map<Object, Object> map = new HashMap<Object, Object>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        int code = Integer.parseInt(dateFormat.format(now));
        int caseCode = caseRegionId + code;
        String caseForensics = encrypt.uuidFactory();
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
        settlementManagemapper.setUpCase(map);
        result.put("result", 1);
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
    public Map<Object, Object> forensicsUpload(String forensics, String caseForensics, int forensicsType, String forensicsContext, int lostAmount, int lostWeight) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        String[] forensicsPic = forensics.split(",");
        //add type、i、casecode
        int caseCode = settlementManagemapper.searchByForensics(caseForensics);
        String caseType = settlementManagemapper.searchByForensicsType(forensicsType);
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
                info.put("forensicsType", forensicsType);
                info.put("lostAmount", lostAmount);
                info.put("lostWeight", lostWeight);
                info.put("photoUrl", photoUrl);
                info.put("photoMiniUrl", photoMiniUrl);
                info.put("forensicsContext", forensicsContext);
                settlementManagemapper.forensicsUpload(info);
            }else{
                result.put("result",10);
                result.put("picone", i+1);
            }
        }
        result.put("result", 1);
        return result;
    }
}