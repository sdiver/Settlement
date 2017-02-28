package mapper;

import model.user_info;

import java.util.Map;

/**
 * Created by sdiver on 2/28/17.
 */
public interface userOperateMapper {

    void register(Map<Object, Object> map);

    user_info login(Map<Object, Object> map);

    void changePwd(Map<Object, Object> map);

    void checkToken(Map<Object, Object> map);

    void modifyInfo(Map<Object, Object> map);
}
