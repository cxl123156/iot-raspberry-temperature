package com.cxl.iot.collector;

import com.cxl.iot.entity.DingTalkOutgoingRequest;
import com.cxl.iot.entity.DingTalkRobotResult;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TemperatureCollector {

    public static volatile float recentTemperature = 0;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH点mm分");


    /**
     * 获取树莓派温度
     *
     * @param outgoingRequest 钉钉请求内容
     * @return DingTalkRobotResult
     * @throws IOException IO异常
     */
    public DingTalkRobotResult getTemperature(DingTalkOutgoingRequest outgoingRequest) throws IOException {
        String msg;
        if (recentTemperature == 0) {
            msg = "读取温度异常";
            return buildMsgTemplate(msg);
        }
        Date current = new Date(System.currentTimeMillis());
        String time = DATE_FORMAT.format(current);
        String dingTalkMsgTemplate = getMsgTemplate();
        msg = dingTalkMsgTemplate.replace("${temperatureValue}", String.valueOf(recentTemperature)).replace("${time}", time);
        return buildMsgTemplate(msg);
    }

    private DingTalkRobotResult buildMsgTemplate(String msg) {
        DingTalkRobotResult.MarkDown markDown = DingTalkRobotResult.MarkDown.builder().text(msg).title("树莓派监控室温").build();
        DingTalkRobotResult.At at = DingTalkRobotResult.At.builder().build();
        return DingTalkRobotResult.builder().msgType("markdown").markDown(markDown).at(at).build();
    }


    private String getMsgTemplate() throws IOException {
        File file = new File("./msg_template");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        if (fileChannel != null) {
            fileChannel.read(byteBuffer);
        }
        return new String(byteBuffer.array()).trim();
    }

}
