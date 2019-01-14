package com.fn.healfie.utils;

import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimesUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
            "yyyy-MM-dd");
    public static final SimpleDateFormat MMDD_HHMM = new SimpleDateFormat(
            "MM-dd HH:mm");
    public static final SimpleDateFormat YYYYMMDD_HHMM = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");

    private TimesUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    public static long getLongTime(String timeStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = dateFormat.parse(timeStr);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getLongTimeForecond(String timeStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = dateFormat.parse(timeStr);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String timesToString(long time) {
        return getTime(time, DATE_FORMAT_DATE);
    }

    public static String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(date);
    }


    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, YYYYMMDD_HHMM);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    public static String getMonth() {
        Time time = new Time("GMT+8");
        time.setToNow();
        int month = time.month + 1;

        if (month < 10) {
            return "0" + month;
        } else {
            return month + "";
        }

    }

    public static int getCurrentMonth() {
        Time time = new Time("GMT+8");
        time.setToNow();
        int month = time.month;
        return month;
    }

    public static int getCurrentYear() {
        Time time = new Time("GMT+8");
        time.setToNow();
        int month = time.year;
        return month;
    }

    public static int getCurrentDay() {
        Time time = new Time("GMT+8");
        time.setToNow();
        int month = time.monthDay;
        return month;
    }

    /**
     * 今年已经过天数
     *
     * @return
     */
    public static int getTotalDay() {
        int month = Integer.valueOf(getMonth());
        int totalDay = 0;
        if (month == 1) {
            return getCurrentDay();
        }
        for (int i = 1; i < month; i++) {
            totalDay += getDaysByYearMonth(getCurrentYear(), i);
        }
        totalDay += getCurrentDay();
        return totalDay;
    }


    /**
     * 获得显示的时间
     */
    public static String getShowTime(long time) {
        String showTime = getTime(time, DEFAULT_DATE_FORMAT);
        String releaseTime = getTime(time, DATE_FORMAT_DATE);
        String today = getCurrentYear() + "-" + getMonth() + "-" + getCurrentDay();
        long release = getLongTime(releaseTime);
        long toTime = getLongTime(today);

        //同一天显示时间
        if (release == toTime) {
            showTime = showTime.substring((showTime.indexOf(" ") + 1), showTime.lastIndexOf(":"));
        } else {//不是同一天显示日期
            showTime = showTime.substring((showTime.indexOf("-") + 1), showTime.lastIndexOf(" "));
        }
        return showTime;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据日期 找到对应日期的 星期
     */
    public static String getDayOfWeekByDate(String date) {
        String dayOfweek = "-1";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormatter.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String str = formatter.format(myDate);
            dayOfweek = str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayOfweek;
    }

    /**
     * 将日期 YYYY-MM-DD  转化为 YYYY年MM月DD日
     *
     * @return
     */
    public static String getBirthDay(String date) {

        try {
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);//定义起始日期
            SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");

            SimpleDateFormat sdf1 = new SimpleDateFormat("MM");

            SimpleDateFormat sdf2 = new SimpleDateFormat("dd");

            String str1 = sdf0.format(d1);

            String str2 = sdf1.format(d1);

            String str3 = sdf2.format(d1);
            return str1 + "年" + str2 + "月" + str3 + "日";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 根据日期获得年份
     *
     * @return
     */
    public static int getYearForTime(String date) {
        try {
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);//定义起始日期
            SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
            String str1 = sdf0.format(d1);
            return Integer.parseInt(str1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 根据日期获得年份
     *
     * @return
     */
    public static int getMonthForTime(String date) {
        try {
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);//定义起始日期
            SimpleDateFormat sdf0 = new SimpleDateFormat("MM");
            String str1 = sdf0.format(d1);
            return Integer.parseInt(str1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 根据日期获得年份
     *
     * @return
     */
    public static int getDayForTime(String date) {
        try {
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);//定义起始日期
            SimpleDateFormat sdf0 = new SimpleDateFormat("dd");
            String str1 = sdf0.format(d1);
            return Integer.parseInt(str1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    /**
     * 根据时间获得日期(DD:HH)
     *
     * @param time
     * @return
     */
    public static String getBeginTime(String time) {
        try {
            long getTime = Long.parseLong(time);
            Date date = new Date(getTime);
            int beginMonth = date.getMonth();
            String showTime = "";
            String minutes = "";
            if (date.getMinutes() < 10) {
                minutes = "0" + date.getMinutes();
            } else {
                minutes = date.getMinutes() + "";
            }
            if (beginMonth == getCurrentMonth()) {
                showTime = date.getDate() + "日" + date.getHours() + ":" + minutes;
            } else {
                showTime = (beginMonth + 1) + "月" + date.getDate() + "日" + date.getHours() + ":" + minutes;
            }
            return "预计" + showTime + "开始 ";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获得小时
     *
     * @param time
     * @return
     */
    public static String getHours(long time) {
        long second = time / 1000;
        long hour = second / 60 / 60;
        long minute = (second - hour * 60 * 60) / 60;
        long sec = (second - hour * 60 * 60) - minute * 60;
        String rHour = "";
        // 时
        if (hour < 10) {
            rHour = "0" + hour;
        } else {
            rHour = hour + "";
        }
        return rHour;
    }


    /**
     * 获得分
     *
     * @param time
     * @return
     */
    public static String getMintu(long time) {
        long second = time / 1000;
        long hour = second / 60 / 60;
        long minute = (second - hour * 60 * 60) / 60;
        long sec = (second - hour * 60 * 60) - minute * 60;

        String rMin = "";
        // 时
        if (minute < 10) {
            rMin = "0" + minute;
        } else {
            rMin = minute + "";
        }
        return rMin;
    }

    /**
     * 获得秒
     *
     * @param time
     * @return
     */
    public static String getSecond(long time) {
        long second = time / 1000;
        long hour = second / 60 / 60;
        long minute = (second - hour * 60 * 60) / 60;
        long sec = (second - hour * 60 * 60) - minute * 60;
        String rSs = "";
        // 秒
        if (sec < 10) {
            rSs = "0" + sec;
        } else {
            rSs = sec + "";
        }
        return rSs;
    }


}
