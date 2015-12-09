package com.joy.app.utils.http;

import com.android.library.httptask.ObjectRequest;
import com.joy.app.JoyApplication;

import java.util.Map;

/**
 * @author litong  <br>
 * @Description 我的行程规划    <br>
 */
public class PlanHtpUtil extends BaseHtpUtil{

    /**
     *添加旅行计划
     *
     * @return
     */
    public static ObjectRequest getUserPlanAddRequest(String poiId, String folderId,Class clazz) {

        Map<String, String> params = getBaseParams();
        params.put("product_id", poiId);
        params.put("folder", folderId);
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());

        return createPostRequest(URL_POST_PLAN_ADD, params,clazz);
    }
    /**
     *我的旅行计划文件夹列表
     *
     * @return
     */
    public static ObjectRequest getUserPlanFolderRequest(Class clazz,int count, int page) {

        Map<String, String> params = getBaseParams();
        params.put(KEY_COUNT, count+"");
        params.put(KEY_PAGE, page+"");
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());

        return createPostRequest(URL_POST_PLAN_FOLDER, params,clazz);
    }
    /**
     * 创建我的旅行计划文件夹
     * @param folder_name  不传 创建默认文件夹
     * @return
     */
    public static ObjectRequest getUserPlanFolderCreateRequest(String folder_name,Class clazz) {

        Map<String, String> params = getBaseParams();
        params.put("folder_name", folder_name);
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());

        return createPostRequest(URL_POST_PLAN_FOLDER_CREATE, params,clazz);
    }
    /**
     *删除我的旅行计划文件夹
     *
     * @return
     */
    public static ObjectRequest getUserPlanFolderDeleteRequest(String folder_id,Class clazz) {

        Map<String, String> params = getBaseParams();
        params.put("folder_id", folder_id);
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());

        return createPostRequest(URL_POST_PLAN_FOLDER_DELETE, params,clazz);
    }
    /**
     *添加旅行计划
     *
     * @return
     */
    public static ObjectRequest getUserPlanFolderModifyRequest(String new_name,String folder_id,Class clazz) {

        Map<String, String> params = getBaseParams();
        params.put("new_name", new_name);
        params.put("folder_id", folder_id);
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());

        return createPostRequest(URL_POST_PLAN_FOLDER_MODIFY, params,clazz);

    }
    /**
     *添加旅行计划
     *
     * @return
     */
    public static ObjectRequest getUserPlanListRequest(String folder_id,Class clazz) {

        Map<String, String> params = getBaseParams();
        params.put("folder_id", folder_id);
        params.put(KEY_USER_TOKEN, JoyApplication.getUserToken());

        return createPostRequest(URL_POST_PLAN_LIST, params,clazz);

    }
}
