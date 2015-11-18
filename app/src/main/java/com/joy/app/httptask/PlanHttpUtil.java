package com.joy.app.httptask;

import com.joy.app.JoyApplication;
import com.joy.app.httptask.sample.BaseHtpUtil;
import com.joy.library.utils.DeviceUtil;

import java.util.Map;

/**
 * @author litong  <br>
 * @Description 我的行程规划    <br>
 */
public class PlanHttpUtil extends BaseHtpUtil{

    /**
     *添加旅行计划
     *
     * @return
     */
    public static String getUserPlanAddUrl(String poiId,String folderId) {

        Map<String, Object> params = getBaseParams();
        params.put("poiid", poiId);
        params.put("folder", folderId);
        params.put("user_token", JoyApplication.getUserToken());

        return createGetUrl(URL_POST_PLAN_ADD, params);
    }
    /**
     *我的旅行计划文件夹列表
     *
     * @return
     */
    public static String getUserPlanFolderUrl(int count,int page) {

        Map<String, Object> params = getBaseParams();
        params.put("count", count);
        params.put("page", page);
        params.put("user_token", JoyApplication.getUserToken());

        return createGetUrl(URL_POST_PLAN_FOLDER, params);
    }
    /**
     * 创建我的旅行计划文件夹
     * @param folder_name  不传 创建默认文件夹
     * @return
     */
    public static String getUserPlanFolderCreateUrl(String folder_name) {

        Map<String, Object> params = getBaseParams();
        params.put("folder_name", folder_name);
        params.put("user_token", JoyApplication.getUserToken());

        return createGetUrl(URL_POST_PLAN_FOLDER_CREATE, params);
    }
    /**
     *删除我的旅行计划文件夹
     *
     * @return
     */
    public static String getUserPlanFolderDeleteUrl(String folder_id) {

        Map<String, Object> params = getBaseParams();
        params.put("folder_id", folder_id);
        params.put("user_token", JoyApplication.getUserToken());

        return createGetUrl(URL_POST_PLAN_FOLDER_DELETE, params);
    }
    /**
     *添加旅行计划
     *
     * @return
     */
    public static String getUserPlanFolderModifyUrl(String new_name,String folder_id) {

        Map<String, Object> params = getBaseParams();
        params.put("new_name", new_name);
        params.put("folder_id", folder_id);
        params.put("user_token", JoyApplication.getUserToken());

        return createGetUrl(URL_POST_PLAN_FOLDER_MODIFY, params);
    }
    /**
     *添加旅行计划
     *
     * @return
     */
    public static String getUserPlanListUrl(String folder_id) {

        Map<String, Object> params = getBaseParams();
        params.put("folder_id", folder_id);
        params.put("user_token", JoyApplication.getUserToken());

        return createGetUrl(URL_POST_PLAN_LIST, params);
    }
}
