package com.softserve.lv460.application.dto.localDevice;

import lombok.Data;

@Data
public class LocalDeviceRequest {
    private Long locationId;
    private Long supportedDeviceId;
}
