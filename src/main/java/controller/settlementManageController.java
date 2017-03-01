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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sdiver on 3/1/17.
 */
@Controller
@RequestMapping({"/settlementManage"})
public class settlementManageController {
    private static Logger logger = LoggerFactory.getLogger(userOperateController.class);
    @Autowired
    private userOperateService userOperateservice;
    @Autowired
    private settlementManageService settlementManageservice;
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
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementManageservice.setUpCase(caseRegionId, caseCellphoneNumber, isHousehold, isOwner,
                    case_status, caseAddress, caseReporter, caseIdentity, caseTime, userId);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/forensicsUpload", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> forensicsUpload(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        String forensics = request.getParameter("forensics");
        String caseForensics = request.getParameter("caseForensics");
        String forensicsType = request.getParameter("forensicsType");
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
}
