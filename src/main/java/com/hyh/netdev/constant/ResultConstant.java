package com.hyh.netdev.constant;


import com.hyh.netdev.vo.Result;

/**
 * 可复用状态码类
 * 注意： 从本类获取的常量值不可修改任何内容
 *
 * @author hyh
 */
public class ResultConstant {
    public final static Result SYSTEM_ERROR = new Result<>(-1, "系统错误");
    public final static Result SUCCESS = new Result<>(20000, "ok");
    public final static Result TOKEN_EXPIRE = new Result<>(1001, "登陆密钥无效");
    public final static Result ARGS_ERROR = new Result<>(1002, "参数错误");
    public final static Result PERMISSION_ERROR = new Result<>(1003, "权限不足");
    public final static Result CLAZZ_NOT_FOUND = new Result<>(2001, "无此班级");
    public final static Result ACCOUNT_ALLOCATED = new Result<>(2002, "账号已被使用");
    public final static Result ACCOUNT_NOT_FOUND = new Result<>(2003, "账号未找到");
    public final static Result PASSWORD_ERROR = new Result<>(2004, "旧密码错误");
    public final static Result USERNAME_PASSWORD_ERROR = new Result<>(2005, "用户名密码错误");
    public final static Result FILE_ERROR = new Result<>(2006, "文件错误");
    public final static Result PRODUCT_UPDATE_ERROR = new Result<>(3000,"商品上传或更新失败");
    public final static Result EMAIL_USED = new Result<>(2010,"邮箱已被使用");
    public final static Result USER_NOT_ACTIVATED = new Result<>(2011,"用户还没有激活账号，请到邮箱去激活");
    public final static Result DEPARTMENT_EXIST_RESOURCE_POOL = new Result<>(3000,"部门已使用资源池");



    private ResultConstant() {

    }

    public class JUPYTER_USER_SERVER_NOT_EXIST {
    }
}
