package org.paz.community.member.service;

import lombok.extern.slf4j.Slf4j;
import org.paz.community.member.domain.Member;
import org.paz.community.member.dto.MemberDto;
import org.paz.community.dto.TokenDto;
import org.paz.community.member.repository.CommonMemberRepository;
import org.paz.community.utils.JwtTokenProvider;
import org.paz.community.utils.TimeGetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 일반유저가 ~~~
 */
@Slf4j
@Service
@Transactional
public class MemberServiceImpl implements MemberService {



    private final CommonMemberRepository commonMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MemberServiceImpl(CommonMemberRepository commonMemberRepository, PasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
        this.commonMemberRepository = commonMemberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 사진 업로드를 위한 의존성 및 변수 선언
    TimeGetter time = new TimeGetter();
    private String uploadPath = "src/main/resources/static/images/profile";
    String imagePath;


    /**
     *
     *
     * @param dto   로그인 정보
     * @param files 프로필이
     */
    @Override // 회원가입
    public void createMember(MemberDto.SignIn dto, List<MultipartFile> files) {
        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            imagePath = "/images/profile/" + time.getFormattedCurrentTime() + originalName;
            Path savePath = Paths.get(uploadPath, time.getFormattedCurrentTime() + originalName);

            try {
                Files.createDirectories((savePath.getParent()));
                file.transferTo(savePath);
            } catch (IOException e) {
                // noah: 1점
                System.out.println(e);
                log.info("여기 에러가 비ㅏ용");
            }
        }

        Member member = new Member(dto.getNickname(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()), imagePath);
        commonMemberRepository.createUser(member);
    }

    @Override // 이메일 중복 확인
    public boolean emailChk(Member member) {
        Integer result = commonMemberRepository.emailChk(member);
        return result > 0;
    }

    /**
     * 유저의 닉네임 중복 체크
     * @param member 멤버 정보
     * @return 닉네임 중복여부
     */
    @Transactional(readOnly = true)
    @Override // 닉네임 중복 확인
    public boolean nicknameChk(Member member) {
        Integer result = commonMemberRepository.nicknameChk(member);
        return result > 0;
    }

    @Override // 로그인
    public MemberDto.LoginReturn login(MemberDto.Login dto) {
        String rawPassword = dto.getPassword();
        Member member = Member.withEmail(dto.getEmail());
        Member memberInfo = commonMemberRepository.login(member);
        boolean isValid = passwordEncoder.matches(rawPassword, memberInfo.getPassword());

        // 노아 코드 개선
        if (!isValid) {
            throw new RuntimeException("Invalid login");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getEmail(), null, List.of(new SimpleGrantedAuthority("USER")));
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication, memberInfo.getId());
        MemberDto.LoginReturn loginReturn = new MemberDto.LoginReturn(tokenDto.getGrantType(), tokenDto.getAccessToken(), tokenDto.getRefreshToken(), memberInfo.getProfile_image_path());
        return loginReturn;
    }

    @Override
    public MemberDto.Info readInfo(String token) {
        Member member = new Member(jwtTokenProvider.extractId(token));
        Member res = commonMemberRepository.readInfo(member);
        MemberDto.Info result = new MemberDto.Info(res.getNickname(), res.getEmail());
        return result;
    }

    @Override
    public void modifyInfo(String token, MemberDto.ModifyInfo dto, List<MultipartFile> files) {
        if (files != null) {
            for (MultipartFile file : files) {
                String originalName = file.getOriginalFilename();
                imagePath = "/images/profile/" + time.getFormattedCurrentTime() + originalName;
                Path savePath = Paths.get(uploadPath, time.getFormattedCurrentTime() + originalName);

                try {
                    Files.createDirectories((savePath.getParent()));
                    file.transferTo(savePath);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } else {
            imagePath = null;
        }


        int userId = jwtTokenProvider.extractId(token);
        Member member = Member.withModifyInfo(userId, dto.getNickname(), imagePath);
        commonMemberRepository.modifyInfo(member);
    }

    @Override
    public void changePassword(String token, MemberDto.ChangePassword dto) {
        int userId = jwtTokenProvider.extractId(token);
        Member member = new Member(userId, passwordEncoder.encode(dto.getPassword()));
        commonMemberRepository.changePassword(member);

    }

    @Override
    public void deleteAccount(String token) {
        int userId = jwtTokenProvider.extractId(token);
        Member member = new Member(userId);
        commonMemberRepository.deleteMember(member);
    }
}

