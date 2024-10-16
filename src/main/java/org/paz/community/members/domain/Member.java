package org.paz.community.members.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Member {// JPA의 객체간의 구분을 위함
    private Integer id;
    private String nickname;
    private String email;
    private String password;
    private String profile_image_path;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    // 기본 생성자
    public Member(Integer id, String nickname, String email, String password, String profile_image_path, String created_at, String updated_at, String deleted_at){
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profile_image_path = profile_image_path;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    // 회원가입을 위한 생성자
    public Member(String nickname, String email, String password, String profile_image_path){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profile_image_path = profile_image_path;
    }
    public Member(int id, String nickname, String password, String profile_image_path){
        // 로그인 반환 & 회원정보 수정 입력의 정적 팩토리 메서드 생성을 위함.
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.profile_image_path = profile_image_path;
    }
    // 이메일 중복확인을 위한 생성자
    public static Member withEmail(String email) {
        return new Member(null, email, null, null);
    }
    // 닉네임 중복확인을 위한 생성자
    public static Member withNickname(String nickname){
        return new Member(nickname, null, null, null);
    }
    public static Member withLoginReturn(int id, String password, String profile_image_path){
        // 로그인 이후 반환값들
        return new Member(id, null, password, profile_image_path);
    }
    // 아이디로 회원정보 조회
    public Member(int id){ // 입력값에 id가 들어가는 요청(회원정보 조회 수정 삭제 etc)
        this.id = id;
    }
    public Member(String nickname, String email){ //회원 정보 출력
        this.nickname = nickname;
        this.email = email;
    }
    public static Member withModifyInfo(int id, String nickname, String profile_image_path){
        // 회원정보 수정시 입력 도메인
        return new Member(id, nickname, null, profile_image_path);
    }
    public Member(int id, String password){
        // 비밀번호 변경 입력 도메인
        this.id = id;
        this.password = password;
    }

}

// https://nyyang.tistory.com/125

//@table @generated value//db에서 들어오는거에 달아줘야 하는 거   @Column @join/onetoMany같은 연관관계 관련
// 엔티티 먼저 그다음 연관관계 순으로