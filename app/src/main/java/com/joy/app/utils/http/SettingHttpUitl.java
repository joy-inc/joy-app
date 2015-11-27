package com.joy.app.utils.http;

import com.joy.app.bean.HolidayInfo;

import java.util.Map;

/**
 * 设置相关和系统一些其他需求的网络
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-26
 */
public class SettingHttpUitl extends BaseHtpUtil {

    /**
     * 获取假期信息
     * @return
     */
    public static String getHolidayInfo() {

        Map<String, Object> params = getBaseParams();
        return createGetUrl(URL_HOLIDAYINFO, params);
    }
}
