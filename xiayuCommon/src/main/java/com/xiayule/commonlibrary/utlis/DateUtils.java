package com.xiayule.commonlibrary.utlis;

import android.net.ParseException;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class DateUtils {
    // protected static Log logger = LogFactory.getLog(DateUtil.class);

    // 格式：年－月－日 小时：分钟：秒
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";

    // 格式：年_月_日_小时_分钟_秒
    public static final String FORMAT_ONE_TWO = "yyyy_MM_dd_HH_mm_ss";

    // 格式：年－月－日 小时：分钟
    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";


    // 格式：年月日 小时分钟秒
    public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";

    // 格式：年－月－日
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
    // 格式：yyyyMMdd
    public static final String LONG_DATE_FORMAT_TWO = "yyyyMMdd";

    //格式不 补零
    public static final String LONG_NO_ZOO_FORMAT = "yyyy-M-d";

    // 格式：月－日
    public static final String SHORT_DATE_FORMAT = "MM-dd";
    // 格式：月－日
    public static final String SHORT_DATE_FORMAT1 = "MM/dd";
    //格式：04月-21日
    public static final String SHORT_DATE_FORMAT_TYPE = "MM月dd日";

    // 格式：小时：分钟：秒
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";

    // 格式 ：小时：分钟
    public static final String HOUR_MINUTE = "HH:mm";

    // 格式：年-月
    public static final String MONTG_DATE_FORMAT = "yyyy-MM";
    // 年的加减
    public static final int SUB_YEAR = Calendar.YEAR;

    // 月加减
    public static final int SUB_MONTH = Calendar.MONTH;

    // 天的加减
    public static final int SUB_DAY = Calendar.DATE;

    // 小时的加减
    public static final int SUB_HOUR = Calendar.HOUR;

    // 分钟的加减
    public static final int SUB_MINUTE = Calendar.MINUTE;

    // 秒的加减
    public static final int SUB_SECOND = Calendar.SECOND;

    // 格式：月－日-时-分
    public static final String SHORT_DATE_FORMAT_LONG_TIME = "MM-dd HH:mm";

    private static String datePattern = "MM/dd/yyyy";

    private static String timePattern = datePattern + " HH:MM a";
    // 格式：年-月-日
    public static final String MONTG_DATE_FORMAT_YEAR_DAY = "yyyy年MM月dd日";

    // 格式：年.月
    public static final String TEACHER_DATE_FORMAT_YEAR = "yyyy.MM";

    /**
     * Return default datePattern (MM/dd/yyyy)
     *
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        return datePattern;
    }

    static final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四",
            "星期五", "星期六"};

    @SuppressWarnings("unused")
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public DateUtils() {
    }

    /**
     * 周代码转换
     *
     * @param week
     * @return
     */
    public static String DateToWeek(String week) {
        String day = null;
        if ("1".equals(week)) {
            day = "日";
        } else if ("2".equals(week)) {
            day = "一";
        } else if ("3".equals(week)) {
            day = "二";
        } else if ("4".equals(week)) {
            day = "三";
        } else if ("5".equals(week)) {
            day = "四";
        } else if ("6".equals(week)) {
            day = "五";
        } else if ("7".equals(week)) {
            day = "六";
        }

        return day;
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     *
     * @param dateStr
     * @return
     */
    public static Date stringtoDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            // log.error(e);
            d = null;
        }
        return d;
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     */
    public static Date stringtoDate(String dateStr, String format,
                                              ParsePosition pos) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr, pos);
        } catch (Exception e) {
            d = null;
        }
        return d;
    }

    /**
     * 把日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            // log.error(e);
        }
        return result;
    }

    /**
     * 获取当前时间的指定格式
     *
     * @param format
     * @return
     */
    public static String getCurrDate(String format) {
        return dateToString(new Date(), format);
    }

    /**
     * 获取当前时间 yyyy-mm-dd hh:mm:ss
     */
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * @param dateStr
     * @param amount
     * @return
     */
    public static String dateSub(int dateKind, String dateStr, int amount) {
        Date date = stringtoDate(dateStr, FORMAT_ONE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateKind, amount);
        return dateToString(calendar.getTime(), FORMAT_ONE);
    }

    /**
     * 两个日期相减
     *
     * @param firstTime
     * @param secTime
     * @return 相减得到的秒数
     */
    public static long timeSub(String firstTime, String secTime) {
        long first = stringtoDate(firstTime, FORMAT_ONE).getTime();
        long second = stringtoDate(secTime, FORMAT_ONE).getTime();
        return (second - first) / 1000;
    }

    /**
     * 转换日期格式
     *
     * @param serverTime
     * @return
     */
    public static String getConversionTime(String serverTime, String format) {
        return dateToString(stringtoDate(serverTime, FORMAT_ONE), format);
    }


    /**
     * 获得某月的天数
     *
     * @param year  int
     * @param month int
     * @return int
     */
    public static int getDaysOfMonth(String year, String month) {
        int days = 0;
        if (month.equals("1") || month.equals("3") || month.equals("5")
                || month.equals("7") || month.equals("8") || month.equals("10")
                || month.equals("12")) {
            days = 31;
        } else if (month.equals("4") || month.equals("6") || month.equals("9")
                || month.equals("11")) {
            days = 30;
        } else {
            if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
                    || Integer.parseInt(year) % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }

        return days;
    }

    /**
     * 获取某年某月的天数
     *
     * @param year  int
     * @param month int 月份[1-12]
     * @return int
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前日期
     *
     * @return int
     */
    public static int getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得当前月份
     *
     * @return int
     */
    public static int getToMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得当前年份
     *
     * @return int
     */
    public static int getToYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的天
     *
     * @param date Date
     * @return int
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的年
     *
     * @param date Date
     * @return int
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的周
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String week = dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        return week;
    }

    /**
     * 返回日期所在周的周一
     *
     * @param date
     * @return
     */
    public static String getDateWeekMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = 0;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            week = 6;
        } else {
            week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        }
        calendar.add(Calendar.DAY_OF_YEAR, -week);
        return dateToString(calendar.getTime(), LONG_DATE_FORMAT);
    }

    /**
     * 返回日期所在周的周一
     *
     * @param date
     * @return
     */
    public static String getWeekMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = 0;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            week = 6;
        } else {
            week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        }
        calendar.add(Calendar.DAY_OF_YEAR, -week);
        return dateToString(calendar.getTime(), LONG_DATE_FORMAT);
    }

    /**
     * 得到本周周一
     *
     * @param date
     */
    public static String getMondayOfThisWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return dateToString(c.getTime(), LONG_DATE_FORMAT);
    }

    /**
     * 得到本周周日
     *
     * @param date
     */
    public static String getSundayOfThisWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return dateToString(c.getTime(), LONG_DATE_FORMAT);
    }

    /**
     * 返回日期所在下周的周一
     *
     * @param date
     * @return
     */
    public static String getNextWeekMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH, 1);
        int week = 0;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        } else {
            week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        }

        calendar.add(Calendar.DAY_OF_YEAR, -week);
        return dateToString(calendar.getTime(), LONG_DATE_FORMAT);
    }

    /**
     * 返回日期所在上周的周一
     *
     * @param date
     * @return
     */
    public static String getLastWeekMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        calendar.add(Calendar.DAY_OF_YEAR, -week);
        return dateToString(calendar.getTime(), LONG_DATE_FORMAT);
    }

    /**
     * 返回日期所在周的周日
     *
     * @param date
     * @return
     */
    public static String getWeekSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = 7 - (calendar.get(Calendar.DAY_OF_WEEK) - 1);
        calendar.add(Calendar.DAY_OF_YEAR, week);
        return dateToString(calendar.getTime(), LONG_DATE_FORMAT);
    }

    /**
     * 返回日期所在下周的周日
     *
     * @param date
     * @return
     */
    public static String getNextWeekSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH, 1);
        int week = 7 - (calendar.get(Calendar.DAY_OF_WEEK) - 1);
        calendar.add(Calendar.DAY_OF_YEAR, week);
        return dateToString(calendar.getTime(), LONG_DATE_FORMAT);
    }

    /**
     * 返回日期所在下一周周一的日期
     *
     * @param date
     * @return
     */
    public static String getLastWeekSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        int week = 7 - (calendar.get(Calendar.DAY_OF_WEEK) - 1);
        calendar.add(Calendar.DAY_OF_YEAR, week);
        return dateToString(calendar.getTime(), LONG_DATE_FORMAT);
    }

    /**
     * 根据开始日期和结束日期，获取两个日期之间周数
     *
     * @param iBegin
     * @param iEnd
     * @return
     */
    public static int getWeek(Date iBegin, Date iEnd) {

        // 获取第一周周一的日期
        Date beginWeek = stringtoDate(getWeekMonday(iBegin), LONG_DATE_FORMAT);
        // 获取最后一周下周周一的日期
        Date endWeek = stringtoDate(getNextWeekMonday(iEnd), LONG_DATE_FORMAT);
        long day = dayDiff(beginWeek, endWeek);
        int weeks = 0;
        if (day > 0) {
            weeks = (int) (day / 7);
        }

        return weeks;
    }

    /**
     * 根据开始日期，获取当前时间的周次
     *
     * @param iBegin
     * @return
     */
    public static int getCurrentWeek(Date iBegin) {

        // 获取第一周周一的日期
        Date beginWeek = stringtoDate(getWeekMonday(iBegin), LONG_DATE_FORMAT);
        // 获取当前周 下周周一的日期
        Date endWeek = stringtoDate(
                getNextWeekMonday(stringtoDate(getCurrDate(LONG_DATE_FORMAT),
                        LONG_DATE_FORMAT)), LONG_DATE_FORMAT);
        long day = dayDiff(beginWeek, endWeek);
        int weeks = 0;
        if (day > 0) {
            weeks = (int) (day / 7);
        }

        return weeks;
    }

    /**
     * 返回日期的月份，1-12
     *
     * @param date Date
     * @return int
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
     *
     * @param date1 Date
     * @param date2 Date
     * @return long
     */
    public static long dayDiff(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / 86400000;
    }

    /**
     * 比较两个日期的年差
     *
     * @param before
     * @param after
     * @return
     */
    public static int yearDiff(String before, String after) {
        Date beforeDay = stringtoDate(before, LONG_DATE_FORMAT);
        Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
        return getYear(afterDay) - getYear(beforeDay);
    }

    /**
     * 比较指定日期与当前日期的差
     *
     * @param after
     * @return
     */
    public static int yearDiffCurr(String after) {
        Date beforeDay = new Date();
        Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
        return getYear(beforeDay) - getYear(afterDay);
    }

    /**
     * 比较指定日期与当前日期的差
     *
     * @param before
     * @return
     * @author chenyz
     */
    public static long dayDiffCurr(String before) {
        Date currDate = DateUtils.stringtoDate(currDay(), LONG_DATE_FORMAT);
        Date beforeDate = stringtoDate(before, LONG_DATE_FORMAT);
        return (currDate.getTime() - beforeDate.getTime()) / 86400000;

    }

    /**
     * 获取每月的第一周
     *
     * @param year
     * @param month
     * @return
     * @author chenyz
     */
    public static int getFirstWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取每月的最后一周
     *
     * @param year
     * @param month
     * @return
     * @author chenyz
     */
    public static int getLastWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, getDaysOfMonth(year, month));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得当前日期字符串，格式"yyyy_MM_dd_HH_mm_ss"
     *
     * @return
     */
    public static String getCurrent() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        StringBuffer sb = new StringBuffer();
        sb.append(year).append("_").append(addzero(month, 2)).append("_")
                .append(addzero(day, 2)).append("_").append(addzero(hour, 2))
                .append("_").append(addzero(minute, 2)).append("_")
                .append(addzero(second, 2));
        return sb.toString();
    }

    /**
     * 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String getNow() {
        Calendar today = Calendar.getInstance();
        return dateToString(today.getTime(), FORMAT_ONE);
    }

    /**
     * 获取当前时间
     *
     * @param strFormat --指定格式
     * @return
     */
    public static String getNow(String strFormat) {
        Calendar today = Calendar.getInstance();
        return dateToString(today.getTime(), strFormat);
    }

    /**
     * 判断日期是否有效,包括闰年的情况
     *
     * @param date YYYY-mm-dd
     * @return
     */
    public static boolean isDate(String date) {
        StringBuffer reg = new StringBuffer(
                "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }

    /**
     * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
     *
     * @param date   日期 为null时表示当天
     * @param months 相加(相减)的月数
     */
    public static Date nextMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 取得指定日期过 day 天后的日期 (当 day 为负数表示指日期之前);
     *
     * @param date 日期 为null时表示当天
     */
    public static Date nextDay(Date date, int day) {

        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    /**
     * 取得距离今天 day 日的日期
     *
     * @param day
     * @param format
     * @return
     * @author chenyz
     */
    public static String nextDay(int day, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, day);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 取得指定日期过 day 周后的日期 (当 day 为负数表示指定月之前)
     *
     * @param date 日期 为null时表示当天
     */
    public static Date nextWeek(Date date, int week) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.WEEK_OF_MONTH, week);
        return cal.getTime();
    }

    /**
     * 获取当前的日期(yyyy-MM-dd)
     */
    public static String currDay() {
        return DateUtils.dateToString(new Date(), DateUtils.LONG_DATE_FORMAT);
    }

    /**
     * 获取当前的日期(yyyy-MM-dd)
     */
    public static String currDay(String format) {
        return DateUtils.dateToString(new Date(), format);
    }

    /**
     * 获取昨天的日期
     *
     * @return
     */
    public static String befoDay() {
        return befoDay(DateUtils.LONG_DATE_FORMAT);
    }

    /**
     * 根据时间类型获取昨天的日期
     *
     * @param format
     * @return
     * @author chenyz
     */
    public static String befoDay(String format) {
        return DateUtils.dateToString(DateUtils.nextDay(new Date(), -1), format);
    }

    /**
     * 获取明天的日期
     */
    public static String tomorrowDay() {
        return DateUtils.dateToString(DateUtils.nextDay(new Date(), 1),
                DateUtils.LONG_DATE_FORMAT);
    }

    /**
     * 获取明天的日期
     */
    public static String tomorrowDay(String format) {
        return DateUtils.dateToString(DateUtils.nextDay(new Date(), 1), format);
    }

    /**
     * 获取明天的日期(yyyy-mm-dd hh:mm:ss)
     */
    public static String tomorrowDayTime() {
        return nextDay(1, DateUtils.FORMAT_ONE);
    }

    /**
     * 获取后天的日期(yyyy-mm-dd hh:mm:ss)
     */
    public static String afterDayTime() {
        return nextDay(2, DateUtils.FORMAT_ONE);
    }

    /**
     * 取得当前时间距离1900/1/1的天数
     *
     * @return
     */
    public static int getDayNum() {
        int daynum = 0;
        GregorianCalendar gd = new GregorianCalendar();
        Date dt = gd.getTime();
        GregorianCalendar gd1 = new GregorianCalendar(1900, 1, 1);
        Date dt1 = gd1.getTime();
        daynum = (int) ((dt.getTime() - dt1.getTime()) / (24 * 60 * 60 * 1000));
        return daynum;
    }

    /**
     * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
     *
     * @param day
     * @return
     */
    public static Date getDateByNum(int day) {
        GregorianCalendar gd = new GregorianCalendar(1900, 1, 1);
        Date date = gd.getTime();
        date = nextDay(date, day);
        return date;
    }

    /**
     * 针对yyyy-MM-dd HH:mm:ss格式,显示yyyymmdd
     */
    public static String getYmdDateCN(String datestr) {
        if (datestr == null)
            return "";
        if (datestr.length() < 10)
            return "";
        StringBuffer buf = new StringBuffer();
        buf.append(datestr.substring(0, 4)).append(datestr.substring(5, 7))
                .append(datestr.substring(8, 10));
        return buf.toString();
    }

    /**
     * 获取本月第一天
     *
     * @param format
     * @return
     */
    public static String getFirstDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 获取某月第一天
     *
     * @param iDate
     * @param format
     * @return
     */
    public static String getFirstDayOfMonth(Date iDate, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(iDate);
        cal.set(Calendar.DATE, 1);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 获取本月最后一天
     *
     * @param format
     * @return
     */
    public static String getLastDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 获取某月最后一天
     *
     * @param format
     * @return
     */
    public static String getLastDayOfMonth(Date iDate, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(iDate);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     *
     * @param sourceDate
     * @param formatLength
     * @return 重组后的数据
     */
    public static String addzero(int sourceDate, int formatLength) {
        /*
         * 0 指前面补充零 formatLength 字符总长度为 formatLength d 代表为正数。
         */
        String newString = String.format("%0" + formatLength + "d", sourceDate);
        return newString;
    }

    /**
     * 判断是否在2个时间点内
     *
     * @param iStartDate  开始时间
     * @param iEndDate    结束时间
     * @param iSelectDate 选择的时间
     * @return
     */
    public static Boolean isTimeQuantum(String iStartDate, String iEndDate,
                                        String iSelectDate) {
        int startDiff = yearDiff(iStartDate, iSelectDate);
        int endDiff = yearDiff(iSelectDate, iEndDate);
        if (startDiff >= 0 && endDiff > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static final Date str2Date(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            return null;
        } catch (java.text.ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

        return (date);
    }

    public static final String date2Str(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(datePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    public static final String date2Str(Date aDate, String format) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(format);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    public static final String date2Str(String pattern, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(pattern);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    // 日期格式转换成时间戳
    public static long getTimeStamp(String pattern, String strDate) {
        long returnTimeStamp = 0;
        Date aDate = null;
        try {
            aDate = convertStringToDate(pattern, strDate);
        } catch (ParseException pe) {
            aDate = null;
        }
        if (aDate == null) {
            returnTimeStamp = 0;
        } else {
            returnTimeStamp = aDate.getTime();
        }
        return returnTimeStamp;
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate)
            throws ParseException {
        Date aDate = null;

        try {

            aDate = convertStringToDate(datePattern, strDate);
        } catch (ParseException pe) {
            // log.error("Could not convert '" + strDate
            // + "' to a date, throwing exception");
            pe.printStackTrace();
            return null;

        }
        return aDate;
    }

    /**
     * This method generates a string representation of a date/time in the
     * format you specify on input
     *
     * @param aMask   the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @throws ParseException
     * @see SimpleDateFormat
     */
    public static final Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            return null;
        } catch (java.text.ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

        return (date);
    }

    //******************** 仿微信时间显示*******************//

    /**
     * 时间戳格式转换
     */
    public static String getNewChatTime(long timesamp) {
        String result = "";
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timesamp);

        String timeFormat = "M月d日 HH:mm";
        String yearTimeFormat = "yyyy年M月d日 HH:mm";
        String am_pm = "";
        int hour = otherCalendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 6) {
            am_pm = "凌晨";
        } else if (hour >= 6 && hour < 12) {
            am_pm = "早上";
        } else if (hour == 12) {
            am_pm = "中午";
        } else if (hour > 12 && hour < 18) {
            am_pm = "下午";
        } else if (hour >= 18) {
            am_pm = "晚上";
        }
        timeFormat = "M月d日 " + am_pm + "HH:mm";
        yearTimeFormat = "yyyy年M月d日 " + am_pm + "HH:mm";

        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int todayMonth = todayCalendar.get(Calendar.MONTH);
            int otherMonth = otherCalendar.get(Calendar.MONTH);
            if (todayMonth == otherMonth) {//表示是同一个月
                int temp = todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = getHourAndMin(timesamp);
                        break;
                    case 1:
                        result = "昨天 " + getHourAndMin(timesamp);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if (dayOfMonth == todayOfMonth) {//表示是同一周
                            int dayOfWeek = otherCalendar.get(Calendar.DAY_OF_WEEK);
                            if (dayOfWeek != 1) {//判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1] + getHourAndMin(timesamp);
                            } else {
                                result = getTime(timesamp, timeFormat);
                            }
                        } else {
                            result = getTime(timesamp, timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timesamp, timeFormat);
                        break;
                }
            } else {
                result = getTime(timesamp, timeFormat);
            }
        } else {
            result = getYearTime(timesamp, yearTimeFormat);
        }
        return result;
    }

    /**
     * 当天的显示时间格式
     *
     * @param time
     * @return
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     *
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTime(long time, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     *
     * @param time
     * @param yearTimeFormat
     * @return
     */
    public static String getYearTime(long time, String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat);
        return format.format(new Date(time));
    }

    /**
     * 时间戳格式转换
     */
    public static String getFormatTime(long timesamp) {
        String result = "";
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timesamp);

        SimpleDateFormat timeFormat = new SimpleDateFormat("M月d日 HH:mm");
        SimpleDateFormat yearTimeFormat = new SimpleDateFormat("yyyy年M月d日 HH:mm");
        SimpleDateFormat hourAndMinFormat = new SimpleDateFormat("HH:mm");

        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int todayMonth = todayCalendar.get(Calendar.MONTH);
            int otherMonth = otherCalendar.get(Calendar.MONTH);
            if (todayMonth == otherMonth) {//表示是同一个月
                int temp = todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = hourAndMinFormat.format(new Date(timesamp));
                        break;
                    case 1:
                        result = "昨天 " + hourAndMinFormat.format(new Date(timesamp));
                        break;

                    default:
                        result = timeFormat.format(new Date(timesamp));
                        break;
                }
            } else {
                result = timeFormat.format(new Date(timesamp));
            }
        } else {
            result = yearTimeFormat.format(new Date(timesamp));
        }
        return result;
    }

    /**
     * 将日期格式的字符串转换为长整型
     *
     * @param date
     * @return
     */
    public static long convert2long(String date) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 秒换算为时分秒
     *
     * @param second
     * @return
     */
    public static String cal(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
        if (d == 0 && s < 10) {
            return d + ":" + "0" + s;
        }
        if (d == 0 && s > 10) {
            return d + ":" + s;
        }
        if (d < 10 && s < 10) {
            return "0" + d + ":" + "0" + s;
        }
        if (d < 10 && s > 10) {
            return "0" + d + ":" + s;
        }

        if (d > 10 && s > 10) {
            return d + ":" + s;
        }

        if (d > 10 && s < 10) {
            return d + ":" + "0" + s;
        }
        return d + ":" + s;
    }


    /**
     * 返回日时分秒
     *
     * @param second
     * @return
     */
    public static String secondToTime(long second) {

        //转换的天数
        long days = second / 86400;
        //剩余秒数
        second = second % 86400;
        //转换小时数
        long hours = second / 3600;
        //剩余秒数
        second = second % 3600;
        //转换分钟
        long minutes = second / 60;
        //剩余秒数
        second = second % 60;

        if (0 < days) {
            return days + "天 " + (hours < 10 ? "0" + hours : hours) + "：" + (minutes < 10 ? "0" + minutes : minutes)
                    + "：" + (second < 10 ? "0" + second : second);
        } else {
            return (hours < 10 ? "0" + hours : hours) + "：" + (minutes < 10 ? "0" + minutes : minutes)
                    + "：" + (second < 10 ? "0" + second : second);
        }
    }
}
