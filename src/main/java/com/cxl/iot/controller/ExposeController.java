package com.cxl.iot.controller;

import com.cxl.iot.entity.DingTalkOutgoingRequest;
import com.cxl.iot.entity.DingTalkRobotResult;
import com.cxl.iot.collector.TemperatureCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/iot")
public class ExposeController {

    @Autowired
    private TemperatureCollector temperatureCollector;

    @PostMapping("/temperature/get")
    public DingTalkRobotResult getTemperature(@RequestBody DingTalkOutgoingRequest outgoingRequest) throws IOException {
        return temperatureCollector.getTemperature(outgoingRequest);
    }

    @GetMapping("/temperature/get")
    public DingTalkRobotResult getTemperatureGet() throws IOException {
        return temperatureCollector.getTemperature(DingTalkOutgoingRequest.builder().build());
    }
}
