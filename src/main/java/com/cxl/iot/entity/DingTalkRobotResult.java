package com.cxl.iot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * {
 * "msgtype": "markdown",
 * "markdown": {
 * "title":"杭州天气",
 * "text": "#### 杭州天气 @150XXXXXXXX \n> 9度，西北风1级，空气良89，相对温度73%\n> ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\n> ###### 10点20分发布 [天气](https://www.dingalk.com) \n"
 * },
 * "at": {
 * "atMobiles": [
 * "150XXXXXXXX"
 * ],
 * "atUserIds": [
 * "user123"
 * ],
 * "isAtAll": false
 * }
 * }
 */
@Data
@Builder
public class DingTalkRobotResult {

    /**
     * 首屏会话透出的展示内容。
     */
    @JsonProperty("msgtype")
    private String msgType;

    @JsonProperty("markdown")
    private MarkDown markDown;

    private At at;

    @Data
    @Builder
    public static class MarkDown {

        /**
         * 首屏会话透出的展示内容。
         */
        private String title;

        /**
         * markdown格式的消息内容。
         */
        private String text;

    }

    @Data
    @Builder
    public static class At {

        /**
         * 被@人的手机号。
         * 消息内容text内要带上"@手机号"，跟atMobiles参数结合使用，才有@效果，如上示例
         */
        private List<String> atMobiles;

        /**
         * 被@人的用户userid。
         */
        private List<String> atUserIds;

        /**
         * '@所有人是true，否则为false。
         */
        private Boolean isAtAll;
    }


}
