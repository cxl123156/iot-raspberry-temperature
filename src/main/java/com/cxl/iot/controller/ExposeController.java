package com.cxl.iot.controller;

import com.cxl.iot.entity.DingTalkOutgoingRequest;
import com.cxl.iot.entity.DingTalkRobotResult;
import com.cxl.iot.collector.TemperatureCollector;
import com.cxl.iot.security.DingTalkSignValidator;
import com.cxl.iot.service.DingTalkOutgoingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/iot")
public class ExposeController {

    @Autowired
    private TemperatureCollector temperatureCollector;

    @Autowired
    private DingTalkOutgoingService dingTalkOutgoingService;

    @Autowired
    private DingTalkSignValidator dingTalkSignValidator;

    @PostMapping("/temperature/get")
    public DingTalkRobotResult getTemperature(@RequestBody DingTalkOutgoingRequest outgoingRequest, HttpServletRequest request) throws Exception{
        if (dingTalkSignValidator.validateSign(request)) {
            return dingTalkOutgoingService.dealOutgoing(outgoingRequest);
        }else {
            return dingTalkOutgoingService.buildUnknownMsg(outgoingRequest,"认证异常");
        }
    }

    @GetMapping("/temperature/get")
    public DingTalkRobotResult getTemperatureGet() throws IOException {
        return temperatureCollector.getTemperature(DingTalkOutgoingRequest.builder().build());
    }
}
