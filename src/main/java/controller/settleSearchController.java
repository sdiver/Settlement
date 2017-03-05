package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.settlementSearchService;
import service.userOperateService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping({"/settlementSearch"})
public class settleSearchController {
    private static Logger logger = LoggerFactory.getLogger(userOperateController.class);
    @Autowired
    userOperateService userOperateservice;
    @Autowired
    settlementSearchService settlementSearchservice;
    @RequestMapping(value = "/settleStatistics", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> settleStatistics(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date caseStartTime = null;
        if(request.getParameter("startTime") != null && request.getParameter("startTime") != "") {
            String startTime = request.getParameter("startTime");
            caseStartTime = new Date(dateFormat.parse(startTime).getTime());
        }
        Date caseEndTime = null;
        if(request.getParameter("endTime") != null && request.getParameter("endTime") != "") {
            String endTime = request.getParameter("endTime");
            caseEndTime = new Date(dateFormat.parse(endTime).getTime());
        }
        int regionId = Integer.parseInt(request.getParameter("regionId"));
        int forensicsType = Integer.parseInt(request.getParameter("forensicsType"));
        int status = Integer.parseInt(request.getParameter("status"));
        int Page = Integer.parseInt(request.getParameter("Page"));
        int pagePerNum = Integer.parseInt(request.getParameter("pagePerNum"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementSearchservice.settleStatistics(userId, caseStartTime, caseEndTime, regionId,
                    forensicsType, status, Page, pagePerNum);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/settleinfo", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> settleinfo(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int caseCode = Integer.parseInt(request.getParameter("caseCode"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = settlementSearchservice.settleinfo(caseCode, userId);
            return map;
        }
        map.put("result", 0);
        return map;
    }
}
