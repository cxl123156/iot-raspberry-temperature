package com.cxl.iot.collector;

import com.cxl.iot.entity.DingTalkOutgoingRequest;
import com.cxl.iot.entity.DingTalkRobotResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
public class TemperatureCollector {

    @Value("${raspberry.temperature.file}")
    private String temperatureFilePath;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH点mm分");

    /**
     * 获取树莓派温度
     *
     * @param outgoingRequest 钉钉请求内容
     * @return DingTalkRobotResult
     * @throws IOException IO异常
     */
    public DingTalkRobotResult getTemperature(DingTalkOutgoingRequest outgoingRequest) throws IOException {
        float templateValue = -1;
        File file = new File(temperatureFilePath);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        if (fileChannel != null) {
            fileChannel.read(byteBuffer);
        }
        String content = new String(byteBuffer.array());
        String[] strArray = content.split("t=");
        if (strArray.length == 2) {
            templateValue = Float.parseFloat(strArray[1].trim());
            templateValue /= 1000;
        }
        return buildMsgTemplate(templateValue, outgoingRequest);
    }

    private DingTalkRobotResult buildMsgTemplate(float templateValue, DingTalkOutgoingRequest outgoingRequest) throws IOException {
        Date current = new Date(System.currentTimeMillis());
        String time = DATE_FORMAT.format(current);
        String dingTalkMsgTemplate = getMsgTemplate();
        String msg = dingTalkMsgTemplate.replace("${temperatureValue}", String.valueOf(templateValue)).replace("${time}", time);
        DingTalkRobotResult.MarkDown markDown = DingTalkRobotResult.MarkDown.builder().text(msg).title("树莓派监控室温").build();
        DingTalkRobotResult.At at = DingTalkRobotResult.At.builder().build();
        return DingTalkRobotResult.builder().msgType("markdown").markDown(markDown).at(at).build();
    }

    private String getMsgTemplate() throws IOException {
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("msg_template")).getPath();
        RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        if (fileChannel != null) {
            fileChannel.read(byteBuffer);
        }
        return new String(byteBuffer.array()).trim();
    }

}
