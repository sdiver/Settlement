package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.userOperateService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**   
* @Title: userOperateController
* @Package controller 
* @Description: user manager, including add users, modify users, search users.
* @author Sdiver 18605916639_wo_cn   
* @date 2017/2/28 上午1:54 
* @version V1.0   
*/
@Controller
@RequestMapping({"/userOperate"})
public class userOperateController {
    private static Logger logger = LoggerFactory.getLogger(userOperateController.class);
    @Autowired
    private userOperateService userOperateservice;

    /**
     *@Title: register
     *@Description: register user
     *@param: userName, userPassword, userChName, phoneNumber, userTypeId, workAreaId, workAddress
     *@return: java.util.Map<java.lang.Object,java.lang.Object>
     *@author: Sdiver
     *@Date: 2/28/17 3:08 PM
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> register(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        String userChName = request.getParameter("userChName");
        int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
        int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
        int workAreaId = Integer.parseInt(request.getParameter("workAreaId"));
        String workAddress = request.getParameter("workAddress");
        Map<Object, Object> map = userOperateservice.register(userName, userPassword, userChName, phoneNumber, userTypeId,
                workAreaId, workAddress);
        return map;
    }
    /**
     *@Title: login
     *@Description: login
     *@param: userName, userPassword
     *@return: java.util.Map<java.lang.Object,java.lang.Object>
     *@author: Sdiver
     *@Date: 2/28/17 3:39 PM
     */
    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public @ResponseBody Map<Object, Object> login(HttpServletRequest request) throws Exception{
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        Map<Object, Object> map = userOperateservice.login(userName, userPassword);
        return map;
    }
    /**
     *@Title: changePwd
     *@Description: changePwd
     *@param: [request]
     *@return: java.util.Map<java.lang.Object,java.lang.Object>
     *@author: Sdiver
     *@Date: 2/28/17 3:44 PM
     */
    @RequestMapping(value = {"/changePwd"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public @ResponseBody Map<Object, Object> changePwd(HttpServletRequest request) throws Exception{
        int userId = Integer.parseInt(request.getParameter("userId"));
        String userPassword = request.getParameter("userPassword");
        String newPassword = request.getParameter("newPassword");
        Map<Object, Object> map = userOperateservice.changePwd(userId, userPassword, newPassword);
        return map;
    }
    /**
     *@Title: checkToken
     *@Description: checkToken
     *@param: [request]
     *@return: java.util.Map<java.lang.Object,java.lang.Object>
     *@author: Sdiver
     *@Date: 2/28/17 3:48 PM
     */
    @RequestMapping(value = {"/checkToken"}, method = {RequestMethod.POST}, produces  = "application/json; charset=utf-8")
    public @ResponseBody int checkToken(HttpServletRequest request) throws Exception{
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int map = userOperateservice.checkToken(userId, token);
        return map;
    }
    /**
     *@Title: modifyInfo
     *@Description: modifyInfo
     *@param: [request]
     *@return: java.util.Map<java.lang.Object,java.lang.Object>
     *@author: Sdiver
     *@Date: 2/28/17 4:02 PM
     */
    @RequestMapping(value = {"/modifyInfo"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public @ResponseBody Map<Object, Object> modifyInfo(HttpServletRequest request) throws Exception{
        int userId = Integer.parseInt(request.getParameter("userId"));
        String phoneNumber = request.getParameter("phoneNumber");
        String workAddress = request.getParameter("workAddress");
        Map<Object, Object> map = userOperateservice.modifyInfo(userId, phoneNumber, workAddress);
        return map;
    }
}
