package com.fn.healfie.consts;

/**
 * Created by Administrator on 2018/11/1.
 */

public class MyUrl {
    /**
     * from @zhaojian
     * content 开发环境
     */
    private static String BASE = "http://13.229.151.83:8081/healfie";

    /**
     * from @zhaojian
     * content 获取短信验证码
     */
    public static String VERIFICATION = BASE + "/api/verification";

    /**
     * from @zhaojian
     * content 註冊
     */
    public static String REGISTER = BASE + "/api/register";

    /**
     * from @zhaojian
     * content 會員信息編輯
     */
    public static String MEMBERINFO = BASE + "/api/member/info";

    /**
     * from @zhaojian
     * content 修改密碼
     */
    public static String CHANGEPASSWORD = BASE + "/api/change/password";

    /**
     * from @zhaojian
     * content 登录
     */
    public static String LOGIN = BASE + "/api/login";

    /**
     * from @zhaojian
     * content 藥品食品列表
     */
    public static String RECORD = BASE + "/api/record";

    /**
     * from @zhaojian
     * content 创建食物
     */
    public static String FOOD = BASE + "/api/food";

    /**
     * from @zhaojian
     * content 获取图片
     */
    public static String SHOWIMAGE = BASE + "/api/show/image";

    /**
     * from @zhaojian
     * content 搜索聯繫人
     */
    public static String FRIENDSEARCH = BASE + "/api/friend/search";

    /**
     * from @zhaojian
     * content 添加聯繫人
     */
    public static String FRIENDINFO = BASE + "/api/friend/info";

    /**
     * from @zhaojian
     * content 添加聯繫人
     */
    public static String FRIENDAUDIT = BASE + "/api/friend/audit/info";

}
