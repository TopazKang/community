package org.paz.community.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
