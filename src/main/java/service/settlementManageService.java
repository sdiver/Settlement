package service;

import java.util.Date;
import java.util.Map;

/**
 *@Title: settlementManageService
 *@Package: service
 *@Description: settlementManageService.java
 *@author: Sdiver mail 18605916639_wo_cn
 *@date: 3/1/17
 *@version: V1.0
 */
public interface settlementManageService {
    Map<Object,Object> listRegion();

    Map<Object,Object> setUpCase(int caseRegionId, int caseCellphoneNumber, int isHousehold, int isOwner,
                                 int case_status, String caseAddress, String caseReporter, String caseIdentity,
                                 Date caseTime, int userId);

    Map<Object,Object> forensicsUpload(String forensics, String caseForensics, int forensicsType,
                                       String forensicsContext, int lostAmount, int lostWeight);

    Map<Object,Object> deleteForensics(String caseForensics, int forensicsType);

    Map<Object,Object> deleteSettlement(int caseCode);
}
