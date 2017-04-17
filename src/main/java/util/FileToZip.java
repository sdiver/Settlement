package util;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class FileToZip {
    private File zipFile;
    public FileToZip(String zipFilePath) {
        zipFile = new File(zipFilePath);
    }
    public void compress(String srcPathName, ZipOutputStream out) throws IOException {
        File srcdir = new File(srcPathName);
        if (srcdir.isDirectory()) {
            if (!srcdir.exists())
                throw new RuntimeException(srcPathName + "不存在！");

            Project prj = new Project();
            Zip zip = new Zip();
            zip.setProject(prj);
            zip.setDestFile(zipFile);
            FileSet fileSet = new FileSet();
            fileSet.setProject(prj);
            fileSet.setDir(srcdir);
            //fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹 eg:zip.setIncludes("*.java");
            //fileSet.setExcludes(...); 排除哪些文件或文件夹
            zip.addFileset(fileSet);
            zip.execute();
        }else{
            doCompress(srcdir, out);
        }
    }
    public void compress(String srcPathName) throws IOException {
        File srcdir = new File(srcPathName);
        if (srcdir.isDirectory()) {
            if (!srcdir.exists())
                throw new RuntimeException(srcPathName + "不存在！");

            Project prj = new Project();
            Zip zip = new Zip();
            zip.setProject(prj);
            zip.setDestFile(zipFile);
            FileSet fileSet = new FileSet();
            fileSet.setProject(prj);
            fileSet.setDir(srcdir);
            //fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹 eg:zip.setIncludes("*.java");
            //fileSet.setExcludes(...); 排除哪些文件或文件夹
            zip.addFileset(fileSet);
            zip.execute();
        }else{
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
            doCompress(srcdir, out);
        }
    }
    public static void doCompress(File file, ZipOutputStream out) throws IOException{
        System.out.println(out);
        System.out.println(1);
        if( file.exists() ){
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(file.getName()));
            int len = 0 ;
            // 读取文件的内容,打包到zip文件
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.closeEntry();
            fis.close();
        }
    }

}