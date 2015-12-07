package com.joy.app.utils.hotel;

import android.text.format.DateFormat;

import com.android.library.utils.TextUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间戳格式的工具类
 *
 * @author yhb
 */
public class HotelTimeUtil {

    final static long min = 1000 * 60;
    final static long hour = 1000 * 60 * 60;
    public final static long day = 1000 * 60 * 60 * 24;

    private static long SERVER_TIME_DEALY;

    /**
     * 统一的时间戳格式化，APP的时间戳都按这个规则来
     *
     * @param timeMillis
     * @return
     */
    public static CharSequence getCommonTimeFormatText(long timeMillis) {

        long temp = System.currentTimeMillis() - timeMillis;
        if (temp < min) {

            return temp / 1000 + "秒前";
        } else if (temp < hour) {

            return temp / min + "分钟前";
        } else if (temp < day) {

            return temp / hour + "小时前";
        } else if (temp < (7 * day)) {

            return temp / day + "天前";
        } else {

            return DateFormat.format("yyyy-MM-dd", timeMillis);
        }
    }

    /**
     * 日历转换成固定格式的形式返回yyyy年MM月dd日
     *
     * @param calendar
     * @return
     */
    public static String getCalendarTimeToString(Calendar calendar) {
        if (calendar == null) {
            return TextUtil.TEXT_EMPTY;
        }

        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    /**
     * 日历转换成固定格式的形式返回yyyy-MM-dd
     *
     * @param calendar
     * @return
     */
    public static String getTimeWithoutChinaToString(Calendar calendar) {
        if (calendar == null) {
            return TextUtil.TEXT_EMPTY;
        }

        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 计算2个Calendar对象之间相差多少天
     *
     * @param startCalendar
     * @param endCalendar
     * @return
     */
    public static int getDaysBettweenCalendar(Calendar startCalendar, Calendar endCalendar) {
        if (startCalendar == null || endCalendar == null) {
            return 0;
        }

        long time = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        int days = new Long(time / (1000 * 60 * 60 * 24)).intValue();

        return days;
    }
    /**
     * 计算2个Calendar对象之间相差多少天
     *
     * @param start
     * @param end
     * @return
     */
    public static int getDaysBettweenCalendar(long start, long end) {

        long time =end - start;
        int days = new Long(time / (1000 * 60 * 60 * 24)).intValue();

        return days;
    }

    //获取明天和后天的long
    //[0] 明天
    //[1]后天
    public static long[] get2Day() {

        return getLastOffsetDay(1);
    }

    /**
     * 获取偏移量的日期
     *
     * @param offset
     * @return
     */
    public static long[] getLastOffsetDay(int offset) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, +offset);
        long begin = calendar.getTime().getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        long end = calendar.getTime().getTime();
        return new long[]{begin, end};
    }

    /**
     * 返回 文字格式化（周中的天数）
     *
     * @param timeMillis
     * @return
     */
    public static String getDayWeek(long timeMillis) {

        long timeNowMillis = System.currentTimeMillis();

        if (Math.abs(timeNowMillis - timeMillis) < day && timeMillis < timeNowMillis) {
            return "今天";
        }
        if ((timeMillis - timeNowMillis) < day && timeMillis > timeNowMillis) {
            return "明天";
        } else if ((timeMillis - timeNowMillis) < day * 2 && timeMillis > timeNowMillis) {
            return "后天";
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        String date = simpleDateFormat.format(new Date(timeMillis));

        return date;
    }

    /**
     * 获得指定时区当天0点的时间戳
     *
     * @return
     */
    public static long getTodayStartTimeInMillis(String timeZone) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        cal.setTimeZone(TimeZone.getTimeZone(timeZone));

        return cal.getTimeInMillis();
    }

    /**
     * 获取服务器时间
     */
    public static long getServerSynchronizeTime() {

        long timeNow = System.currentTimeMillis(); // 秒数
//        return timeNow - SERVER_TIME_DEALY;
        return timeNow - 0; // 这里貌似不需要计算时间差了，同折扣
    }

    /**
     * 获取和服务器时间的差值
     */
    public static void getServerTime() {

//        if (SERVER_TIME_DEALY <= 0) {
//            HttpTask task = new HttpTask();
//            task.setHttpTaskParams(DealHtpUtil.getServerTime());
//            task.setListener(new QyerJsonListener<ServerTime>(ServerTime.class) {
//
//                @Override
//                public void onTaskPre() {
//                }
//
//                @Override
//                public void onTaskResult(ServerTime result) {
//
//                    SERVER_TIME_DEALY = Long.parseLong(result.getTime()) * 1000 - System.currentTimeMillis();
//                }
//
//                @Override
//                public void onTaskFailed(int failedCode, String msg) {
//
//                }
//            });
//        }
    }
}
