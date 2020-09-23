package com.danpopescu.shop.web.payload;

import lombok.Getter;

@Getter
public class JwtAuthResponse {

    private final String accessToken;
    private final String tokenType = "Bearer";

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
