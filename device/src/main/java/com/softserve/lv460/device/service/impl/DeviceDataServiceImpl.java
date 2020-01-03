package com.softserve.lv460.device.service.impl;

import com.softserve.lv460.device.config.DeviceCacheConfig;
import com.softserve.lv460.device.config.PropertiesConfig;
import com.softserve.lv460.device.document.DeviceData;
import com.softserve.lv460.device.exceptions.DeviceNotRegisteredException;
import com.softserve.lv460.device.exceptions.Massages;
import com.softserve.lv460.device.repositiry.DeviceDataRepository;
import com.softserve.lv460.device.service.DeviceDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class DeviceDataServiceImpl implements DeviceDataService {
  private ConcurrentLinkedQueue<DeviceData> batch = new ConcurrentLinkedQueue<>();
  private DeviceDataRepository deviceDataRepository;
  private DeviceCacheConfig deviceCacheConfig;
  private PropertiesConfig propertiesConfig;

  public DeviceDataServiceImpl(DeviceDataRepository deviceDataRepository, DeviceCacheConfig deviceCacheConfig, PropertiesConfig propertiesConfig) {
    this.deviceDataRepository = deviceDataRepository;
    this.deviceCacheConfig = deviceCacheConfig;
    this.propertiesConfig = propertiesConfig;
  }

  @Override
  public void save(DeviceData deviceData) throws ExecutionException {
    if (deviceCacheConfig.isKeyValid(deviceData.getUuId())) {
      addToBatch(deviceData);
    } else throw new DeviceNotRegisteredException(Massages.DEVICE_NOT_REGISTERED);
  }

  private void addToBatch(DeviceData deviceData) {
    batch.add(deviceData);
    if (batch.size() == propertiesConfig.getBatchSize()) {
      System.out.println(batch);
      deviceDataRepository.saveAll(batch);
      batch.clear();
    }
  }

  @Bean
  private void timer() {
    final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
    ses.scheduleWithFixedDelay(() -> {
      if (!batch.isEmpty()) {
        deviceDataRepository.saveAll(batch);
        batch.clear();
      }
    }, 0, propertiesConfig.getBatchTime(), TimeUnit.SECONDS);
  }


}