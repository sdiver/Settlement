package service;

import java.io.IOException;
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

    Map<Object,Object> setUpCase(int caseRegionId, long caseCellphoneNumber, int isHousehold, int isOwner,
                                 int case_status, String caseAddress, String caseReporter, String caseIdentity,
                                 Date caseTime, int userId);

    Map<Object,Object> forensicsUpload(String forensics, String caseForensics, int forensicsType,
                                       String forensicsContext, int lostAmount, int lostWeight);

    Map<Object,Object> deleteForensics(String caseForensics, int forensicsType, int forensicsId);

    Map<Object,Object> deleteSettlement(String caseCode);

    Map<Object,Object> report(String caseCode);

    Map<Object,Object> sendBack(String caseCode);

    Map<Object,Object> caseExpected(String caseCode, String amountOfPay, String id) throws IOException;

    Map<Object,Object> forensicsUpdate(String forensics, String caseForensics, int forensicsId, int forensicsType,
                                       String forensicsContext, int lostAmount, int lostWeight);

    Map<Object,Object> modifyCase(String caseCode, long caseCellphoneNumber, int isHousehold, int isOwner, String caseAddress, String caseReporter, String caseIdentity, Date caseFormatTime, int userId);

    Map<Object,Object> deletePhoto(String caseForensics, int forensicsType, int forensicsId, int photoId, String url);

    Map<Object,Object> sendBackReason(int forensicsId, String caseForensics, int photoId, String sendBackReason);
}
