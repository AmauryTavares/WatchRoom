package com.example.watchroom;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Video {

    private String name;
    private String time;
    private String views;
    private String description;
    private String urlThumb;

    public Video(String name, String time, String views, String description, String urlThumb) {
        this.name = name;
        this.time = time;
        this.views = views;
        this.description = description;
        this.urlThumb = urlThumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlThumb() {
        return urlThumb;
    }

    public void setUrlThumb(String urlThumb) {
        this.urlThumb = urlThumb;
    }

    public static String TruncateText(String text, int limit) {
        String result = text;

        if (text.length() > limit) {
            result = result.substring(0, limit) + "...";
        }

        return result;
    }

    public static String ConvertViewsCount(BigInteger viewCount) {
        int compare1thousand = viewCount.compareTo(new BigInteger("1000"));
        int compare1million = viewCount.compareTo(new BigInteger("1000000"));
        int compare1billion = viewCount.compareTo(new BigInteger("1000000000"));

        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        if (compare1thousand == -1) {
            return viewCount.toString();
        } else if (compare1thousand >= 0 && compare1million == -1) {
            return df.format(viewCount.divide(new BigInteger("1000"))) + " mil";
        } else if(compare1million >= 0 && compare1billion == -1){
            return df.format(viewCount.divide(new BigInteger("1000000")))+ " mi";
        } else {
            return df.format(viewCount.divide(new BigInteger("1000000000")))+ " bi";
        }
    }

    public static String ConverttoHHMMSS(String youtubetime) {

        String pattern = new String("^PT(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+))S?$");

        Pattern r = Pattern.compile(pattern);
        String result;

        Matcher m = r.matcher(youtubetime);
        if (m.find()) {
            String hh = m.group(1);
            String mm = m.group(2);
            String ss = m.group(3);
            mm = mm !=null?mm:"0";
            ss = ss !=null?ss:"0";
            result = String.format("%02d:%02d", Integer.parseInt(mm), Integer.parseInt(ss));

            if (hh != null) {
                result = hh + ":" + result;
            }
        } else {
            result = "00:00";
        }
        return result;
    }
}
