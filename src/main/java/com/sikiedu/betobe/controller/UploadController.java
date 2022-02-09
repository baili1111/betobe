package com.sikiedu.betobe.controller;

import biz.source_code.base64Coder.Base64Coder;
import com.sikiedu.betobe.domain.User;
import com.sikiedu.betobe.service.UserService;
import com.sikiedu.betobe.utils.BaseDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

/**
 * @author zhu
 * @date 2021/4/24 11:59:13
 * @description
 */
@Controller
public class UploadController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private BaseDataUtils baseDataUtils;

	@RequestMapping("/uploadCoverAJAX")
	@ResponseBody
	public String uploadCoverAJAX(String base64) throws IOException {

		// 获得classpath在本地的地址
		String path = ResourceUtils.getURL("classpath:").getPath() + "static/upload/blog/cover";
		// 保存图片
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 替换数据
		if (base64.contains("jpeg")) {
			base64 = base64.replaceFirst("jpeg", "jpg");
		}
		// 获取文件名
		String upName = UUID.randomUUID().toString() + System.currentTimeMillis() + "." + base64.substring(11, 14);
		// 将base64中有用的数据保存下来
		String iconBase64 = base64.substring(22);
		// 将base64换为字节数据
		byte[] buffer = Base64Coder.decode(iconBase64);
		// 用FileOutputStream写文件
		FileOutputStream outputStream = new FileOutputStream(path + "/" + upName);
		outputStream.write(buffer);
		outputStream.close();
		// 返回Json
		String json = "{\"success\":"+true+",\"fileName\":\""+upName+"\"}";
		return json;
	}

	/**
	 * 博客上传封面进行预览，等待切割
	 * 富文本上传图片
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/uploadImageAJAX")
	@ResponseBody
	public String uploadImageAJAX(@RequestParam("file")MultipartFile file) throws IOException {

		// 保存图片

		// 创建文件
		String path = ResourceUtils.getURL("classpath:").getPath() + "static/upload/blog/temp";
		String name = file.getOriginalFilename();
		//String name = UUID.randomUUID().toString() + ".jpg";
		File dest = new File(path + "/" + name);
		// 如果路径不存在，创建路径
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		// 保存文件
		file.transferTo(dest);
		//String json = "{\"success\":" + true + "}";

		String json = "{\"success\":"+true+",\"summernoteImage\":\"upload/blog/temp/"+name+"\"}";

		return json;
	}

	/**
	 * 将裁剪后的头像或者背景保存
	 * @param base64 裁剪后的头像或背景
	 * @param type 根据类型确定保存的地方
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadImage")
	public String uploadImage(String base64, String type) throws Exception {
		//System.out.println(base64);
		User user = (User) httpSession.getAttribute("user");
		String path = null;
		// 获取classpath在本地的地址
		if ("cover".equals(type)){
			path = ResourceUtils.getURL("classpath:").getPath() + "static/upload/user/cover";
		} else {
			path = ResourceUtils.getURL("classpath:").getPath() + "static/upload/user/head";
		}
		// 如果文件夹不存在，创建文件夹
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 替换数据
		if (base64.contains("jpeg")) {
			base64 = base64.replaceFirst("jpeg", "jpg");
		}
		// 文件名
		String upName = UUID.randomUUID().toString()+System.currentTimeMillis()+"."+base64.substring(11, 14);
		// 将base64中有用的数据保存下来
		String iconBase64 = base64.substring(22);
		// 将base64换为字节数据
		byte[] buffer = Base64Coder.decode(iconBase64);

		// FileOutputStream(String name)
		// 创建一个向具有指定名称的文件中写入数据的输出文件流。
		FileOutputStream out = new FileOutputStream(path + "/" + upName);
		out.write(buffer);
		out.close();
		//System.out.println(path + "/" + upName);
		// 保存到数据库

		if ("cover".equals(type)) {
			user.setCoverImage("user/cover/" + upName);
		} else {
			user.setHeadImage("user/head/" + upName);
		}

		userService.saveUser(user);

		return "redirect:/findUserProfileSettingsById?id=" + user.getId();
	}

	/**
	 * 上传头像或者背景等待裁剪
	 * @param file 头像或者背景
	 * @param model 放置数据回显
	 * @param type 上传的是封面还是头像
	 * @return cropper.html
	 * @throws Exception
	 */
	@RequestMapping("/cropper")
	public String cropper(@RequestParam("file")MultipartFile file, Model model, String type) throws Exception {

		// 保存图片

		// 创建文件
		String path = ResourceUtils.getURL("classpath:").getPath() + "static/upload/user/temp";
		System.out.println(path);
		// 如果路径不存在，创建路径
		File dest = new File(path + "/" + file.getOriginalFilename());
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		// 保存文件
		file.transferTo(dest);


		// 用于页面的cropper插件回显图片
		model.addAttribute("fileName", file.getOriginalFilename());


		User user = (User) httpSession.getAttribute("user");
		// 查看当前用户有几个人关注他
		Integer followersNum = userService.findFollowersNumById(user.getId().toString());
		// request域和session域不冲突
		model.addAttribute("user", user);
		model.addAttribute("followersNum", followersNum);
		model.addAttribute("page", "AboutMe");
		baseDataUtils.getData(model, user.getId().toString());
		model.addAttribute("type", type);
		return "cropper.html";
	}
}
