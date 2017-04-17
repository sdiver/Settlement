package service;

import java.util.Map;
/**
 *@Title: userOperateService
 *@Package: service
 *@Description: userOperateService.java
 *@author: Sdiver mail 18605916639_wo_cn
 *@date: 2/28/17
 *@version: V1.0
 */
public interface userOperateService {
    Map<Object, Object> register(String userName, String userPassword, String userChName, String phoneNumber, int userTypeId,
                                 int workAreaId, String workAddress);
    Map<Object,Object> login(String userName, String userPassword);
    Map<Object,Object> changePwd(int userId, String userPassword, String newPassword);
    int checkToken(int userId, String token);
    Map<Object,Object> modifyInfo(int userId, String phoneNumber, String workAddress);
}
