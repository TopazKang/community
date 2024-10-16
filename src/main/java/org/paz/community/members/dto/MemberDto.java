package org.paz.community.members.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

public class MemberDto {

    @Getter
    public static class SignIn {
        private String nickname;
        private String email;
        private String password;

        public SignIn(String nickname, String email, String password) {
            this.nickname = nickname;
            this.email = email;
            this.password = password;
        }
    }
    @Getter
    public static class EmailChk {
        private String email;

        public EmailChk(String email){
            this.email = email;
        }
    }

    @Getter
    public static class NicknameInput {
        private String nickname;

        public NicknameInput(String nickname){
            this.nickname = nickname;
        }
    }

    @Getter
    public static class Login {
        private String email;
        private String password;

        public Login(String email, String password){
            this.email = email;
            this.password = password;
        }
    }
    @Getter
    public static class LoginReturn {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private String profile_image_path;

        public LoginReturn(String grantType, String accessToken, String refreshToken, String profile_image_path){
            this.grantType = grantType;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.profile_image_path = profile_image_path;
        }
    }
    @Getter
    public static class Info {
        private String nickname;
        private String email;

        public Info(String nickname, String email){
            this.nickname = nickname;
            this.email = email;
        }
    }
    @Getter
    public static class ModifyInfo {
        private String nickname;

        @JsonCreator
        public ModifyInfo(String nickname){
            this.nickname = nickname;
        }
    }
    @Getter
    public static class ChangePassword {
        private String password;

        @JsonCreator
        public ChangePassword(String password){
            this.password = password;
        }
    }
}

// https://velog.io/@p4rksh/Spring-Boot%EC%97%90%EC%84%9C-%EA%B9%94%EB%81%94%ED%95%98%EA%B2%8C-DTO-%EA%B4%80%EB%A6%AC%ED%95%98%EA%B8%B0