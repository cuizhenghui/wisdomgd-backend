package com.think.common.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.Date;

public class OpinionUtils {

    public static int getTimeStatus(long flagTime, Integer timeLimit, Date startTime, Date curTime) {
        long between = DateUtil.between(startTime, curTime, DateUnit.SECOND);
        long limit = ObjectUtil.isNotNull(timeLimit) ? timeLimit*60*60 : 0;
        int timeStatus = 0;
        if (between > limit) { // 已超期
            timeStatus = 2;
        } else {
            if (between > (limit - flagTime)) { // 即将超期
                timeStatus = 1;
            }
        }
        return timeStatus;
    }

    /**
     * 转换蜂鸟类型到本系统类型
     * @param siteCls
     * @return
     */
    public static int transSiteClsToArtSource(Integer siteCls) {
        /**
         * 新华舆情系统内媒体来源类别：原类别编号-类别名称[转译后类别编号]
         * {1-新闻[1] 2-论坛[16] 3-贴吧[64] 4-微博[1048576] 5-微信[268435456]
         * 6-博客[256] 7-电子报[2] 8-视频[65536] 9-APP[4] 10-短视频[65536] 99-其他[-9999]}
         */
        //本系统 1-微博 2-微信 3-新闻 4-论坛 5-贴吧 6-APP 7-电子报 8-视频 9-博客 10-其他
        int artSource = 10;
        if (ObjectUtil.isNotNull(siteCls)) {
            switch (siteCls.intValue()) {
                case 1: artSource = 3; break;
                case 2: artSource = 7; break;
                case 4: artSource = 6; break;
                case 16: artSource = 4; break;
                case 64: artSource = 5; break;
                case 256: artSource = 9; break;
                case 65536: case 6553601: artSource = 8; break;
                case 1048576: artSource = 1; break;
                case 268435456: artSource = 2; break;
                case 8: case 32: case -9999: artSource = 10; break;
            }
        }
        return artSource;
    }
}
