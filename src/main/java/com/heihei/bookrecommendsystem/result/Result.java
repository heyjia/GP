package com.heihei.bookrecommendsystem.result;

/**
 * @ClassName Result
 * @Description    json结果集合，包含结果代码code，错误信息msg 成功时候返回的数据 data
 * @Author CHENZEJIA
 * @Date 2019/12/17 10:56
 **/
public class Result<T> {
    private int code;
    private String msg;
    private  T data;
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }
    public Result(T data) {
        this.data = data;
    }
    public Result(CodeMsg codeMsg) {
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result() {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
