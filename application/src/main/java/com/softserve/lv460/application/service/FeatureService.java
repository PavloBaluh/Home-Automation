package com.softserve.lv460.application.service;

import com.softserve.lv460.application.dto.feature.FeatureRequest;
import com.softserve.lv460.application.dto.feature.FeatureResponse;
import com.softserve.lv460.application.entity.Feature;
import com.softserve.lv460.application.repository.FeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FeatureService {

  private FeatureRepository featureRepository;

  public Feature create(FeatureRequest featureRequest) {
    Feature feature = new Feature();
    feature.setName(featureRequest.getName());
    feature.setDescription(featureRequest.getDescription());
    return featureRepository.save(feature);
  }

  public Feature findFeature(Long id) {
    return featureRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Feature with id " + id + "does not exists"));
  }

  public List<FeatureResponse> findAll() {
    return featureRepository.findAll().stream().map(FeatureService:: featureToResponse).collect(Collectors.toList());
  }

  public static FeatureResponse featureToResponse(Feature feature) {
    FeatureResponse featureResponse = new FeatureResponse();
    featureResponse.setId(feature.getId());
    featureResponse.setName(feature.getName());
    featureResponse.setDescription(feature.getDescription());
    return featureResponse;
  }

  public Feature update(Long id, FeatureRequest featureRequest) {
    Feature feature = findFeature(id);
    feature.setName(featureRequest.getName());
    feature.setDescription(featureRequest.getDescription());
    return featureRepository.save(feature);
  }

  public void delete(Long id) {
    Feature feature = findFeature(id);
    featureRepository.delete(feature);
  }
}