package cn.ggdo.system.framework.tools.zipped;

import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.util.Enumeration;  
import java.util.zip.ZipException;  
  
import org.apache.tools.zip.ZipEntry;  
import org.apache.tools.zip.ZipFile;  
import org.apache.tools.zip.ZipOutputStream;  
  
/**
 * 类名：Zip 
 * 版本：1.0.0
 * 用途说明：压缩方法
 * 业务区分(压缩方法)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class Zip {  
    /** 
     * 压缩方法 
     * @param srcName 源文件名称 
     * @param targetName 目标文件名称 
     * @throws IOException  
     */  
    public  void compress(String srcName, String targetName) throws IOException{  
        System.out.println("===========开始压缩===========");  
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(targetName));//设置输出  
        File src = new File(srcName);//锁定要压缩的文件或文件夹  
        this.realCompress(zout, src, "");//调用真正压缩方法  
        zout.close();  
        System.out.println("===========压缩完成===========");  
    }  
  
    private void realCompress(ZipOutputStream zout, File src, String pathName) throws IOException{  
        if(src.isDirectory()){//文件夹  
            File[] files = src.listFiles();//遍历该文件夹下所有的文件或文件夹  
            zout.putNextEntry(new ZipEntry(pathName + "/"));//将这些文件添加到要压缩的文件中  
            pathName = pathName.length() == 0 ? "" : pathName + "/";  
            for(int i = 0; i < files.length; i++){  
                this.realCompress(zout, files[i], pathName + files[i].getName());//递归调用自己，继续遍历  
            }  
        }else{//文件  
            if("".equals(pathName)){//如果开始文件不是目录，那么一定是压缩单一文件  
                File f = new File(src.getAbsolutePath());  
                FileInputStream fis = new FileInputStream(f);  
                BufferedInputStream bis = new BufferedInputStream(fis);  
                byte[] buf = new byte[1024];  
                int len;  
                FileOutputStream fos = new FileOutputStream("D:\\1.zip");  
                BufferedOutputStream bos = new BufferedOutputStream(fos);  
                ZipOutputStream zos = new ZipOutputStream(bos);//压缩包  
                ZipEntry ze = new ZipEntry(f.getName());//这是压缩包名里的文件名  
                zos.putNextEntry(ze);//写入新的 ZIP 文件条目并将流定位到条目数据的开始处  
                while((len=bis.read(buf))!=-1){  
                    zos.write(buf,0,len);  
                    zos.flush();  
                }  
                bis.close();  
                zos.close();   
            }else{  
                zout.putNextEntry(new ZipEntry(pathName));  
                FileInputStream in = new FileInputStream(src);//建立字节流  
                int b;  
                System.out.println("压缩：" + pathName);  
                while((b = in.read()) != -1){  
                    zout.write(b);  
                }  
                in.close();  
            }  
        }  
    }  
  
    /** 
     * 解压缩文件 
     * @param srcName 源文件名称 
     * @param targetName 目标文件名称 
     * @throws IOException  
     */  
    @SuppressWarnings("unchecked")  
    public void unCompress(String srcName, String targetName) throws IOException{  
        System.out.println("=============解压开始=============");  
        ZipFile zipFile = new ZipFile(srcName);//创建一个ZIPfile对象  
        Enumeration e = zipFile.getEntries();  
        ZipEntry zipEntry = null;  
        File dest = new File(targetName);//要创建的目录  
        dest.mkdirs();//创建目录（该目录为根目录）  
        while(e.hasMoreElements()){  
            zipEntry = (ZipEntry)e.nextElement();//转为ZipEnrty对象  
            this.unRealCompress(zipEntry, targetName, zipFile);  
        }  
        System.out.println("=============解压结束=============");  
    }  
  
    private void unRealCompress(ZipEntry zipEntry,String targetName, ZipFile zipFile) throws ZipException, IOException{  
        String entryName = zipEntry.getName();//获取文件名称  
        if(zipEntry.isDirectory()){  
            entryName = entryName.substring(0,entryName.length() - 1);  
            File f = new File(targetName + File.separator + entryName);//根目录+"/"+文件名  
            f.mkdirs();//创建该文件路径  
        }else{  
            int index = entryName.lastIndexOf("\\");  
            if(index != -1){  
                File df = new File(targetName + File.separator + entryName.substring(0,index));  
                df.mkdirs();  
            }  
            index = entryName.lastIndexOf("/");  
            if(index != -1){  
                File df = new File(targetName + File.separator + entryName.substring(0,index));  
                df.mkdirs();  
            }  
            File f = new File(targetName + File.separator + zipEntry.getName());  
  
            InputStream in = zipFile.getInputStream(zipEntry);  
            OutputStream out = new FileOutputStream(f);  
  
            int c;  
            byte[] by = new byte[1024];  
  
            while ((c = in.read(by)) != -1) {  
                out.write(by, 0, c);  
            }  
            out.flush();  
            System.out.println("解压：" + f.getAbsolutePath());  
            in.close();  
            out.close();  
        }  
    }  
  
    public static void main(String[] args) {  
        try {  
            new Zip().compress("D:\\IO操作", "D:\\IO操作.zip");  
            new Zip().unCompress("D:\\IO操作.zip", "D:\\新IO操作");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  
