package com.mail.myapplication.ui.utils;

import org.xutils.common.util.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/1.
 */

public class TimeUtilsX {
    //字符串转时间戳
    public static String getTime(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    //时间戳转字符串  yyyy/MM/dd hh:mm
    public static String getStrTime(String timeStamp) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long l = Long.valueOf(timeStamp) * 1000;
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }


    //时间戳转字符串
    public static String getStrTime2() {
        String timeString;
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
//        long l = Long.valueOf(timeStamp) * 1000;
        timeString = sdf.format(new Date());//单位秒
        return timeString;
    }



    //时间戳转字符串
    public static String getStrTime4(String timeStamp) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        long l = Long.valueOf(timeStamp) * 1000;
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    //时间戳转字符串
    public static String getStrTime3() {
        String timeString;
        SimpleDateFormat sdf = new SimpleDateFormat("mmss");
//        long l = Long.valueOf(timeStamp) * 1000;
        timeString = sdf.format(new Date());//单位秒
        return timeString;
    }


    //时间戳转字符串
    public static String getStrTime(String timeStamp, String sr) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(sr);
        long l = Long.valueOf(timeStamp) * 1000;
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }
    public static String formatSecond(Object second) {
        String html = "0秒";
        if (second != null) {
            Double s = (Double) second;
            String format;
            Object[] array;
            Integer hours = (int) (s / (60 * 60));
            Integer minutes = (int) (s / 60 - hours * 60);
            Integer seconds = (int) (s - minutes * 60 - hours * 60 * 60);
            if (hours > 0) {
                format = "%1$,d时%2$,d分%3$,d秒";
                array = new Object[]{hours, minutes, seconds};
            } else if (minutes > 0) {
                format = "%1$,d分%2$,d秒";
                array = new Object[]{minutes, seconds};
            } else {
                format = "%1$,d秒";
                array = new Object[]{seconds};
            }
            Integer i;
//            if (hours > 48) {
//                i = hours / 24;
//                html = i + "天前";
//            } else {
//                html = "还剩" + String.format(format, array);
//            }
            html = "还剩" + String.format(format, array);
        }
        return html;
    }

    public static String mformatSecond(Object second) {
        String html = "0秒";
        if (second != null) {
            Double s = (Double) second;
            String format;
            Object[] array;
            Integer hours = (int) (s / (60 * 60));
            Integer minutes = (int) (s / 60 - hours * 60);
            Integer seconds = (int) (s - minutes * 60 - hours * 60 * 60);
            if (hours > 0) {
                format = "%1$,d时%2$,d分%3$,d秒";
                array = new Object[]{hours, minutes, seconds};
            } else if (minutes > 0) {
                format = "%1$,d分%2$,d秒";
                array = new Object[]{minutes, seconds};
            } else {
                format = "%1$,d秒";
                array = new Object[]{seconds};
            }
            Integer i;
//            if (hours > 48) {
//                i = hours / 24;
//                html = i + "天前";
//            } else {
//                html = "还剩" + String.format(format, array);
//            }
            html = String.format(format, array);
        }
        return html;
    }

    public static String formatSecond3(Object second) {
        LogUtil.e("time============="+second.toString());
        String html = "0秒";
        if (second != null) {
            Double s = (Double) second;
            String format;
            Object[] array;
            Integer hours = (int) (s / (60 * 60));
            Integer minutes = (int) (s / 60 - hours * 60);
            Integer seconds = (int) (s - minutes * 60 - hours * 60 * 60);
            if (hours > 0) {
                format = "%1$,d时%2$,d分%3$,d秒";
                array = new Object[]{hours, minutes, seconds};
            } else if (minutes > 0) {
                format = "%1$,d分%2$,d秒";
                array = new Object[]{minutes, seconds};
            } else {
                format = "%1$,d秒";
                array = new Object[]{seconds};
            }
            Integer i;
            if (hours > 48) {
                i = hours / 24;
                html = i + "天前";
                return html;
            }
            if (hours >= 1) {
                html = hours + "小时前";
                return html;
            }
            if (minutes >= 1) {
                html = minutes + "分钟前";
                return html;
            }
            if (seconds > 0) {
                html = seconds + "秒前";
                return html;
            }
        }
        return html;
    }

    public static String formatSecond2(Object second) {
        String html = "0秒";
        if (second != null) {
            Double s = (Double) second;
            String format;
            Object[] array;
            Integer hours = (int) (s / (60 * 60));
            Integer minutes = (int) (s / 60 - hours * 60);
            Integer seconds = (int) (s - minutes * 60 - hours * 60 * 60);
            if (hours > 0) {
                format = "%1$,d'%2$,d'%3$,d'";
                array = new Object[]{hours, minutes, seconds};
            } else if (minutes > 0) {
                format = "%1$,d'%2$,d''";
                array = new Object[]{minutes, seconds};
            } else {
                format = "%1$,d''";
                array = new Object[]{seconds};
            }
            html = String.format(format, array);
        }
        return html;
    }

    public static String formatSecond4(Object second) {
        String html = "0秒";
        if (second != null) {
            Double s = (Double) second;
            String format;
            Object[] array;
            Integer hours = (int) (s / (60 * 60));
            Integer minutes = (int) (s / 60 - hours * 60);
            Integer seconds = (int) (s - minutes * 60 - hours * 60 * 60);
            if (hours > 0) {
                format = "%1$,d时%2$,d分%3$,d秒";
                array = new Object[]{hours, minutes, seconds};
            } else if (minutes > 0) {
                format = "%1$,d分%2$,d秒";
                array = new Object[]{minutes, seconds};
            } else {
                format = "%1$,d秒";
                array = new Object[]{seconds};
            }
            Integer i;
            if (hours > 48) {
                i = hours / 24;
                html = "还剩" + i + "天";
                return html;
            }
            if (hours >= 1) {
                html = "还剩" + hours + "小时";
                return html;
            }
            if (minutes >= 1) {
                html = "还剩" + minutes + "分钟";
                return html;
            }
            if (seconds > 0) {
                html = "还剩" + seconds + "秒";
                return html;
            }
        }
        return html;
    }

    public static String[] formatSecond5(Object second) {
        String[] str = new String[2];
        str[0]="0";
        str[1]="分钟";
        String html = "0秒";
        if (second != null) {
            Double s = (Double) second;
            String format;
            Object[] array;
            Integer hours = (int) (s / (60 * 60));
            Integer minutes = (int) (s / 60 - hours * 60);
            Integer seconds = (int) (s - minutes * 60 - hours * 60 * 60);
            if (hours > 0) {
                format = "%1$,d时%2$,d分%3$,d秒";
                array = new Object[]{hours, minutes, seconds};
            } else if (minutes > 0) {
                format = "%1$,d分%2$,d秒";
                array = new Object[]{minutes, seconds};
            } else {
                format = "%1$,d秒";
                array = new Object[]{seconds};
            }
            Integer i = 0;
            if (hours > 48) {
                i = hours / 24;
                html = "还剩" + i + "天";
                str[0] = i + "";
                str[1] = "天";
                return str;
            }
            if (hours >= 1) {
                html = "还剩" + hours + "小时";
                str[0] = hours + "";
                str[1] = "小时";
                return str;
            }
            if (minutes >= 1) {
                html = "还剩" + minutes + "分钟";
                str[0] = i + "";
                str[1] = "分钟";
                return str;
            }
            if (seconds > 0) {
                html = "还剩" + seconds + "秒";
                str[0] = i + "";
                str[1] = "秒";
                return str;
            }
        }
        return str;
    }

    public static String formatSecond6(Object second) {
        LogUtil.e("time============="+second.toString());
        String html = "0秒";
        if (second != null) {
            Long s = (Long) second;
            String format;
            Object[] array;
            Integer hours = (int) (s / (60 * 60));
            Integer minutes = (int) (s / 60 - hours * 60);
            Integer seconds = (int) (s - minutes * 60 - hours * 60 * 60);
            if (hours > 0) {
                format = "%1$,d时%2$,d分%3$,d秒";
                array = new Object[]{hours, minutes, seconds};
            } else if (minutes > 0) {
                format = "%1$,d分%2$,d秒";
                array = new Object[]{minutes, seconds};
            } else {
                format = "%1$,d秒";
                array = new Object[]{seconds};
            }
            Integer i;
            if (hours > 48) {
                i = hours / 24;
                html = i + "天前";
                return html;
            }
            if (hours >= 1) {
                html = hours + "小时前";
                return html;
            }
            if (minutes >= 1) {
                html = minutes + "分钟前";
                return html;
            }
            if (seconds > 0) {
                html = seconds + "秒前";
                return html;
            }
        }
        return html;
    }
}
