package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.Comment;
import com.sikiedu.betobe.service.CommentService;
import com.sikiedu.betobe.service.VideoService;
import com.sikiedu.betobe.utils.BaseDataUtils;
import com.sikiedu.betobe.utils.PageBean;
import com.sikiedu.betobe.utils.SendEmailManager;
import com.sikiedu.betobe.domain.User;
import com.sikiedu.betobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author zhu
 * @date 2021/4/5 16:57:22
 * @description
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpSession session;

	@Autowired
	private CommentService commentService;

	@Autowired
	private BaseDataUtils baseDataUtils;

	@Autowired
	private VideoService videoService;

	/**
	 * 登录后跳转的界面上填写用户资料
	 * 也可自行去修改
	 * profile-settings.html
	 *
	 * @param user
	 * @return
	 */
	@RequestMapping("/updateUser")
	public String updateUser(User user){

		User userInSession = (User) session.getAttribute("user");
		//System.out.println(user);
		// 更新user

		userInSession.setFirstName(user.getFirstName());
		userInSession.setLastName(user.getLastName());
		userInSession.setUsername(user.getUsername());
		userInSession.setDisplayName(user.getDisplayName());

		userInSession.setPassword(user.getPassword());

		userInSession.setEmail(user.getEmail());
		userInSession.setWebUrl(user.getWebUrl());
		userInSession.setPhone(user.getPhone());
		userInSession.setDescription(user.getDescription());

		userInSession.setQqLink(user.getQqLink());
		userInSession.setWeixinLink(user.getWeixinLink());

		userService.saveUser(userInSession);

		return "redirect:/findUserProfileSettingsById?id=" + userInSession.getId();
	}

	/**
	 * 查找该用户正在浏览哪一位用户的主页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findUserAboutMeById")
	public String findUserAboutMeById(String id, Model model) {
		baseDataUtils.getData(model, id);
		model.addAttribute("page", "AboutMe");
		return "profile-about-me.html";
	}
	@RequestMapping("/findUserVideosById")
	public String findUserVideoById(String id, Model model, Integer currentPage) {
		baseDataUtils.getData(model, id);

		// 给页面pageBean
		PageBean videoPageBean = videoService.getNormalPageBeanByUserId(currentPage, 8, id);

		Random r = new Random();
		session.setAttribute("videoCode", r.nextInt(60000));


		model.addAttribute("videoPageBean", videoPageBean);
		model.addAttribute("userId", id);
		model.addAttribute("page", "Videos");
		return "profile-video.html";
	}
	@RequestMapping("/findUserFavoriteVideosById")
	public String findUserFavoriteVideosById(String id, Model model, Integer currentPage) {
		baseDataUtils.getData(model, id);

		// 查询用户喜欢的所有视频
		PageBean videoPageBean = videoService.getNormalUserLikeVideoPageBeanByUserId(currentPage, 8, id);

		model.addAttribute("videoPageBean", videoPageBean);
		model.addAttribute("userId", id);
		model.addAttribute("page", "FavoriteVideos");
		return "profile-favorite.html";
	}
	@RequestMapping("/findUserFollowersById")
	public String findUserFollowersById(String id, Model model, Integer currentPage) {
		baseDataUtils.getData(model, id);

		PageBean userPageBean = userService.getUserFollowerPageBeanByUserId(currentPage, 12, id);

		// 粉丝的数量

		// 粉丝的信息，分页

		model.addAttribute("userPageBean", userPageBean);
		model.addAttribute("userId", id);
		model.addAttribute("page", "Followers");
		return "profile-followers.html";
	}

	/**
	 * 用账号密码登录的时候，跳转到这个请求，这时id 是 登录人 id， 所以下面的request域中 user就是session域中的user
	 * 当在url中http://www.pinzhi365.com/findUserCommentsById?id=12修改的时候， 相当于访问别人的主页，request域中的user就是别人
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findUserProfileSettingsById")
	public String findUserProfileSettingsById(String id, Model model) {
		User user = userService.findUserById(id);
		// 查找所有评论这个用户的评论
		List<Comment> commentList = commentService.findCommentListByCommentUserId(id);
		model.addAttribute("commentList", commentList);
		// 查看当前用户有几个人关注他
		Integer followersNum = userService.findFollowersNumById(id);
		model.addAttribute("user", user);
		model.addAttribute("followersNum", followersNum);
		model.addAttribute("page", "ProfileSettings");
		return "profile-settings.html";
	}


	/**
	 * 添加关注
	 * 登录者查看别人的主页关注
	 * @param id 登录人
	 * @param followId 被登录人点击的主页
	 * @return
	 */
	@RequestMapping("/addFollows")
	@ResponseBody
	public String addFlows(String id, String followId) {

		User user = userService.findUserById(id);
		User followUser = userService.findUserById(followId);

		String json = null;

		// 不允许自己关注自己
		if (user.getUsername().equals(followUser.getUsername())) {
			json = "{\"success\":" + false + "}";
			return json;
		}

		// 在id的follow集合中添加关系
		user.getFollows().add(followUser);
		// 保存关系
		userService.saveUser(user);
		json = "{\"success\":" + true + "}";

		return json;
	}

	/**
	 * 忘记密码的请求，根据phone和email确定用户账号，然后修改重置密码
	 * 发送邮件通知用户密码为随机的6位数
	 * @param phone 手机号
	 * @param email 邮箱号
	 * @param model 用户输错信息时，用于回显数据在页面上
	 * @return 转发到login-forgot-pass.html页面
	 */
	@RequestMapping("/forgotPassword")
	public String forgotPassword(String phone, String email, Model model) {

		// 根据phone查询用户
		User user = userService.findUserByPhone(phone);
		if (user != null) {
			// 找到了
			// 判断邮箱是否正确
			if (user.getEmail().equals(email)) {

				// 正确
				// 发送邮件重置密码
				Random random = new Random();
				String password = "";
				for (int i = 0; i < 6; i++) {
					password += random.nextInt(10);
				}

				// 发送邮件
				SendEmailManager d = new SendEmailManager(email, "baili reset password",
						"你好：<br/><br/><p>重置密码为：</p><br/><p style='color:red;'>" + password + "</p>");
				d.start();

				// 同步到数据库
				userService.changeUserPasswordByPhoneAndEmail(phone, email, password);

			} else {

				// 不正确
				// 邮箱错误，回显phone
				model.addAttribute("error", "Error in email");
				model.addAttribute("phone", phone);
				return "login-forgot-pass.html";
			}


		} else {

			// 没找到
			// 手机号错误，回显email
			model.addAttribute("error", "Error in Phone");
			model.addAttribute("email", email);
			return "login-forgot-pass.html";
		}

		return "redirect:/loginBetobe";
	}

	/**
	 * 注册用户的请求
	 * @param user 前端提交数据时，会将form表单的对应字段封装到user对象里面
	 * @return 转发到登录页面
	 */
	@RequestMapping("/register")
	public String register(User user) {

		// 用户名
		//private String username;
		//private String password;
		//private String email;

		//private String firstName;
		//private String lastName;
		// 显示名称
		//private String diaplayName;

		// 个人首页
		//private String webUrl;
		// 电话
		//private String phone;
		user.setPhone(user.getUsername());
		// 个人描述
		//private String description;

		// social Link
		//private String qqLink;
		//private String weixinLink;

		// 封面图片
		//private String coverImage;
		user.setCoverImage("user/cover/cover.jpg");
		// 头像
		//private String headImage;
		user.setHeadImage("user/head/" + new Random().nextInt(15) + ".jpg");
		// 创建时间
		//private String createTime;

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = format.format(date);
		user.setCreateTime(createTime);

		userService.saveUser(user);

		return "redirect:loginBetobe";
	}

	/**
	 * 异步校验用户输入的手机验证码是否正确
	 * @param sms 前端用户填写的验证码，异步校验
	 * @return json，用于回调函数的判断
	 */
	@RequestMapping("/judgeSMS")
	@ResponseBody
	public String judgeSMS(String sms) {
		String smsInSession = (String)session.getAttribute("sms");
		String json = null;
		// 与用户输入的手机验证码判断是否相同
		if (sms.equals(smsInSession)) {
			// 相等
			json = "{\"message\":" + true + "}";

		} else {
			// 不相等
			json = "{\"message\":" + false + "}";

		}
		System.out.println("judge");
		return json;
	}


	/**
	 * 发送手机验证码的请求
	 * @param phone 前端用户填写的（手机号）
	 * @return json，用于回调函数的判断
	 */
	@RequestMapping("/sms")
	@ResponseBody
	public String sms(String phone) {


		System.out.println(phone);
		String json = null;
		// TODO
		// 判断数据库中是否存在该手机号
		if (userService.findUserByUsername(phone) != null) {
			// 存在
			json = "{\"message\":" + false + "}";

		} else {
			// 不存在
			json = "{\"message\":" + true + "}";
			// 发送验证码
			SMS(phone, session);
		}

		return json;
	}


	/**
	 * 调用腾讯云API发送验证码
	 * @param telephone 收取短信的手机号码
	 * @param session 将验证码放置到session域中
	 */
	private void SMS(String telephone, HttpSession session) {

		// 腾讯云中的数据
		int appid = ;
		String appKey = ;
		int templateId = ;
		String smsSign = ;

		// 电话
		String phoneNumber = telephone;
		// 验证码
		Random random = new Random();
		String code = "";
		for (int i = 0; i < 4; i++) {
			code += random.nextInt(10);
		}
		// 放入session域中
		session.setAttribute("sms", code);
		// 验证码
		String[] params = new String[1];
		params[0] = code;

		System.out.println("UserController -----> SMS -----> 验证码为： " + code);

		/*SmsSingleSender smsSingleSender = new SmsSingleSender(appid, appKey);
		try {
			smsSingleSender.sendWithParam("86", phoneNumber, templateId, params, smsSign, "", "");
		} catch (HTTPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}


}
