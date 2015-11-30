package com.joy.app.bean;


import com.android.library.utils.TextUtil;

/**
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-07-09
 * 节假日的明细
 */
public class HolidayInfo {

    //    public HolidayInfo(String s, String t) {
    //        data = s;
    //        info = t;
    //    }
    public HolidayInfo() {
    }

    /**
     * 日期
     */
    private String data = TextUtil.TEXT_EMPTY;

    /**
     * 日期 描述
     */
    private String info = TextUtil.TEXT_EMPTY;

    public String getData() {

        return data;
    }

    public void setData(String data) {

        if (!TextUtil.isEmpty(data)) {

            this.data = data.replace(" ", "").replace("-0", "").replace("-", "");
        } else {

            this.data = TextUtil.TEXT_EMPTY;
        }
    }


    public String getInfo() {

        return info;
    }

    public void setInfo(String info) {

        this.info = TextUtil.filterNull(info);
    }
}
