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
* @Description: userOperateController.java
* @author Sdiver 18605916639_wo_cn   
* @date 2017/2/28 上午1:54 
* @version V1.0   
*/
@Controller
@RequestMapping({"/userOperate"})
public class userOperateController {
    private static Logger logger = LoggerFactory.getLogger(userOperateController.class);
    @Autowired
    userOperateService userOperateservice;
    @RequestMapping(value = {"/"}, method = {RequestMethod.POST}, produces = {"application/json; charset = utf-8"})
    @ResponseBody
    public Map<Object, Object> login(HttpServletRequest request) throws Exception{


        return null;
    }
}
