package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.newsService;
import service.userOperateService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**   
* @Title: newsController
* @Package controller 
* @Description: newsController.java
* @author Sdiver 18605916639_wo_cn   
* @date 2017/4/6 下午3:13 
* @version V1.0   
*/
@Controller
@RequestMapping({"/news"})
public class newsController {
    private static Logger logger = LoggerFactory.getLogger(userOperateController.class);
    @Autowired
    newsService newsservice;
    @Autowired
    userOperateService userOperateservice;
    /**
         *@Title: addPhoto
         *@Description: newsController.java
         *@param: pic;orderBy
         *@return: result
         *@author: Sdiver
         *@Date: 2017/4/10 下午4:41
         */
    @RequestMapping(value = "/addPhoto", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> addPhoto(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        String pic = request.getParameter("pic");
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = newsservice.addPhoto(pic);
            return map;
        }
        map.put("result", 0);
        return map;
    }
/**
     *@Title: listPhoto
     *@Description: newsController.java
     *@param: --
     *@return: result
     *@author: Sdiver
     *@Date: 2017/4/10 下午10:21
     */
    @RequestMapping(value = "/listPhoto", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> listPhoto(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = newsservice.listPhoto();
            return map;
        }
        map.put("result", 0);
        return map;
    }
/**
     *@Title: deletePhoto
     *@Description: newsController.java
     *@param: id
     *@return: result
     *@author: Sdiver
     *@Date: 2017/4/10 下午10:55
     */
    @RequestMapping(value = "/deletePhoto", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> deletePhoto(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int id = Integer.parseInt(request.getParameter("id"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = newsservice.deletePhoto(id);
            return map;
        }
        map.put("result", 0);
        return map;
    }
    @RequestMapping(value = "/movePhoto", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<Object, Object> movePhoto(HttpServletRequest request) throws Exception {
        logger.debug("TEST");
        Map<Object, Object> map = new HashMap<Object, Object>();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String token = request.getParameter("token");
        int upId = Integer.parseInt(request.getParameter("upId"));
        int downId = Integer.parseInt(request.getParameter("downId"));
        int check = userOperateservice.checkToken(userId, token);
        if(check == 1) {
            map = newsservice.movePhoto(upId, downId);
            return map;
        }
        map.put("result", 0);
        return map;
    }
}
