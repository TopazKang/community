package org.paz.community.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.paz.community.member.domain.Member;
import org.paz.community.member.dto.MemberDto;
import org.paz.community.member.repository.CommonMemberRepository;
import org.paz.community.security.SecurityContextUtil;
import org.paz.community.utils.TimeGetter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {



    private final CommonMemberRepository commonMemberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


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
    public void createMember(MemberDto.SignUp dto, List<MultipartFile> files) {
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

        Member member = new Member(dto.getName(), dto.getNickname(), dto.getEmail(), bCryptPasswordEncoder.encode(dto.getPassword()), imagePath);
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

//    @Override // 로그인
//    public MemberDto.LoginReturn login(MemberDto.Login dto) {
//        String rawPassword = dto.getPassword();
//        Member member = Member.withEmail(dto.getEmail());
//        Member memberInfo = commonMemberRepository.login(member);
//        boolean isValid = passwordEncoder.matches(rawPassword, memberInfo.getPassword());
//
//        // 노아 코드 개선
//        if (!isValid) {
//            throw new RuntimeException("Invalid login");
//        }
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getEmail(), null, List.of(new SimpleGrantedAuthority("USER")));
//        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication, memberInfo.getId());
//        MemberDto.LoginReturn loginReturn = new MemberDto.LoginReturn(tokenDto.getGrantType(), tokenDto.getAccessToken(), tokenDto.getRefreshToken(), memberInfo.getProfile_image_path());
//        return loginReturn;
//    }

    @Override
    public MemberDto.Info readInfo(Long authenticatedId) {
        Member member = new Member(authenticatedId);
        Member res = commonMemberRepository.readInfo(member);
        MemberDto.Info result = new MemberDto.Info(res.getNickname(), res.getEmail(), res.getProfile_image_path());
        return result;
    }

    @Override
    public void modifyInfo(MemberDto.ModifyInfo dto, List<MultipartFile> files) {
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


        Long userId = SecurityContextUtil.getCurrentUserId();
        Member member = Member.withModifyInfo(userId, dto.getNickname(), imagePath);
        commonMemberRepository.modifyInfo(member);
    }

    @Override
    public void changePassword(MemberDto.ChangePassword dto) {
        Long userId = SecurityContextUtil.getCurrentUserId();
        Member member = new Member(userId, bCryptPasswordEncoder.encode(dto.getPassword()));
        commonMemberRepository.changePassword(member);

    }

    @Override
    public void deleteAccount() {
        Long userId = SecurityContextUtil.getCurrentUserId();
        Member member = new Member(userId);
        commonMemberRepository.deleteMember(member);
    }
}

