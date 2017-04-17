package service.serviceImpl;

import mapper.newsMapper;
import model.newsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.newsService;
import util.ImageUpload;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("newsServiceImpl")
/**   
* @Title: newsServiceImpl
* @Package service.serviceImpl 
* @Description: newsServiceImpl.java
* @author Sdiver 18605916639_wo_cn   
* @date 2017/4/11 上午12:23 
* @version V1.0   
*/
public class newsServiceImpl implements newsService {
    @Autowired
    newsMapper newsmapper;
    /**
         *@Title: addPhoto
         *@Description: newsServiceImpl.java
         *@param: pic;orderBy
         *@return: result
         *@author: Sdiver
         *@Date: 2017/4/10 下午4:47
         */
    public Map<Object, Object> addPhoto(String pic) {
        ImageUpload imageupload = new ImageUpload();
        Map<String,Object> image = new HashMap<String,Object>();
        Map<Object,Object> result = new HashMap<Object,Object>();
        try {
            image = imageupload.upload(pic, "news", "news");
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String MSG = (String) image.get("MSG");
        if(MSG.equals("SUCCESS")){
            Map<Object,Object> info = new HashMap<Object,Object>();
            String photoUrl = (String) image.get("URL");
            info.put("photoUrl", photoUrl);
            newsmapper.addPhoto(info);
            result.put("result", 1);
            return result;
        }
        result.put("result",10);
        return result;
    }

    public Map<Object, Object> listPhoto() {
        List<newsInfo> newsinfo = newsmapper.listPhoto();
        Map<Object,Object> result = new HashMap<Object,Object>();
        result.put("newsinfo", newsinfo);
        return result;
    }

    public Map<Object, Object> deletePhoto(int id) {
        newsmapper.deletePhoto(id);
        Map<Object,Object> result = new HashMap<Object,Object>();
        result.put("result", 1);
        return result;
    }

    public synchronized Map<Object, Object> movePhoto(int upId, int downId) {
        int orderBy = newsmapper.getOrder(upId);
        int orderDownBy = newsmapper.getOrder(downId);
        Map<Object,Object> result = new HashMap<Object,Object>();
        Map<Object,Object> info = new HashMap<Object,Object>();
        Map<Object,Object> infotwo = new HashMap<Object,Object>();
        info.put("orderBy", orderDownBy);
        info.put("id", upId);
        infotwo.put("orderBy", orderBy);
        infotwo.put("id", downId);
        newsmapper.updateOrder(info);
        newsmapper.updateOrder(infotwo);
        result.put("result", 1);
        return result;
    }
}
