package com.cxl.iot.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

/**
 * {
 * "conversationId": "xxx",
 * "atUsers": [
 * {
 * "dingtalkId": "xxx",
 * "staffId":"xxx"
 * }
 * ],
 * "chatbotCorpId": "dinge8a565xxxx",
 * "chatbotUserId": "$:LWCP_v1:$Cxxxxx",
 * "msgId": "msg0xxxxx",
 * "senderNick": "杨xx",
 * "isAdmin": true,
 * "senderStaffId": "user123",
 * "sessionWebhookExpiredTime": 1613635652738,
 * "createAt": 1613630252678,
 * "senderCorpId": "dinge8a565xxxx",
 * "conversationType": "2",
 * "senderId": "$:LWCP_v1:$Ff09GIxxxxx",
 * "conversationTitle": "机器人测试-TEST",
 * "isInAtList": true,
 * "sessionWebhook": "https://oapi.dingtalk.com/robot/sendBySession?session=xxxxx",
 * "text": {
 * "content": " 你好"
 * },
 * "msgtype": "text"
 * }
 */
@Data
@Builder
public class DingTalkOutgoingRequest {

    @Tolerate
    public DingTalkOutgoingRequest() {
    }

    /**
     * 加密的会话ID。
     */
    private String conversationId;

    /**
     * 被@人的信息。
     */
    private List<AtUsers> atUsers;

    /**
     * 加密的机器人所在的企业corpId。
     */
    private String chatbotCorpId;

    /**
     * 加密的机器人ID。
     */
    private String chatbotUserId;

    /**
     * 加密的消息ID。
     */
    private String msgId;

    /**
     * 发送者昵称。
     */
    private String senderNick;

    /**
     * 是否为管理员。
     */
    private String isAdmin;

    /**
     * 企业内部群中@该机器人的成员userid。
     * 该字段在机器人发布线上版本后，才会返回。
     */
    private String senderStaffId;

    /**
     * 当前会话的Webhook地址过期时间。
     */
    private Long sessionWebhookExpiredTime;

    /**
     * 消息的时间戳，单位ms。
     */
    private Long createAt;

    /**
     * 企业内部群有的发送者当前群的企业corpId。
     */
    private String senderCorpId;

    /**
     * 1：单聊
     * 2：群聊
     */
    private String conversationType;

    /**
     * 加密的发送者ID。
     * 使用senderStaffId，作为发送者userid值。
     */
    private String senderId;

    /**
     * 群聊时才有的会话标题。
     */
    private String conversationTitle;

    /**
     * 是否在@列表中。
     */
    private Boolean isInAtList;

    /**
     * 当前会话的Webhook地址。
     */
    private String sessionWebhook;

    /**
     *
     */
    private Text text;

    /**
     * 目前只支持text。
     */
    private String msgtype;

    @Data
    @Builder
    public static class AtUsers {

        @Tolerate
        public AtUsers() {
        }

        /**
         * 加密的发送者ID。
         */
        private String dingtalkId;

        /**
         * 企业内部群有的发送者在企业内的userid。
         */
        private String staffId;
    }

    @Data
    @Builder
    public static class Text {

        @Tolerate
        public Text() {
        }

        /**
         * 消息文本
         */
        private String content;
    }

}
