package service.serviceImpl;

import mapper.settlementSearchMapper;
import mapper.userOperateMapper;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.settlementSearchService;
import util.globalV;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String areaType = String.valueOf(regionId);
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
                if(status > 0 || townId == regionId / 100){
                    settlementStatisticsList = settlementSearchmapper.settleStatistics(map);
                    settleinfoList = settlementSearchmapper.settleSearch(map);
                }
                break;
            case 3:
                if(townId == regionId / 100){
                    settlementStatisticsList = settlementSearchmapper.settleStatistics(map);
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
        settleinfoList = substitute(settleinfoList, typeId);
        result.put("settlementStatistics", settlementStatisticsList);
        result.put("settleinfoList", settleinfoList);
        return result;
    }

    public Map<Object, Object> settleinfo(int caseCode, int userId) {
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
    private List<settleinfo> substitute(List<settleinfo> settleinfoList, int typeId){
        List<settleinfo> settleinfos = null;
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
            switch (aSettleinfoList.getHeading()) {
                case 1:
                    aSettleinfoList.setCaseType(globalV.motorcycle);
                    break;
                case 2:
                    aSettleinfoList.setCaseType(globalV.electrombile);
                    break;
                case 3:
                    aSettleinfoList.setCaseType(globalV.jewelry);
                    break;
                case 4:
                    aSettleinfoList.setCaseType(globalV.animals);
                    break;
                case 5:
                    aSettleinfoList.setCaseType(globalV.others);
                    break;
            }
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
