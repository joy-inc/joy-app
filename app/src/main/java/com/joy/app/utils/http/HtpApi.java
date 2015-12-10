package com.joy.app.utils.http;

/**
 * 网络请求 api
 */
public interface HtpApi {

    String QYER_URL_BASE = "http://open.qyer.com";// base url
//    String JOY_URL_BASE = "http://api.joy.com";// base url
    String JOY_URL_BASE = "http://api.joy.4awork.com";// base url

    // sample
    String URL_GET_SEARCH_HOT_CITY = QYER_URL_BASE + "/qyer/hotel/hot_city_list";// 200热门城市
    String URL_GET_CITY_INFO = QYER_URL_BASE + "/qyer/footprint/city_detail";// 获取城市详情
    String URL_GET_SPECIAL_LIST = QYER_URL_BASE + "/qyer/special/topic/special_list";// 获取专题列表

    // main
    String URL_POST_MAIN_ROUTE_LIST = JOY_URL_BASE + "/homepage/index_list";//首页的路线

    // poi折扣====================================
    String URL_POST_PRODUCT_DETAIL = JOY_URL_BASE + "/product/detail";// 获取poi折扣详情接口
    String URL_POST_COMMENTS = JOY_URL_BASE + "/product/comment_list";// 获取商品评论列表
    String URL_POST_COMMENT_ADD = JOY_URL_BASE + "/product/add_comment";// poi折扣评论接口
    String URL_POST_OPTIONS = JOY_URL_BASE + "/product/option_list";// 获取商品项目列表
    String URL_POST_CONTACT_GET = JOY_URL_BASE + "/product/contact_info";// 获取联系人信息
    String URL_POST_CONTACT_ADD = JOY_URL_BASE + "/product/add_contact";// 添加联系人信息
    String URL_POST_ORDER_LIST = JOY_URL_BASE + "/order/order_list";// 获取订单列表
    String URL_POST_ORDER_CREATE = JOY_URL_BASE + "/order/create";// 创建订单
    String URL_POST_ORDER_DETAIL = JOY_URL_BASE + "/order/detail";// 获取订单
    String URL_POST_ORDER_CANCEL = JOY_URL_BASE + "/order/cancel";// 取消订单
    String URL_POST_ORDER_PAY_CREATE_CHARGE = JOY_URL_BASE + "/order/create_charge";// 创建Ping++支付凭证

    // 行程规划====================================
    String URL_POST_PLAN_ADD = JOY_URL_BASE + "/user/plan_add";//  添加我的旅行计划
    String URL_POST_PLAN_FOLDER = JOY_URL_BASE + "/user/plan_folders";//  我的旅行计划文件夹列表
    String URL_POST_PLAN_LIST = JOY_URL_BASE + "/user/plan_list";//  我的旅行计划数据列表
    String URL_POST_PLAN_FOLDER_CREATE = JOY_URL_BASE + "/user/plan_folder_create";//  我的文件夹创建接口
    String URL_POST_PLAN_FOLDER_MODIFY = JOY_URL_BASE + "/user/plan_folder_modify";//  我的文件夹修改接口
    String URL_POST_PLAN_FOLDER_DELETE = JOY_URL_BASE + "/user/plan_folder_delete";//  我的文件夹删除接口

    //酒店============
    String URL_POST_HOTEL_LIST = JOY_URL_BASE + "/place/cityfun";//  城市玩乐
    String URL_GET_HOTEL_LIST = QYER_URL_BASE + "/qyer/hotel/search_list";// 请求酒店列表 
    String URL_GET_SEARCH_FILTERS = QYER_URL_BASE + "/qyer/hotel/search_filters"; //酒店筛选条件列表
    String URL_GET_SEARCH_HOTEL_AUTOCOMPLATE = QYER_URL_BASE + "/qyer/hotel/search_autocomplate";//搜索酒店联想

    //公共的,设置的
    //    String URL_HOLIDAYINFO =   "http://open.qyer.com/qyer/hotel/get_holidays_info";// 日历信息
    //用户============================================
    String URL_USER_GETCODE = JOY_URL_BASE + "/account/send_compatible_mobile_login_code";//获取验证码
    String URL_USER_LOGIN = JOY_URL_BASE + "/account/compatible_mobile_login";//获取登录
    String URL_USER_LOGIN_OUT = JOY_URL_BASE + "/account/logout";//登出

    // city
    String URL_POST_CITY = JOY_URL_BASE + "/place/index_page";
    String URL_POST_CITY_FUN = JOY_URL_BASE + "/place/cityfun";
    String URL_POST_CITY_ROUTE = JOY_URL_BASE + "/place/route_list";
}