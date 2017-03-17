package com.jizhi.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.image.Thumbnailator;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.DateUtil;
import com.simple.common.util.FileUploadUtil;
import com.simple.common.util.PrimaryKeyUtil;
@Controller
@RequestMapping(value = "/image")
public class ImageUploadController {
	
	private static final Logger log = LoggerFactory.getLogger(ImageUploadController.class);

	@RequestMapping(value="/uploadFile",method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(">>>>"+DateUtil.date2AllString(new Date()));
		try {
			//List<String> images = new ArrayList<String>();
			String imagepath = null;
			for (MultipartFile file:files ) {
				long timestart = System.currentTimeMillis();
//				String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
//				if (StringUtils.isEmpty(suffix)) {
//					suffix = request.getParameter("suffix");
//				}
//				suffix="jpg";
				String imagewidth = request.getParameter("width");
				String foler = FileUploadUtil.UPLOAD_IMAGE_DIR +System.currentTimeMillis();
				File folerfile = new File(EnvPropertiesConfiger.getValue("uploadDir")+foler);
				if (!folerfile.exists()) {
					folerfile.mkdirs();
				}
				String filepath = foler+ File.separator + PrimaryKeyUtil.getUUID() ;
				String path = filepath+".jpg";
				Thumbnailator.scale(file.getInputStream(), 1920, 1080, 0.4f, 0, EnvPropertiesConfiger.getValue("uploadDir")+filepath+".jpg");
				long time1 = System.currentTimeMillis();
				System.out.println(">>>>img time1 : "+(time1-timestart));
				if (null != imagewidth) {
					int width = 0;
					try {
						width = Integer.parseInt(imagewidth);
					}catch(Exception e) {
					}
					if (width>0) {
						Thumbnailator.cutSquareImage(new File(EnvPropertiesConfiger.getValue("uploadDir")+path), width, width, 0.8f, 0, EnvPropertiesConfiger.getValue("uploadDir")+filepath+"_"+width+".jpg");
						path = filepath+"_"+width+".jpg";
					}
				}
				System.out.println(">>>>img time2 : "+(System.currentTimeMillis()-time1));
				//images.add(path);
				imagepath = path;
//				File f = new File(foler+ File.separator + PrimaryKeyUtil.getUUID() + "." + suffix);
//				file.transferTo(f);//此处会生成图片到磁盘
//				//先上传一张背景为黑色的图片
//				String blackFile = ImageHandleUtil.img_change(f,null, false, false);
//				//File f = FileUploadUtil.getFileByInputStream(file.getInputStream(), suffix, FileUploadUtil.UPLOAD_IMAGE_DIR);
//				String path = blackFile.replace(EnvPropertiesConfiger.getValue("uploadDir"), "");
//				if ( null != imagewidth) {
//					int width = 0;
//					try {
//						width = Integer.parseInt(imagewidth);
//					}catch(Exception e) {
//					}
//					if (width>0) {
//						//再上传一张背景为白色的图片
//						//String whiteFile = ImageHandleUtil.img_change(f,"w", false, true);
//						ImageHandleUtil.cutImage(new File(blackFile), width);
//						path = ImageHandleUtil.getScaleFilePath(blackFile.replace(EnvPropertiesConfiger.getValue("uploadDir"), ""),width);
//					}
//				}
//				images.add(path);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"上传成功", imagepath);
		}catch(Exception e) {
			log.error("上传失败",e);
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"上传失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
}
