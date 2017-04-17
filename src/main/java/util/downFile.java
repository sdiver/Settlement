package util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class downFile {
    public void downloadFile(String path, HttpServletResponse response) throws IOException {
        File file = new File(path);
        String filename = file.getName();
        InputStream fis = new BufferedInputStream(new FileInputStream(path));
        byte[] buff = new byte[fis.available()];
        fis.read(buff);
        fis.close();
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(
                filename.replace(" ","").getBytes("utf-8"),"iso8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        os.write(buff);
        os.flush();
        os.close();
    }

    public void deleteFile(File file){
        boolean i;
        if(file.exists()){
            if(file.isFile()){
                i = file.delete();
                System.out.println(i);
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                assert files != null;
                for (File file1 : files) {
                    this.deleteFile(file1);
                }
                i = file.delete();
                System.out.println(i);
            }
        } else {
            System.out.println("file not exist");
        }
    }
}
