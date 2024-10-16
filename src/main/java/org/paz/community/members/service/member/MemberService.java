package org.paz.community.members.service.member;

import org.paz.community.members.domain.Member;
import org.paz.community.members.dto.MemberDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberService{

    // 회원가입
    void createMember(MemberDto.SignIn dto, List<MultipartFile> file);

    // 이메일 중복 확인\
    boolean emailChk(Member member);
    // 닉네임 중복확인
    boolean nicknameChk(Member member);
    // 로그인
    MemberDto.LoginReturn login(MemberDto.Login dto);
    // 로그아웃

    // find by id
    MemberDto.Info readInfo(String token);
    // 회원정보 수정
    void modifyInfo(String token, MemberDto.ModifyInfo dto, List<MultipartFile> file);
    // 비밀번호 변경
    void changePassword(String token, MemberDto.ChangePassword dto);
    // 회원 탈퇴
    void deleteAccount(String token);
}