package com.heihei.bookrecommendsystem.result;

/**
 * @ClassName CodeMsg
 * @Description 错误时候的结果集
 * @Author CHENZEJIA
 * @Date 2019/12/17 10:57
 **/
public class CodeMsg {


    private int code;
    private String msg;

    public static CodeMsg PASSWORD_ERROR = new CodeMsg(10001,"密码错误");
    public static CodeMsg UNKNOW_ACCOUNT = new CodeMsg(10002,"用户不存在");
    public static CodeMsg LOGOUT_ERROR = new CodeMsg(10004,"登录失败");
    public static CodeMsg USER_EXISTED = new CodeMsg(10005,"用户已存在");
    public static CodeMsg REGISTER_ERROR = new CodeMsg(10006,"数据库异常，注册失败");
    public static CodeMsg SEND_EMAIL_ERROR = new CodeMsg(10007,"发送邮件失败");
    public static CodeMsg EMAIL_TOO_FAST = new CodeMsg(10008,"请勿频繁点击，登录邮箱查看验证码");
    public static CodeMsg WITHOUT_CHECK_CODE = new CodeMsg(10009,"请获取邮箱验证码");
    public static CodeMsg CHECK_CODE_TIME_OUT = new CodeMsg(10010,"邮箱验证码已过期，请重新获取");
    public static CodeMsg CHECK_CODE_ERROR = new CodeMsg(10011,"验证码错误，请重新输入");
    public static CodeMsg EMAIL_EXISTED = new CodeMsg(10012,"注册失败，邮箱已被注册");
    public static CodeMsg UPDATE_USER_ERROR = new CodeMsg(10013,"更新用户信息失败");
    public static CodeMsg USER_NAME_EXISTED = new CodeMsg(10014,"用户名已存在");
    public static CodeMsg OLD_PASSWORD_ERROR = new CodeMsg(10015,"旧密码错误");
    public static CodeMsg UPDATE_PASSWORD_ERROR =  new CodeMsg(10016,"修改密码错误，数据库异常");
    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
