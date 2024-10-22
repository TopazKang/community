package org.paz.community.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.paz.community.member.domain.Member;
import org.paz.community.member.dto.MemberDto;
import org.paz.community.member.service.MemberServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
public class MemberController {
    private final MemberServiceImpl commonMemberService;

    public MemberController(MemberServiceImpl commonMemberService) {
        this.commonMemberService = commonMemberService;
    }

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}) // 회원가입
    @Operation(summary = "회원가입")
    public String Signin(@RequestPart(value="request") MemberDto.SignIn dto, @RequestPart(value="file", required = false) List<MultipartFile> files){
        commonMemberService.createMember(dto, files);
        return "success";
    }

    @GetMapping("/email") // 이메일 중복 확인
    @Operation(summary = "이메일 중복 확인")
    public String EmailChk(@RequestParam(value="email") MemberDto.EmailChk dto){
        Member member = Member.withEmail(dto.getEmail()); // 정적 팩토리 메서드 사용
        boolean result = commonMemberService.emailChk(member);
        if(result){
            return "email_already_used";
        }
        else{
            return "available_email";
        }
    }

    @GetMapping("/nickname") // 닉네임 중복 확인
    @Operation(summary = "닉네임 중복 확인")
    public String NicknameChk(@RequestParam(value="nickname") MemberDto.NicknameInput dto){
        Member member = Member.withNickname(dto.getNickname()); // 정적 팩토리 메서드 사용
        boolean result = commonMemberService.nicknameChk(member);
        if(result){
            return "nickname_already_used";
        }
        else{
            return "available_nickname";
        }
    }

    @PostMapping("/login") // 로그인
    @Operation(summary = "로그인")
    public MemberDto.LoginReturn Login(@RequestBody MemberDto.Login dto){
        MemberDto.LoginReturn result = commonMemberService.login(dto);
        return result;
    }
    // @PostMapping("/logout") // 로그아웃

    @GetMapping("/") // 회원 정보 조회(수정을 위한)
    @Operation(summary="회원 정보 조회")
    public MemberDto.Info ReadInfo(@RequestHeader("Authorization") String token){
        MemberDto.Info result = commonMemberService.readInfo(token.substring(7));
        return result;
    }
    @PatchMapping(value="/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}) // 회원 정보 수정
    @Operation(summary="회원 정보 수정")
    public String ModifyInfo(@RequestHeader("Authorization") String token,
                             @RequestPart(value="request") MemberDto.ModifyInfo dto,
                             @RequestPart(value="file", required = false) List<MultipartFile> files){
        commonMemberService.modifyInfo(token.substring(7), dto, files);
        return "success";
    }
    @PutMapping("/") // 비밀번호 변경
    @Operation(summary = "비밀번호 변경")
    public String ChangePassword(@RequestHeader("Authorization") String token,
                                 @RequestBody MemberDto.ChangePassword dto){
        commonMemberService.changePassword(token.substring(7), dto);
        return "success";
    }
    @DeleteMapping("/") // 회원 탈퇴
    @Operation(summary="회원 탈퇴")
    public String DeleteAccount(@RequestHeader("Authorization") String token){
        commonMemberService.deleteAccount(token.substring(7));
        return "success";
    }
}

// https://velog.io/@songs4805/Spring-Controller%EC%97%90%EC%84%9C-MultipartFile-Dto%EB%A5%BC-%ED%95%A8%EA%BB%98-%EC%9A%94%EC%B2%AD%ED%95%98%EA%B8%B0
