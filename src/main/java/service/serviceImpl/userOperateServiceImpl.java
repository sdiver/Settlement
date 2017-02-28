package service.serviceImpl;

import mapper.userOperateMapper;
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
    encryption encrypt = new encryption();
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
        return null;
    }
    /**
     *@Title: userOperateServiceImpl
     *@Package: service.serviceImpl
     *@Description: userOperateServiceImpl.java
     *@author: Sdiver mail 18605916639_wo_cn
     *@date: 2/28/17
     *@version: V1.0
     */
    public Map<Object, Object> changePwd(String userId, String userPassword, String newPassword) {
        return null;
    }
    /**
     *@Title: userOperateServiceImpl
     *@Package: service.serviceImpl
     *@Description: userOperateServiceImpl.java
     *@author: Sdiver mail 18605916639_wo_cn
     *@date: 2/28/17
     *@version: V1.0
     */
    public Map<Object, Object> checkToken(String userId, String token) {
        return null;
    }
    /**
     *@Title: userOperateServiceImpl
     *@Package: service.serviceImpl
     *@Description: userOperateServiceImpl.java
     *@author: Sdiver mail 18605916639_wo_cn
     *@date: 2/28/17
     *@version: V1.0
     */
    public Map<Object, Object> modifyInfo(String userId, String phoneNumber, String workAddress) {
        return null;
    }
}
