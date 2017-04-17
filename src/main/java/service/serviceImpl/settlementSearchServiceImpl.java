package service.serviceImpl;

import mapper.settlementSearchMapper;
import mapper.userOperateMapper;
import model.*;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.settlementSearchService;
import util.FileToZip;
import util.downFile;
import util.globalV;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("settlementSearchServiceImpl")
public class settlementSearchServiceImpl implements settlementSearchService {
    @Autowired
    userOperateMapper userOperatemapper;
    @Autowired
    settlementSearchMapper settlementSearchmapper;
    public Map<Object, Object> settleStatistics(int userId, Date caseStartTime, Date caseEndTime, int regionId,
                                                int forensicsType, int status, int Page, int pagePerNum) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        int pageMinNum = ( Page - 1 ) * pagePerNum;
        String areaType = "";
        int regionTransation = 0;
        if(regionId % 100 == 0){
            regionTransation = regionId / 100;
        }
        if(regionId != 0){
            areaType = areaType + regionTransation;
        }
        map.put("caseStartTime", caseStartTime);
        map.put("caseEndTime", caseEndTime);
        map.put("areaType", areaType);
        map.put("forensicsType", forensicsType);
        map.put("status", status);
        map.put("pageMinNum", pageMinNum);
        map.put("pagePerNum", pagePerNum);
        user_info userinfo = userOperatemapper.findUserByUserId(userId);
        int typeId = userinfo.getUser_type_id();
        int areaId = userinfo.getWork_area_id();
        int townId = areaId / 100;
        settlementStatistics settlementStatisticsList = null;
        List<settleinfo> settleinfoList = null;
        switch (typeId){
            case 1:
                if(status > 0){
                    settlementStatisticsList = settlementSearchmapper.settleStatistics(map);
                    settleinfoList = settlementSearchmapper.settleSearch(map);
                }
                break;
            case 2:
                if(status > 0 && townId == regionId / 100){
                    settlementStatisticsList = settlementSearchmapper.settleStatistics(map);
                    settleinfoList = settlementSearchmapper.settleSearch(map);
                }
                break;
            case 3:
                if(townId == regionId / 100){
                    settlementStatisticsList = settlementSearchmapper.settleStatistics(map);
                    settlementStatisticsList.setSum_pay(0);
                    settleinfoList = settlementSearchmapper.settleSearch(map);
                }
                break;
            case 4:
                if(status > 0 ) {
                    settlementStatisticsList = settlementSearchmapper.settleStatistics(map);
                    settleinfoList = settlementSearchmapper.settleSearch(map);
                }
                break;
        }
        if(settleinfoList != null) {
            settleinfoList = substitute(settleinfoList, typeId);
        }
        result.put("settlementStatistics", settlementStatisticsList);
        result.put("settleinfoList", settleinfoList);
        return result;
    }

    public Map<Object, Object> settleinfo(String caseCode, int userId) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        caseInfo caseinfo = settlementSearchmapper.settleinfo(caseCode);
        switch (caseinfo.getCase_status()) {
            case 3:
                caseinfo.setCaseSchedule(globalV.closeCase);
                caseinfo.setCaseStatus("-");
                break;
            case 2:
                caseinfo.setCaseSchedule(globalV.UnCloseCase);
                caseinfo.setCaseStatus(globalV.sendBack);
                break;
            case 1:
                caseinfo.setCaseSchedule(globalV.UnCloseCase);
                caseinfo.setCaseStatus(globalV.submit);
                break;
            case 0:
                caseinfo.setCaseSchedule("-");
                caseinfo.setCaseStatus("-");
                break;
        }
        List<forensicsInfo> forensicsInfoList = settlementSearchmapper.
                forensicsInfoList(caseinfo.getCase_forensics());
        int sum_money = 0;
        for (forensicsInfo aForensicsInfoList : forensicsInfoList) {
            sum_money = sum_money + aForensicsInfoList.getAmount_of_pay();
        }
        if(caseinfo.getCase_upload_user() == userId){
            caseinfo.setIfAuthority(1);
        }else{
            caseinfo.setIfAuthority(0);
        }
        caseinfo.setSum_pay(sum_money);
        caseinfo.setForensicsInfoList(forensicsInfoList);
        map.put("caseinfo",caseinfo);
        return map;
    }

    public Map<Object, Object> mySettles(int userId, int status) {
        Map<Object, Object> result = new HashMap<Object, Object>();
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("userId", userId);
        map.put("status", status);
        List<caseInfo> caseInfoList = settlementSearchmapper.mySettles(map);
        result.put("caseInfoList", caseInfoList);
        return result;
    }

    public Map<Object, Object> downloadFile(String caseCode, HttpServletResponse response){
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        String[] caseCoder = caseCode.split(",");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String name = df.format(new Date());
        String zipFilePath = globalV.MainUrl + name +".zip";
        FileToZip zipfile = new FileToZip(zipFilePath);
        File zipFile = new File(zipFilePath);
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0; i < caseCoder.length; i++){
            caseInfo settle = settlementSearchmapper.settleinfo(caseCoder[i]);
            if(settle.getCase_status() != 3){
                map.put("result", 0);
                return map;
            }
            String sourceFilePath = globalV.MainUrl + caseCoder[i] +".zip";
            try {
                zipfile.compress(sourceFilePath, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        downFile file = new downFile();
        try {
            file.downloadFile(zipFilePath, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File casePathFile = new File(zipFilePath);
        file.deleteFile(casePathFile);
        result.put("result", 1);
        return result;
    }

    private List<settleinfo> substitute(List<settleinfo> settleinfoList, int typeId){
        List<settleinfo> settleinfos = new ArrayList<settleinfo>();
        settleinfoList.size();
        for (settleinfo aSettleinfoList : settleinfoList) {
            switch (aSettleinfoList.getCase_status()) {
                case 3:
                    aSettleinfoList.setCaseSchedule(globalV.closeCase);
                    aSettleinfoList.setCaseStatus("-");
                    break;
                case 2:
                    aSettleinfoList.setCaseSchedule(globalV.UnCloseCase);
                    aSettleinfoList.setCaseStatus(globalV.sendBack);
                    break;
                case 1:
                    aSettleinfoList.setCaseSchedule(globalV.UnCloseCase);
                    aSettleinfoList.setCaseStatus(globalV.submit);
                    break;
            }
            String[] i = aSettleinfoList.getHeadingConcat().split(",");
            String info = "";
            for(int j =0; j<i.length; j++) {
                int h = Integer.parseInt(i[j]);
                switch (h) {
                    case 1:
                        info = info + globalV.motorcycle+";";
                        break;
                    case 2:
                        info = info + globalV.electrombile+";";
                        break;
                    case 3:
                        info = info + globalV.jewelry+";";
                        break;
                    case 4:
                        info = info + globalV.animals+";";
                        break;
                    case 5:
                        info = info + globalV.others+";";
                        break;
                }
            }
            aSettleinfoList.setCaseType(info);
            switch (typeId) {
                case 1:
                    aSettleinfoList.setCaseStatus("");
                    break;
                case 2:
                    aSettleinfoList.setCaseStatus("");
                    aSettleinfoList.setTown("");
                    break;
                case 3:
                    aSettleinfoList.setSum_pay(0);
                    aSettleinfoList.setTown("");
                    break;
            }
            settleinfos.add(aSettleinfoList);
        }
        return settleinfos;
    }
}
