package com.softserve.lv460.application.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class JWTUserResponse {
  private String accessToken;
  private String refreshToken;
}
