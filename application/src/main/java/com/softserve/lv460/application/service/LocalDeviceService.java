package com.softserve.lv460.application.service;

import com.softserve.lv460.application.constant.ErrorMessage;
import com.softserve.lv460.application.dto.localDevice.LocalDeviceRequest;
import com.softserve.lv460.application.entity.DeviceTemplate;
import com.softserve.lv460.application.entity.LocalDevice;
import com.softserve.lv460.application.entity.Location;
import com.softserve.lv460.application.exception.exceptions.NotFoundIdException;
import com.softserve.lv460.application.repository.DeviceTemplateRepository;
import com.softserve.lv460.application.repository.LocalDeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LocalDeviceService {
    private LocalDeviceRepository localDeviceRepository;
    private DeviceTemplateRepository deviceTemplateRepository;
    private LocationService locationService;

    public LocalDevice findByUuid(String uuid) {
        return localDeviceRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundIdException(ErrorMessage.LOCAL_DEVICE_NOT_FOUND + uuid));
    }

    public List<LocalDevice> findAll() {
        return localDeviceRepository.findAll();
    }

    public List<LocalDevice> findAllByLocation(Location location) {
        return localDeviceRepository.findAllByLocation(location);
    }

    public LocalDevice update(LocalDeviceRequest localDevice) {
        LocalDevice localDeviceByUuid = findByUuid(localDevice.getUuid());

        localDeviceByUuid.setLocation(locationService.findOne(localDevice.getLocationId()));

        return localDeviceRepository.save(localDeviceByUuid);
    }

    public LocalDevice save(LocalDeviceRequest localDeviceRequest) {
        LocalDevice localDevice = new LocalDevice();

        localDevice.setLocation(locationService.findOne((localDeviceRequest.getLocationId())));
        DeviceTemplate deviceTemplate = deviceTemplateRepository.findById(localDeviceRequest.getDeviceTemplateId())
                .orElseThrow(() -> new NotFoundIdException(ErrorMessage.DEVICE_TEMPLATE_NOT_FOUND
                        + localDeviceRequest.getDeviceTemplateId()));
        localDevice.setDeviceTemplate(deviceTemplate);
        localDevice.setUuid(UUID.randomUUID().toString().substring(0, 32));

        return localDeviceRepository.save(localDevice);
    }

    public String delete(String uuid) {
        localDeviceRepository.delete(findByUuid(uuid));

        return uuid;
    }


}
