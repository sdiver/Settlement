package service.serviceImpl;

import mapper.userOperateMapper;
import model.user_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.userOperateService;
import uil.encryption;

import java.util.HashMap;
import java.util.Map;

/**
 *@Title: userOperateServiceImpl
 *@Package: service.serviceImpl
 *@Description: userOperateServiceImpl.java
 *@author: Sdiver mail 18605916639_wo_cn
 *@date: 2/28/17
 *@version: V1.0
 */
@Service("userOperateServiceImpl")
public class userOperateServiceImpl implements userOperateService {
    @Autowired
    private userOperateMapper userOperatemapper;
    private encryption encrypt = new encryption();
    /**
     *@Title: userOperateServiceImpl
     *@Package: service.serviceImpl
     *@Description: userOperateServiceImpl.java
     *@author: Sdiver mail 18605916639_wo_cn
     *@date: 2/28/17
     *@version: V1.0
     */
    public Map<Object, Object> register(String userName, String userPassword, String userChName, int phoneNumber,
                                        int userTypeId, int workAreaId, String workAddress) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        String password = encrypt.MD5(userPassword);
        map.put("userName", userName);
        map.put("password", password);
        map.put("userChName", userChName);
        map.put("phoneNumber", phoneNumber);
        map.put("userTypeId", userTypeId);
        map.put("workAreaId", workAreaId);
        map.put("workAddress", workAddress);
        userOperatemapper.register(map);
        result.put("result", 1);
        return result;
    }
    /**
     *@Title: userOperateServiceImpl
     *@Package: service.serviceImpl
     *@Description: userOperateServiceImpl.java
     *@author: Sdiver mail 18605916639_wo_cn
     *@date: 2/28/17
     *@version: V1.0
     */
    public Map<Object, Object> login(String userName, String userPassword) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        String password = encrypt.MD5(userPassword);
        map.put("userName", userName);
        map.put("password", password);
        user_info userInformation = userOperatemapper.login(map);
        result.put("userInfo", userInformation);
        return result;
    }
    /**
     *@Title: userOperateServiceImpl
     *@Package: service.serviceImpl
     *@Description: userOperateServiceImpl.java
     *@author: Sdiver mail 18605916639_wo_cn
     *@date: 2/28/17
     *@version: V1.0
     */
    public Map<Object, Object> changePwd(int userId, String userPassword, String newPassword) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        String password = encrypt.MD5(userPassword);
        String newPwd = encrypt.MD5(newPassword);
        map.put("userId", userId);
        map.put("password", password);
        map.put("newPwd", newPwd);
        userOperatemapper.changePwd(map);
        result.put("result", 1);
        return result;
    }
    /**
     *@Title: userOperateServiceImpl
     *@Package: service.serviceImpl
     *@Description: userOperateServiceImpl.java
     *@author: Sdiver mail 18605916639_wo_cn
     *@date: 2/28/17
     *@version: V1.0
     */
    public Map<Object, Object> checkToken(int userId, String token) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        map.put("userId", userId);
        map.put("token", token);
        userOperatemapper.checkToken(map);
        result.put("result", 1);
        return result;
    }
    /**
     *@Title: userOperateServiceImpl
     *@Package: service.serviceImpl
     *@Description: userOperateServiceImpl.java
     *@author: Sdiver mail 18605916639_wo_cn
     *@date: 2/28/17
     *@version: V1.0
     */
    public Map<Object, Object> modifyInfo(int userId, String phoneNumber, String workAddress) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        map.put("userId", userId);
        map.put("phoneNumber", phoneNumber);
        map.put("workAddress", workAddress);
        userOperatemapper.modifyInfo(map);
        result.put("result", 1);
        return result;
    }
}
