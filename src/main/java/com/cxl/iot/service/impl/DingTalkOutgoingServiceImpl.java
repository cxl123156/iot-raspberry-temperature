package com.cxl.iot.service.impl;

import com.cxl.iot.collector.TemperatureCollector;
import com.cxl.iot.entity.DingTalkOutgoingRequest;
import com.cxl.iot.entity.DingTalkRobotResult;
import com.cxl.iot.service.DingTalkOutgoingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DingTalkOutgoingServiceImpl implements DingTalkOutgoingService {

    @Autowired
    private TemperatureCollector temperatureCollector;

    @Override
    public DingTalkRobotResult dealOutgoing(DingTalkOutgoingRequest request) throws IOException {
        if (request.getText().getContent().trim().equals("当前家里温度")) {
            return temperatureCollector.getTemperature(request);
        } else {
            return buildUnknownMsg(request,"抱歉，我还不明白这是什么意思");
        }
    }

    @Override
    public DingTalkRobotResult buildUnknownMsg(DingTalkOutgoingRequest outgoingRequest,String msg) {
        DingTalkRobotResult.MarkDown markDown = DingTalkRobotResult.MarkDown.builder().text(msg).title("unknown").build();
        DingTalkRobotResult.At at = DingTalkRobotResult.At.builder().build();
        return DingTalkRobotResult.builder().msgType("markdown").markDown(markDown).at(at).build();
    }
}
