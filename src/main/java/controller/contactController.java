package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.contactService;
import service.userOperateService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping({"/contact"})
public class contactController {
    private static Logger logger = LoggerFactory.getLogger(userOperateController.class);
    @Autowired
    contactService contactservice;
    @Autowired
    userOperateService userOperateservice;
    @RequestMapping(value = "/addPoliceContact", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> addPoliceContact(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        int regionId = Integer.parseInt(request.getParameter("regionId"));
        int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
        String token = request.getParameter("token");
        String policeStationName = request.getParameter("policeStationName");
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = contactservice.addPoliceContact(regionId, phoneNumber, policeStationName);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/modifyPoliceContact", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> modifyPoliceContact(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int id = Integer.parseInt(request.getParameter("id"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        int regionId = Integer.parseInt(request.getParameter("regionId"));
        int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
        String token = request.getParameter("token");
        String policeStationName = request.getParameter("policeStationName");
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = contactservice.modifyPoliceContact(id, regionId, phoneNumber, policeStationName);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/deletePoliceContact", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> deletePoliceContact(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        int id = Integer.parseInt(request.getParameter("id"));
        String token = request.getParameter("token");
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = contactservice.deletePoliceContact(id);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/listContact", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> listContact(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = contactservice.listContact();
            return map;
        }
        map.put("result", 0);
        return map;
    }
}
