package com.cxl.iot.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemperatureResult {

    private String status;

    private Integer value;

}
