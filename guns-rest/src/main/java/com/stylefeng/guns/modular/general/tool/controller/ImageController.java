package com.stylefeng.guns.modular.general.tool.controller;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.MapItemFactory;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.core.util.IdGenerator;
import com.stylefeng.guns.core.util.ToolUtil;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.rest.common.ReturnTip;

/**
 * 俱乐部管理员控制器
 *
 * @author guiyj007
 * @Date 2018-06-25 10:24:28
 */
@Controller
@RequestMapping("/general/tool/image")
public class ImageController extends BaseController {
    /**
     * 图片上传
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public Object upload(@RequestParam("file") CommonsMultipartFile file)throws IOException {
    	/**
    	 * 验证登录 token信息
    	 */
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	if (ToolUtil.isEmpty(clubAdmin.getId())) {
    		return new ReturnTip(500, "上传来源不合法");
    	}
    	
    	// 判断上传的文件是否为空
    	if (file == null) {
    		return new ReturnTip(501, "未识别到上传文件");
    	}
        String type = null;// 文件类型
        String fileName = file.getOriginalFilename();// 文件原名称
        // 判断文件类型
        type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".")+1) : null;
        
        // 判断文件类型是否为空
        if (type == null) {
        	return new ReturnTip(501, "文件类型为空");
        }
        
        if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPEG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
            // 项目在容器中实际发布运行的根路径
//            String realPath = request.getSession().getServletContext().getRealPath("/");
            
        	// 自定义的文件名称
            String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
            // 设置存放图片文件的路径
            Map<String, Object> generatingFileInfo = FileUtil.generateFilePath(trueFileName);
            String path = (String) generatingFileInfo.get("path");
            String url = (String) generatingFileInfo.get("url");
 
            System.out.println("存放图片文件的路径:"+path);
            // 转存文件到指定的路径
            file.transferTo(new File(path));
            
        	Map<String, Object> mapRet = MapItemFactory.composeMap("fileType", type, "height", 75, "width", 75, "url", url);
        	return new ReturnTip(0, "成功", mapRet);
        }else {
        	return new ReturnTip(501, "非正常的图片类型");
        }
        
//    	String url = "http://upyun.newugo.cn/club1390/logo/153179940662978.jpg";
    }
    
    /**
     * 图片上传
     */
    @RequestMapping(value = "/base64UpLoad")
    @ResponseBody
    public Object base64UpLoad(@RequestParam String file) throws Exception {
    	String base64Data = file;
    	try{  
            String dataPrix = "";
            String data = "";

            if(base64Data == null || "".equals(base64Data)){
                throw new Exception("上传失败，上传图片数据为空");
            }else{
                String [] d = base64Data.split("base64,");
                if(d != null && d.length == 2){
                    dataPrix = d[0];
                    data = d[1];
                }else{
                    throw new Exception("上传失败，数据不合法");
                }
            }

            String suffix = "";
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = ".jpg";
            } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
                suffix = ".ico";
            } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            }else{
                throw new Exception("上传图片格式不合法");
            }
            
         // 自定义的文件名称
            String trueFileName = IdGenerator.getTimeId() + suffix;
            
            // 设置存放图片文件的路径
            Map<String, Object> generatingFileInfo = FileUtil.generateFilePath(trueFileName);
            String path = (String) generatingFileInfo.get("path");
            String url = (String) generatingFileInfo.get("url");
 
            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            byte[] bs = Base64Utils.decodeFromString(data);
            
            try{
                //使用apache提供的工具类操作流
            	FileUtils.writeByteArrayToFile(new File(path), bs);
            }catch(Exception ee){
                throw new Exception("上传失败，写入文件失败，"+ee.getMessage());
            }
            
            Map<String, Object> mapRet = MapItemFactory.composeMap("url", url, "fileType", suffix.substring(1), "height", 75, "width", 75);
        	return new ReturnTip(0, "成功", mapRet);
        }catch (Exception e) {  
        	return new ReturnTip(501, "上传失败");
        }  
        
    }
    
}
