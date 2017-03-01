package service.serviceImpl;

import mapper.userOperateMapper;
import model.authorityVo;
import model.user_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.userOperateService;
import util.encryption;

import java.util.HashMap;
import java.util.List;
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
        Map<Object, Object> acodeUpdate = new HashMap<Object, Object>();
        String password = encrypt.MD5(userPassword);
        map.put("userName", userName);
        map.put("password", password);
        user_info userInformation = userOperatemapper.login(map);
        if(userInformation != null) {
            int userId = userInformation.getUser_id();
            String uuid = encrypt.uuidFactory();
            String MDUuid = "SdiverIsCodeGod";
            MDUuid = MDUuid + uuid + userId;
            String activeCode = encrypt.MD5(MDUuid);
            acodeUpdate.put("userId", userId);
            acodeUpdate.put("activeCode", activeCode);
            userOperatemapper.updateCode(acodeUpdate);
            userInformation.setActive_code(activeCode);
            List<authorityVo> authorityVoList = userOperatemapper.listAuthority(userInformation.getUser_type_id());
            result.put("userInfo", userInformation);
            result.put("authorityVoList", authorityVoList);
            return result;
        }
        result.put("result", 0);
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
        int check = userOperatemapper.checkPwd(map);
        if (check == 1) {
            userOperatemapper.changePwd(map);
            userOperatemapper.updateUser(userId);
            result.put("result", 1);
            return result;
        }
        result.put("result", 0);
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
    public int checkToken(int userId, String token) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Map<Object, Object> result = new HashMap<Object, Object>();
        map.put("userId", userId);
        map.put("token", token);
        int check = userOperatemapper.checkToken(map);
        return check;
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
        userOperatemapper.updateUser(userId);
        result.put("result", 1);
        return result;
    }
}
