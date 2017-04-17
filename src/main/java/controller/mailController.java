package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.mailsService;
import service.userOperateService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping({"/mail"})
public class mailController {
    private static Logger logger = LoggerFactory.getLogger(userOperateController.class);
    @Autowired
    mailsService mailservice;
    @Autowired
    userOperateService userOperateservice;
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> send(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        String title = request.getParameter("title");
        String sendUserId = request.getParameter("sendUserId");
        String context = request.getParameter("context");
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = mailservice.send(title, sendUserId, context);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/view", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> view(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = mailservice.view(userId);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/mailInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> mailInfo(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int id = Integer.parseInt(request.getParameter("id"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = mailservice.mailInfo(id);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/deleteMail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> deleteMail(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int id = Integer.parseInt(request.getParameter("id"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = mailservice.deleteMail(id);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/addAppendix", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> addAppendix(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = mailservice.addAppendix(request);
            return map;
    }
    @RequestMapping(value = "/deleteAppendix", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> deleteAppendix(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int id = Integer.parseInt(request.getParameter("id"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = mailservice.deleteAppendix(id);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/listRegion", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> listRegion(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = mailservice.listmailRegion();
            return map;
        }
        map.put("result", 0);
        return map;
    }
}
