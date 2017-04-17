package util;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**   
* @Title: ImageUpload
* @Package util 
* @Description: ImageUpload.java
* @author Sdiver 18605916639_wo_cn   
* @date 2017/3/3 上午12:30 
* @version V1.0   
*/
public class ImageUpload {
    public Map<String,Object> upload(String picStr, String caseCode, String caseType) throws IOException{
        String casePath = globalV.MainUrl+caseCode+"/"+caseType;
        String miniCasePath = globalV.miniMainUrl+caseCode+"/"+caseType;
        File file =new File(casePath);
        if  (!file.exists()  && !file.isDirectory()){
            file.mkdirs();
        }
        File minifile =new File(miniCasePath);
        if  (!minifile.exists() && !minifile.isDirectory()){
            minifile.mkdirs();
        }
        picStr = picStr.replaceAll(" ","+");
        Map<String,Object> map = new HashMap<String,Object>();
        long nowTime = System.currentTimeMillis();
        OutputStream out = null;
        BASE64Decoder decoder = new BASE64Decoder();
        if (picStr != null){
            try {
                // Base64解码
                byte[] b = decoder.decodeBuffer(picStr);
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {// 调整异常数据
                        b[i] += 256;
                    }
                }
                String imgFilePath = casePath+"/"+nowTime+".jpg";// 新生成的图片
                out = new FileOutputStream(imgFilePath);
                out.write(b);
                out.flush();
                ImageScale is=new ImageScale();
                BufferedImage img = ImageIO.read(new File(imgFilePath));
                int width = img.getWidth(null); // 得到源图宽
                int height = img.getHeight(null); // 得到源图长
                BufferedImage img_scale = is.imageZoomOut(img, width / 8, height / 8, true);
                String imgFilePath2 = miniCasePath+"/mini"+nowTime+".jpg";// 新生成的图片
                FileOutputStream out2 = new FileOutputStream(imgFilePath2);
                ImageIO.write(img_scale,"jpg",out2);
                out2.flush();
                out2.close();
                String imgURL = globalV.url+caseCode+"/"+caseType+"/"+nowTime+".jpg";
                String minimgURL = globalV.miniurl+caseCode+"/"+caseType+"/mini"+nowTime+".jpg";
                map.put("MSG", "SUCCESS");
                map.put("URL", imgURL);
                map.put("miniURL", minimgURL);
            } catch (Exception e) {
                System.out.println("============================");
                System.out.println(e.toString());
                map.put("MSG", "FALSE");
            }finally{
                out.close();
            }
        }else{
            map.put("MSG", "no pic");
            map.put("URL", null);
            map.put("miniURL", null);
        }
        return map;
    }
}