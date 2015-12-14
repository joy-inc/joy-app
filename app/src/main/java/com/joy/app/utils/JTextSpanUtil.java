package com.joy.app.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.android.library.utils.TextUtil;

/**
 * 文本Spannable工具类
 * Created by xiaoyu.chen on 15/12/10.
 */
public class JTextSpanUtil {

    /**
     * 格式化单位字符
     *
     * @param priceWithUnit 带一个长度单位字符的 完整串
     * @return 返回第一个字符大小为后边字符的67%大小的文本串
     */
    public static CharSequence getFormatUnitStr(String priceWithUnit) {

        if (TextUtil.isEmpty(priceWithUnit))
            return TextUtil.TEXT_EMPTY;

        SpannableString spannableString = new SpannableString(priceWithUnit);
        spannableString.setSpan(new RelativeSizeSpan(0.67f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
