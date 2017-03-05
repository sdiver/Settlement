package mapper;

import model.region;

import java.util.List;
import java.util.Map;

/**   
* @Title: settlementManageMapper
* @Package mapper 
* @Description: settlementManageMapper.java
* @author Sdiver 18605916639_wo_cn   
* @date 2017/3/2 上午1:40 
* @version V1.0   
*/
public interface settlementManageMapper {
    List<region> listTown();
    List<region> listVillage();
    void setUpCase(Map<Object, Object> map);
    void forensicsUpload(Map<Object, Object> map);
    int searchByForensics(String caseForensics);
    String searchByForensicsType(int forensicsType);
    void deleteForensics(Map<Object, Object> map);
    void deleteSettlement(int caseCode);
    String findByCaseCode(int caseCode);
    void deleteEveryForensics(String caseForensics);
    void paySettle(Map<Object, Object> map);
    void caseExpected(Map<Object, Object> map);
    void forensicsUpdate(Map<Object, Object> info);
}
