package com.cxl.iot.security;


import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class DingTalkSignValidator {

    @Value("${dingtalk.robot.secret}")
    private String secret;

    private static final long ONE_HOUR_MILLIS = 60*60*1000;

    public boolean validateSign(HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String timestamp = request.getHeader("timestamp");
        String signFromRequest = request.getHeader("sign");
        Long timestampLong = Long.parseLong(timestamp);
        Long currentTime = System.currentTimeMillis();
        long interval = currentTime - timestampLong;

        String appSecret = secret;
        String stringToSign = timestamp + "\n" + appSecret;

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        String sign = new String(Base64.encodeBase64(signData));
        return sign.equals(signFromRequest) && ONE_HOUR_MILLIS > interval;
    }

}
