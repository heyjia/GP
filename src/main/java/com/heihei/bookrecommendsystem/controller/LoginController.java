package com.heihei.bookrecommendsystem.controller;

import com.heihei.bookrecommendsystem.entity.BookClassDO;
import com.heihei.bookrecommendsystem.entity.BookDO;
import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.entity.form.EmailForm;
import com.heihei.bookrecommendsystem.entity.form.UserForm;
import com.heihei.bookrecommendsystem.rabbitmq.RabbitMQConfig;
import com.heihei.bookrecommendsystem.redis.MailKeyPerfix;
import com.heihei.bookrecommendsystem.redis.RedisService;
import com.heihei.bookrecommendsystem.result.CodeMsg;
import com.heihei.bookrecommendsystem.result.Result;
import com.heihei.bookrecommendsystem.service.BookService;
import com.heihei.bookrecommendsystem.service.UserService;
import com.heihei.bookrecommendsystem.util.RSAUtil;
import com.heihei.bookrecommendsystem.util.UserCookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private static long  GET_CODE_TIME_OUT = 100000;
    private static long  CODE_TIME_OUT = 1800000;
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    UserCookieUtil userCookieUtil;



    @Autowired
    BookService bookService;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    RedisService redisService;
    //前往登录页面
    @RequestMapping(value = "/toLogin")
    public String toLogin() {
        return "login";
    }

    // 前往注册页面
    @RequestMapping(value = "/toRegister")
    public String toRegister() {
        return "register";
    }

    // 拦截用户登录请求，用shiro进行认证，判断用户是否存在以及用户密码是否正确
    @RequestMapping(value = "/doLogin")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "userId") String userId, @RequestParam(name = "password") String password){
        logger.info("进入doLogin");
        UserDO userDO = userService.getOneUserByUserId(userId);       //根据用户名查询用户，判断其是否存在
        if(userDO == null) {
            return  Result.error(CodeMsg.UNKNOW_ACCOUNT);
        }
        logger.info("用户信息:" + userDO.toString());
        logger.info(RSAUtil.PRIVATE_KEY);
        String inputPassword = RSAUtil.decrypt(password,RSAUtil.PRIVATE_KEY);
        //判断密码是否正确，并且将用户放入session缓存
        String userPpassword = RSAUtil.decrypt(userDO.getPassword(),RSAUtil.PRIVATE_KEY);
        logger.info("表单密码解密后的结果：" + password);
        if (!userPpassword.equals(inputPassword)) {
            return  Result.error(CodeMsg.PASSWORD_ERROR);
        }
        userCookieUtil.addCookie(response,userDO,"");
        logger.info("登录成功");
        return Result.success(true);
    }

    // 前往首页
    @RequestMapping("/toIndex")
    public String toIndex(Model model, UserDO userDO){
        logger.info("首页user：" + userDO.toString());
        //查询所有的分类数
        List<BookClassDO> allBookClass = bookService.getAllBookClass();
        for (BookClassDO bookClass : allBookClass) {
            System.out.println(bookClass.toString());
        }
        int [] randomBookId = new int [8];
        for (int i = 0;i < randomBookId.length;i++) {
            randomBookId[i] = (int)(Math.random() * 10000 + 1);
        }
        //获取随机推荐图书
        List<BookDO> randomBooks = bookService.getRandomBooks(randomBookId);
        for (int i = 0 ; i < randomBooks.size();i++) {
            BookDO bookDO = randomBooks.get(i);
            logger.info("随机推荐：" + bookDO.toString());
            if (bookDO.getName().length() > 10) {
               bookDO.setName(bookDO.getName().substring(0,10) + "...");
            }
        }
        //获取热门图书
        List<BookDO> hotBooks = bookService.getHotBooks();
        for (BookDO bookDO : hotBooks) {
            logger.info("热门图书：" + bookDO.toString());
            if (bookDO.getName().length() > 10) {
                bookDO.setName(bookDO.getName().substring(0,10) + "...");
            }
        }
        List<BookDO> recommenrBookList = bookService.getRecommendBookByUserByUserId(userDO.getId());
        //选择用户偏好图书
        List<BookDO> favoriteBookList = bookService.getFavoriteBookByUserId(userDO.getId());
        model.addAttribute("favoriteBookList",favoriteBookList);
        model.addAttribute("recommendBookList",recommenrBookList);
        model.addAttribute("u",userDO);
        model.addAttribute("class",allBookClass);
        model.addAttribute("randomBooks",randomBooks);
        model.addAttribute("hotBooks",hotBooks);
        logger.info("toLogin方法：前往首页");
        return "index";
    }

    //注册
    @RequestMapping(value = "/register")
    @ResponseBody
    public Result<Boolean> register(UserForm userForm,HttpServletResponse response) {
        String userId = userForm.getUserId();
        UserDO user = userService.getOneUserByUserId(userId);
        if (user != null) {
            return Result.error(CodeMsg.USER_EXISTED);
        }
        user = userService.getOneUserByEmail(userForm.getEmail());
        if (user != null) {
            return Result.error(CodeMsg.EMAIL_EXISTED);
        }
        String codeRedis = redisService.get(MailKeyPerfix.mailKeyPerfix,userForm.getEmail(),String.class);
        if (codeRedis == null || "".equals(codeRedis)) {
            return Result.error(CodeMsg.CHECK_CODE_TIME_OUT);
        }
        //EmailDO emailDO = emailService.getEmailByAddress(userForm.getEmail(),userForm.getUserId());
       // if (emailDO == null) {
       //     return Result.error(CodeMsg.WITHOUT_CHECK_CODE);
//        }else{
//            Date now = new Date();
//            Date lastUpdtTime = emailDO.getUpdtTime();
//            long diff = now.getTime() - lastUpdtTime.getTime();
//            if (diff > CODE_TIME_OUT) {
//                return Result.error(CodeMsg.CHECK_CODE_TIME_OUT);
//            }
//            if (!emailDO.getCode().equals(userForm.getCheckCode())) {
//                return Result.error(CodeMsg.CHECK_CODE_ERROR);
//            }
//        }
        if (!codeRedis.equals(userForm.getCheckCode())) {
                return Result.error(CodeMsg.CHECK_CODE_ERROR);
        }
        boolean result = userService.addUserByForm(userForm);
        if (result == false) {
            Result.error(CodeMsg.REGISTER_ERROR);
        }
        UserDO u = userService.getOneUserByUserId(userId);
        if (u == null) {
            Result.error(CodeMsg.REGISTER_ERROR);
        }else{
            userCookieUtil.addCookie(response,u,"");
        }
        return Result.success(true);
    }

    //发送邮件
    @ResponseBody
    @RequestMapping(value = "email")
    public Result<Boolean>  getEmail(EmailForm emailForm) {
        //判断邮箱是否注册
        UserDO user = userService.getOneUserByEmail(emailForm.getEmail());
        //如果用户存在
        if (user != null) {
            return Result.error(CodeMsg.EMAIL_EXISTED);
        }
        //从数据库查找验证码
        String codeRedis = redisService.get(MailKeyPerfix.mailKeyPerfix,emailForm.getEmail(),String.class);
        if (codeRedis != null && !"".equals(codeRedis) && codeRedis.length() != 0) {
            return Result.error(CodeMsg.EMAIL_TOO_FAST);
        }
        //随机生成验证码
        int random = (int)((Math.random() * 9 + 1) * 100000);
        String code = random + "";
        logger.info("随机生成的验证码为："+ code);
        //插入Redis
        redisService.set(MailKeyPerfix.mailKeyPerfix,emailForm.getEmail(),code);
//        EmailDO emailDO = emailService.getEmailByAddress(emailForm.getEmail(),emailForm.getUserId());
//        if (emailDO != null) {
//            Date now = new Date();
//            Date lastUpdtTime = emailDO.getUpdtTime();
//            long diff = now.getTime() - lastUpdtTime.getTime();//这样得到的差值是微秒级别
//            logger.info("时间差diff: " + diff);
//            if (diff < GET_CODE_TIME_OUT){
//                return Result.error(CodeMsg.EMAIL_TOO_FAST);
//            }else{
//                emailDO.setUpdtTime(new Date());
//                emailDO.setCode(code);
//                emailService.updateOne(emailDO);
//            }
//        }else{
//            emailService.insertCode(emailForm.getUserId(),emailForm.getEmail(),code);
//        }
        try{
            String msg = emailForm.getEmail() + ":" + code;
            amqpTemplate.convertAndSend(RabbitMQConfig.MAIL_QUEUE_NAME,msg);
            //EmailUtil.sendCheckCode(emailForm.getEmail(),code);
        }catch (Exception e) {
            Result.error(CodeMsg.SEND_EMAIL_ERROR);
        }
        return Result.success(true);
    }
    @RequestMapping("/toLogout")
    public String toLogout(HttpServletResponse response,HttpServletRequest request) {
        //删除cookie
        userCookieUtil.removeCookie(request,response);
        return "login";
    }
}
