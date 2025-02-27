package org.paz.community.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

public class MemberDto {

    @Getter
    public static class SignUp {
        private String name;
        private String nickname;
        private String email;
        private String password;

        public SignUp(String name, String nickname, String email, String password) {
            this.name = name;
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
    public static class Info {
        private String name;
        private String nickname;
        private String email;
        private String profile_image_path;

        public Info(String name, String nickname, String email, String profileImagePath){
            this.name = name;
            this.nickname = nickname;
            this.email = email;
            this.profile_image_path = profileImagePath;
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