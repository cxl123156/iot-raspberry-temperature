package com.cxl.iot.service;

import com.cxl.iot.entity.DingTalkOutgoingRequest;
import com.cxl.iot.entity.DingTalkRobotResult;

import java.io.IOException;

public interface DingTalkOutgoingService {

    DingTalkRobotResult dealOutgoing(DingTalkOutgoingRequest request) throws IOException;

    DingTalkRobotResult buildUnknownMsg(DingTalkOutgoingRequest outgoingRequest,String msg);
}
