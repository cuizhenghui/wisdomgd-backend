package com.think.common.enums;

/**
 * @author liujing
 * @version 1.0
 * @ClassName DataSource
 * @date 2019/1/15 15:24
 **/
public enum DataSource {
    // 舆情来源
    WEIBO(1, "微博"),
    WEIXIN(2, "微信"),
    NEWS(3, "新闻"),
    FORUM(4, "论坛"),
    TIEBA(5, "贴吧"),
    APP(6, "APP"),
    NEWSPAPER(7, "电子报"),
    VIDEO(8, "视频"),
    BLOG(9, "博客"),
    OTHER(10, "其他");
    private Integer source;
    private String desc;

    public Integer getSource() {
        return source;
    }

    public String getDesc() {
        return desc;
    }

    DataSource(Integer source, String desc) {
        this.source = source;
        this.desc = desc;
    }

    public static DataSource translateToDataSource(Integer siteCls) {
        if (siteCls == null) {
            return OTHER;
        }
        switch (siteCls) {
            case 1:
                return NEWS;
            case 2:
                return NEWSPAPER;
            case 4:
                return APP;
            case 8:
                return OTHER;
            case 16:
                return FORUM;
            case 32:
                return OTHER;
            case 64:
                return TIEBA;
            case 256:
                return BLOG;
            case 65536:
                return VIDEO;
            case 1048576:
                return WEIBO;
            case 268435456:
                return WEIXIN;
            case -9999:
                return OTHER;
            default:
                return OTHER;
        }
    }
}
