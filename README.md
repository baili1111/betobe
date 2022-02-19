# 视频弹幕网站
运行：
- 单体应用，修改配置文件中的数据库配置即可

- 登录使用到了腾讯云的短信服务，以及QQ登录，微信登录，需要自行修改成自己的配置
    UserController-->SMS()

- 上传视频使用了腾讯云的云点播服务，同样需要修改成自己的账号
    VideoController-->videoSign()
 
- 邮箱订阅功能同样需要修改成自己的邮箱账号
    SendEmailManager-->sendMail()
