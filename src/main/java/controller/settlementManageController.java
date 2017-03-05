package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.settlementManageService;
import service.userOperateService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**   
* @Title: settlementManageController
* @Package controller 
* @Description: settlementManageController.java
* @author Sdiver 18605916639_wo_cn   
* @date 2017/3/1 下午11:57 
* @version V1.0   
*/
@Controller
@RequestMapping({"/settlementManage"})
public class settlementManageController {
    private static Logger logger = LoggerFactory.getLogger(userOperateController.class);
    @Autowired
    private userOperateService userOperateservice;
    @Autowired
    private settlementManageService settlementManageservice;
    /**
     *@Title: listRegion
     *@Description: settlementManageController.java
     *@param: [request]
     *@return: java.util.Map<java.lang.Object_java.lang.Object>
     *@author: Sdiver
     *@Date: 2017/3/2 上午12:02
     */
    @RequestMapping(value = "/listRegion", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> listRegion(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementManageservice.listRegion();
            return map;
        }
        map.put("result", 0);
        return map;
    }
    /**
     *@Title: setUpCase
     *@Description: settlementManageController.java
     *@param: [request]
     *@return: java.util.Map<java.lang.Object_java.lang.Object>
     *@author: Sdiver
     *@Date: 2017/3/2 上午12:03
     */
    @RequestMapping(value = "/setUpCase", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> setUpCase(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int caseRegionId = Integer.parseInt(request.getParameter("caseRegionId"));
        int caseCellphoneNumber = Integer.parseInt(request.getParameter("caseCellphoneNumber"));
        int isHousehold = Integer.parseInt(request.getParameter("isHousehold"));
        int isOwner = Integer.parseInt(request.getParameter("isOwner"));
        int case_status = Integer.parseInt(request.getParameter("case_status"));
        String caseAddress = request.getParameter("caseAddress");
        String caseReporter = request.getParameter("caseReporter");
        String caseIdentity = request.getParameter("caseIdentity");
        String caseTime = request.getParameter("caseTime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date caseFormatTime = new Date(dateFormat.parse(caseTime).getTime());
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementManageservice.setUpCase(caseRegionId, caseCellphoneNumber, isHousehold, isOwner,
                    case_status, caseAddress, caseReporter, caseIdentity, caseFormatTime, userId);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    /**
     *@Title: forensicsUpload
     *@Description: settlementManageController.java
     *@param: [request]
     *@return: java.util.Map<java.lang.Object_java.lang.Object>
     *@author: Sdiver
     *@Date: 2017/3/2 上午12:03
     */
    @RequestMapping(value = "/forensicsUpload", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> forensicsUpload(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        String forensics = request.getParameter("forensics");
        String caseForensics = request.getParameter("caseForensics");
        int forensicsType = Integer.parseInt(request.getParameter("forensicsType"));
        String forensicsContext = request.getParameter("forensicsContext");
        int lostAmount = Integer.parseInt(request.getParameter("lostAmount"));
        int lostWeight = Integer.parseInt(request.getParameter("lostWeight"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementManageservice.forensicsUpload(forensics, caseForensics, forensicsType, forensicsContext,
                    lostAmount, lostWeight);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/forensicsUpdate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> forensicsUpdate(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        String forensics = request.getParameter("forensics");
        String caseForensics = request.getParameter("caseForensics");
        int forensicsId = Integer.parseInt(request.getParameter("forensicsId"));
        int forensicsType = Integer.parseInt(request.getParameter("forensicsType"));
        String forensicsContext = request.getParameter("forensicsContext");
        int lostAmount = Integer.parseInt(request.getParameter("lostAmount"));
        int lostWeight = Integer.parseInt(request.getParameter("lostWeight"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementManageservice.forensicsUpdate(forensics, caseForensics, forensicsId,
                    forensicsType, forensicsContext, lostAmount, lostWeight);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/deleteForensics", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> deleteForensics(HttpServletRequest request) throws Exception {
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        String caseForensics = request.getParameter("caseForensics");
        int forensicsType = Integer.parseInt(request.getParameter("forensicsType"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementManageservice.deleteForensics(caseForensics, forensicsType);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/deleteSettlement", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> deleteSettlement(HttpServletRequest request) throws Exception {
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int caseCode = Integer.parseInt(request.getParameter("caseCode"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementManageservice.deleteSettlement(caseCode);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/report", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> report(HttpServletRequest request) throws Exception {
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int caseCode = Integer.parseInt(request.getParameter("caseCode"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementManageservice.report(caseCode);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/sendBack", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> sendBack(HttpServletRequest request) throws Exception {
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int caseCode = Integer.parseInt(request.getParameter("caseCode"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementManageservice.sendBack(caseCode);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/caseExpected", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> caseExpected(HttpServletRequest request) throws Exception {
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int caseCode = Integer.parseInt(request.getParameter("caseCode"));
        String amountOfPay = request.getParameter("amountOfPay");
        String id = request.getParameter("id");
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementManageservice.caseExpected(caseCode, amountOfPay, id);
            return map;
        }
        map.put("result", 0);
        return map;
    }

}
