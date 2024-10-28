package org.paz.community.member.repository;

import org.paz.community.member.domain.Member;

public interface MemberRepository {

    // 회원가입
    void createUser(Member member);

    // 이메일 중복 확인
    int emailChk(Member member);
    // 닉네임 중복확인
    int nicknameChk(Member member);
    // 로그인 - 아이디와 비밀번호 조회
    Member login(Member member);
    // find by id 회원 정보 조회 - 이메일 닉네임
    Member readInfo(Member member);
    // 회원정보 수정
    void modifyInfo(Member member);
    // 비밀번호 변경
    void changePassword(Member member);
    // 회원 탈퇴
    void deleteMember(Member member);
}
