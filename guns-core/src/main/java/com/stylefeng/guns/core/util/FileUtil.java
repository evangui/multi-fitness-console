package com.stylefeng.guns.core.util;

import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * NIO way
     */
    public static byte[] toByteArray(String filename) {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
            throw new GunsException(GunsExceptionEnum.FILE_NOT_FOUND);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw new GunsException(GunsExceptionEnum.FILE_READING_ERROR);
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                throw new GunsException(GunsExceptionEnum.FILE_READING_ERROR);
            }
            try {
                fs.close();
            } catch (IOException e) {
                throw new GunsException(GunsExceptionEnum.FILE_READING_ERROR);
            }
        }
    }

    /**
     * 删除目录
     *
     * @author guiyj007
     * @Date 2017/10/30 下午4:15
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    
    /**
     * 生成统一文件存储路径
     * @return string
     */
    public static Map<String, Object> generateFilePath(String filename) {
    	Date date = new Date();
    	String dir = "public/" + DateFormatUtils.format(date, "yyyyMM") + "/" + DateFormatUtils.format(date, "dd") + "/";

    	String projectFleDir = SpringContextHolder.getApplicationContext().getEnvironment().getProperty("project.filedir.path");
    	File fileNewDir = new File(projectFleDir + dir);
    	
    	//如果文件夹不存在，则创建
    	if (!fileNewDir.exists()) {
    		fileNewDir.mkdirs();
    	}
    	
    	Map<String, Object> mapRet = new HashMap<String, Object>();
    	mapRet.put("path", projectFleDir + dir + filename);
    	mapRet.put("url", getFileUrl(dir + filename, false));
    	mapRet.put("relativePath", dir + filename);
    	mapRet.put("projectFleDir", projectFleDir);
    	mapRet.put("filename", filename);
    	
    	return mapRet;
    }
    
    /**
     * 生成文件url
     * @param string $filePath storage相对路径
     * @return string
     */
    public static String getFileUrl(String filePath, Boolean completeMode) {
    	if (completeMode == null) {
    		completeMode = false;
    	}
    	
    	String projectFleDirUrl = SpringContextHolder.getApplicationContext().getEnvironment().getProperty("project.filedir.url");
    	if (completeMode) {
    		String projectFleDir = SpringContextHolder.getApplicationContext().getEnvironment().getProperty("project.filedir.path");
    		filePath = filePath.substring(filePath.lastIndexOf(projectFleDir) + 1);
    	}
    	return projectFleDirUrl + filePath;
    }
}