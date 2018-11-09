package com.yum.two_yum.controller;

/**
 * @author 余先德
 * @data 2018/4/19
 */

public class Constant {
    //google秘钥
    public static final String GOOGLEAPI = "AIzaSyBEqVtoPgib5R8l2HfEFbp4k69mSsAA-9Q";

    //未登录点收藏type
    public static final int NOT_LOG_IN_COLLECTION = 1;
    //未登录点下单type
    public static final int NOT_LOG_IN_ORDER = 2;
    //未登录点举报type
    public static final int NOT_LOG_IN_REPORT = 3;
    //支付key(正式版)
    public static final String APPKEY = "pk_live_B3hW2snQVq2a54EDX3luk4yX";
    //商家接单
    public static final String MERCHANT_NEW_ORDER = "merchant_new_order";
    //客人取消订单
    public static final String GUEST_CANCEL_ORDER = "guest_cancel_order";
    //商家取消订单
    public static final String MERCHANT_CANCEL_ORDER = "merchant_cancel_order";
    //系统取消订单
    public static final String SYSTEM_CANCEL_ORDER = "system_cancel_order";
    //商家交付订单
    public static final String MERCHANT_COMPLETE_ORDER = "merchant_complete_order";
    //商家运营信息被修改
    public static final String MERCHANT_INFO_UPDATE = "merchant_info_update";
    //商家银行信息被修改
    public static final String MERCHANT_BANK_UPDATE = "merchant_bank_update";
    //商家证件信息被修改
    public static final String MERCHANT_CERT_UPDATE = "merchant_cert_update";
    //商家菜品信息被修改
    public static final String MERCHANT_MENU_UPDATE = "merchant_menu_update";
    //邮件
    public static final String EMAIl = "2y@2yum.app";

    //访问成功的标记
    public static final String SUCCESSCON = "200";
    //下单出错的标记
    public static final String ORDERERROR = "701";
    //服务器地址
    public static final String DOMAIN_NAME = "http://54.183.161.24/yum";

    public static final String DOMAIN_IMG = "http://54.183.161.24/yum";
    //显示图片
    public static final String SHOW_IMG = "http://54.183.161.24";
    //上报设备
    public static final String BASDEVICE = DOMAIN_NAME+"/bas/device";
    //登录
    public static final String USER_LOGIN = DOMAIN_NAME+"/sys/user/login";
    //找回密码
    public static final String FORGET_PASSWORD = DOMAIN_NAME+"/bas/email/send";
    //注册
    public static final String USER_REGISTER = DOMAIN_NAME+"/sys/user/register";
    //上传图片
    public static final String FILEINFO_UPLOADPERSONAL = DOMAIN_IMG+"/bas/file/upload";
    //修改用户信息
    public static final String USER_UPDATE = DOMAIN_NAME+"/sys/user/update";
    //menu : 菜单中心REST API
    public static final String MENU_INFO = DOMAIN_NAME+"/biz/menu/merchant/info";
    //home : 商户首页REST API
    public static final String HOME_LIST = DOMAIN_NAME+"/biz/home/merchant/list";
    //永久停用
    public static final String MENU_STOP = DOMAIN_NAME+"/biz/menu/merchant/stop";
    //设置发布状态
    public static final String MENU_ISSUE = DOMAIN_NAME+"/biz/menu/merchant/issue";
    //获取今日订单列表
    public static final String MENU_TODAY = DOMAIN_NAME+"/oc/merchant/order/list";
    //商户收藏
    public static final String ADD_LIKE = DOMAIN_NAME+"/biz/user/like";
    //所有商户关键词
    public static final String KEYWORD_ALL = DOMAIN_NAME+"/bas/merchant/keyword/all";
    //获取默认地址
    public static final String USER_ADDRESS_DEFAULT = DOMAIN_NAME+"/sys/user/default/address";
    //获取地址
    public static final String USER_ADDRESS = DOMAIN_NAME+"/sys/user/address/list";
    //修改地址
    public static final String EDIT_USER_ADDRESS = DOMAIN_NAME+"/sys/user/edit/address";
    //添加地址
    public static final String ADD_USER_ADDRESS = DOMAIN_NAME+"/sys/user/address";
    //获取买家订单列表
    public static final String ORDER_LIST_BUYER = DOMAIN_NAME+"/oc/order/buyer/list";
    //获取证件信息
    public static final String GET_MERCHANT_INFO_CERT = DOMAIN_NAME+"/biz/merchant/info/cert";
    //查询银行账户信息
    public static final String GET_MERCHANT_INFO_BANK = DOMAIN_NAME+"/biz/merchant/info/bank";
    //获取运营信息
    public static final String GET_MERCHANT_INFO_INFO = DOMAIN_NAME+"/biz/merchant/info";
    //获取菜品
    public static final String GET_MERCHANT_MENU_LIST = DOMAIN_NAME+"/biz/merchant/menu/list";
    //保存证件信息
    public static final String GET_MERCHANT_SAVE_CERT = DOMAIN_NAME+"/biz/merchant/save/cert";
    //保存银行账户信息
    public static final String GET_MERCHANT_SAVE_BANK = DOMAIN_NAME+"/biz/merchant/save/bank";
    //更新银行账户信息
    public static final String GET_MERCHANT_UPDATE_BANK = DOMAIN_NAME+"/biz/merchant/update/bank";

    //保存运营设置信息
    public static final String GET_MERCHANT_SAVE = DOMAIN_NAME+"/biz/merchant/save";
    //更新运营设置信息
    public static final String GET_MERCHANT_UPDATE = DOMAIN_NAME+"/biz/merchant/update";
    //所有国家
    public static final String COUNTRY_ALL = DOMAIN_NAME+"/bas/country/all";
    //退出
    public static final String USER_LOGOUT = DOMAIN_NAME+"/sys/user/logout";
    //获取商家首页
    public static final String MERCHANT_HOME = DOMAIN_NAME+"/biz/merchant/home";
    //举报类型
    public static final String REPORT_TYPE = DOMAIN_NAME+"/bas/report/type";
    //举报提交
    public static final String REPORT_SUBMIT = DOMAIN_NAME+"/biz/report/submit";
    //删除地址
    public static final String DEL_ADDRESS = DOMAIN_NAME+"/sys/user/delete/address";
    //获取信用卡
    public static final String CREDIT_CARD_GET = DOMAIN_NAME+"/sys/user/credit/card/list";
    //添加信用卡
    public static final String CREDIT_CARD_SAVE = DOMAIN_NAME+"/sys/user/credit/card/save";
    //解绑信用卡
    public static final String CREDIT_CARD_DELETE = DOMAIN_NAME+"/sys/user/credit/card/delete";
    //创建订单
    public static final String CREATE_ORDER = DOMAIN_NAME+"/oc/order/create";
    //收藏列表
    public static final String LIKE_LIST = DOMAIN_NAME+"/biz/user/like/list";
    //发布菜品
    public static final String RELEASE_MENU = DOMAIN_NAME+"/biz/merchant/issue/greens/menu";
    //删除菜品
    public static final String DELETE_MENU = DOMAIN_NAME+"/biz/merchant/delete/menu";
    //设置上架状态
    public static final String SETTING_MENU = DOMAIN_NAME+"/biz/merchant/setting/state";
    //设置菜品排序
    public static final String SETTING_MENU_SORT = DOMAIN_NAME+"/biz/merchant/setting/sort";
    //删除订单
    public static final String ORDER_BUYER_DELETE = DOMAIN_NAME+"/oc/order/buyer/delete";
    //查看订单详情
    public static final String ORDER_BUYER_INFO = DOMAIN_NAME+"/oc/order/buyer/info";
    //买家查看订单跟踪详情
    public static final String ORDER_BUYER_LOG = DOMAIN_NAME+"/oc/order/log/buyer/info";
    //设置订单状态
    public static final String MERCHANT_ORDER_SETUP = DOMAIN_NAME+"/oc/merchant/order/setup";
    //配送中列表
    public static final String MERCHANT_ORDER_DISPATCH_BAG_LIST = DOMAIN_NAME+"/oc/merchant/order/dispatch/bag/list";
    //接单
    public static final String MERCHANT_ORDER_ACCEPT = DOMAIN_NAME+"/oc/merchant/order/accept";
    //配送袋子中的列表
    public static final String MERCHANT_ORDER_DISPATCH_LIST = DOMAIN_NAME+"/oc/merchant/order/dispatch/list";
    //商家统计列表(订单记录)
    public static final String MERCHANT_ORDER_STATISTICS_LIST = DOMAIN_NAME+"/oc/merchant/order/statistics/list";
    //商家统计列表(交易记录)
    //public static final String PAY_TRANSFER_RECORD = DOMAIN_NAME+"/pay/transfer/list";
    public static final String PAY_TRANSFER_RECORD = DOMAIN_NAME+"/count/transaction/record/list";
    //自动接单
    public static final String MERCHANT_AUTOMATION_ACCEPT = DOMAIN_NAME+"/biz/merchant/automation/accept";
    //交易记录(商家端已交付专用)
    public static final String MERCHANT_COMPLETE_ORDER_LIST = DOMAIN_NAME+"/oc/merchant/complete/order/list";
    //获取服务信息
    public static final String BAS_TERMS = DOMAIN_NAME+"/bas/terms";
    //更新证件信息
    public static final String GET_MERCHANT_UPDATE_CERT = DOMAIN_NAME+"/biz/merchant/update/cert";
    //获取用户信息
    public static final String GET_USER_INFO = DOMAIN_NAME+"/sys/user/info";

}
