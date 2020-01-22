package com.softserve.lv460.application.dto.localDevice;

import com.softserve.lv460.application.entity.DeviceTemplate;
import com.softserve.lv460.application.entity.Location;
import lombok.Data;

@Data
public class LocalDeviceResponse {
    private String uuid;
    private Location location;
    private DeviceTemplate supportedDevice;
}