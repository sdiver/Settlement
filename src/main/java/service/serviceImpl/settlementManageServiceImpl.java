package service.serviceImpl;

import mapper.settlementManageMapper;
import model.region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.settlementManageService;
import util.FileToZip;
import util.ImageUpload;
import util.encryption;
import util.globalV;

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
        int caseCode = caseRegionId * (int) Math.pow(10, 16) + code * 100 + 1;
        SimpleDateFormat dateFor = new SimpleDateFormat("yyyyMMdd");
        Date nowTime = new Date();
        int codeTime = Integer.parseInt(dateFor.format(nowTime));
        int caseCodeTime = caseRegionId * (int) Math.pow(10, 8)+ codeTime;
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
        map.put("d", userId);
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

    public Map<Object, Object> deleteForensics(String caseForensics, int forensicsType) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        int caseCode = settlementManagemapper.searchByForensics(caseForensics);
        String caseType = settlementManagemapper.searchByForensicsType(forensicsType);
        map.put("caseForensics", caseForensics);
        map.put("forensicsType", forensicsType);
        settlementManagemapper.deleteForensics(map);
        String casePath = globalV.MainUrl+caseCode+"/"+caseType;
        String miniCasePath = globalV.miniMainUrl+caseCode+"/"+caseType;
        File casePathFile = new File(casePath);
        File caseminiPathFile = new File(miniCasePath);
        deleteFile(casePathFile);
        deleteFile(caseminiPathFile);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> deleteSettlement(int caseCode) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        settlementManagemapper.deleteSettlement(caseCode);
        String caseForensics = settlementManagemapper.findByCaseCode(caseCode);
        settlementManagemapper.deleteEveryForensics(caseForensics);
        String casePath = globalV.MainUrl+caseCode;
        String miniCasePath = globalV.miniMainUrl+caseCode;
        File casePathFile = new File(casePath);
        File caseminiPathFile = new File(miniCasePath);
        deleteFile(casePathFile);
        deleteFile(caseminiPathFile);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> report(int caseCode) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        Map<Object, Object> map = new HashMap<Object, Object>();
        int status = 1;
        map.put("caseCode", caseCode);
        map.put("status", status);
        settlementManagemapper.caseExpected(map);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> sendBack(int caseCode) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        Map<Object, Object> map = new HashMap<Object, Object>();
        int status = 2;
        map.put("caseCode", caseCode);
        map.put("status", status);
        settlementManagemapper.caseExpected(map);
        result.put("result", 1);
        return result;
    }

    public Map<Object, Object> caseExpected(int caseCode, String amountOfPay, String id) {
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
        String zipFilePath = globalV.MainUrl;
        String fileName = String.valueOf(caseCode);
        boolean flag = FileToZip.fileToZip(sourceFilePath, zipFilePath, fileName);
        String url = globalV.url + fileName + ".zip";
        info.put("zip_url", url);
        settlementManagemapper.caseExpected(info);
        if(flag){
            result.put("result", 1);
            return result;
        }
        result.put("result", 0);
        return result;
    }

    public Map<Object, Object> forensicsUpdate(String forensics, String caseForensics, int forensicsId, int forensicsType, String forensicsContext, int lostAmount, int lostWeight) {
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

    private void deleteFile(File file){
        boolean i;
        if(file.exists()){
            if(file.isFile()){
                i = file.delete();
                System.out.println(i);
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                assert files != null;
                for (File file1 : files) {
                    this.deleteFile(file1);
                }
                i = file.delete();
                System.out.println(i);
            }
        } else {
            System.out.println("file not exist");
        }
    }
}