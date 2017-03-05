package mapper;

import model.authorityVo;
import model.user_info;

import java.util.List;
import java.util.Map;

/**   
* @Title: userOperateMapper
* @Package mapper 
* @Description: userOperateMapper.java
* @author Sdiver 18605916639_wo_cn   
* @date 2017/3/1 上午2:37 
* @version V1.0   
*/
public interface userOperateMapper {
    void register(Map<Object, Object> map);
    user_info login(Map<Object, Object> map);
    void updateCode(Map<Object, Object> map);
    List<authorityVo> listAuthority(int roleId);
    int checkPwd(Map<Object, Object> map);
    void changePwd(Map<Object, Object> map);
    int checkToken(Map<Object, Object> map);
    void modifyInfo(Map<Object, Object> map);
    void updateUser(int userId);
    user_info findUserByUserId(int userId);
}
