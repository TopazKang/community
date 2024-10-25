package org.paz.community.post.service.impl;

import lombok.RequiredArgsConstructor;
import org.paz.community.comment.dto.ReadOneCommentResponseDto;
import org.paz.community.comment.entity.CommentEntity;
import org.paz.community.comment.repository.CommentRepository;
import org.paz.community.member.entity.MemberEntity;
import org.paz.community.member.repository.MemberJpaRepository;
import org.paz.community.post.dto.CreatePostRequestDto;
import org.paz.community.post.dto.ModifyPostRequestDto;
import org.paz.community.post.dto.ReadOnePostResponseDto;
import org.paz.community.post.dto.ReadSummaryPostResponseDto;
import org.paz.community.post.entity.PostEntity;
import org.paz.community.post.repository.PostRepository;
import org.paz.community.post.service.PostService;
import org.paz.community.utils.JwtTokenProvider;
import org.paz.community.utils.TimeGetter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final CommentRepository commentRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 사진 업로드를 위한 의존성 및 변수 선언
    TimeGetter time = new TimeGetter();
    private String uploadPath = "src/main/resources/static/images/profile";
    String imagePath;

    /**
     * 게시글 작성 로직
     * @param token 작성자 정보를 담기 위한 토큰
     * @param files 게시글의 사진파일
     * @param data 게시글 정보를 담은 Dto
     */
    @Override
    public void createPost(String token, CreatePostRequestDto data, List<MultipartFile> files) {
        // 게시글 작성자 정보
        Long userId = (long) jwtTokenProvider.extractId(token);
        MemberEntity memberEntity = memberJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 생성: 멤버 엔티티 조회 오류"));

        // files가 null이 아니면 사진을 저장하고 경로 저장
        if (files != null) {
            for(MultipartFile file : files) {
                String originalName = file.getOriginalFilename();
                imagePath = "/images/post/" + time.getFormattedCurrentTime() + originalName;
                Path savePath = Paths.get(uploadPath, time.getFormattedCurrentTime() + originalName);

                try {
                    Files.createDirectories((savePath.getParent()));
                    file.transferTo(savePath);
                } catch (IOException e) {
                    System.out.println(e + "이미지 저장을 위한 경로를 찾을 수 없음.");
                }
            }
        } else {
            imagePath = null;
        }

        // 빌더 패턴을 통해서 게시글 엔티티 생성
        PostEntity postEntity = PostEntity.builder()
                .memberEntity(memberEntity)
                .title(data.getTitle())
                .content(data.getContent())
                .postImagePath(imagePath)
                .build();

        // 게시글 DB 저장
        postRepository.save(postEntity);
    }

    /**
     * 전체 게시글 조회 로직
     * @return List<ReadSummaryPostResponseDto> 전체 게시글 조회용 Dto
     */
    @Override
    public List<ReadSummaryPostResponseDto> readAllPost() {
        // 게시글 엔티티에 전체 게시글 정보 조회
        List<PostEntity> postEntities = postRepository.findAll();

        // 엔티티 list를 토대로 게시글 dto 리스트를 반환
        return postEntities.stream()
                .map(ReadSummaryPostResponseDto::new)
                .toList();
    }

    /**
     * 단일 게시글 조회 로직
     * @param postId 게시글 id
     * @return ReadOnePostResponseDto 단일 게시글 조회용 Dto
      */
    @Override
    public ReadOnePostResponseDto readOnePost(Long postId) {
        // 게시글 엔티티에 postId에 맞는 게시글 정보 조회
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("단일 게시글 조회 오류"));

        // 댓글 엔티티 리스트에 postId에 대한 모든 댓글 조회
        List<CommentEntity> commentEntities = commentRepository.findByPostId(postId);

        // 댓글 조회용 dto 리스트에 엔티티 기준으로 담은 데이터 할당
        List<ReadOneCommentResponseDto> comments = commentEntities.stream()
                .map(ReadOneCommentResponseDto::new)
                .toList();

        // 게시글 및 댓글 정보 반환(Dto)
        return new ReadOnePostResponseDto(postEntity, comments);
    }

    /**
     * 게시글 수정 로직
     * @param postId 게시글 id
     * @param data 게시글 수정 정보를 담은 Dto
     * @param files 게시글 수정: 이미지 파일
     */
    @Override
    public void modifyPost(Long postId, ModifyPostRequestDto data, List<MultipartFile> files) {
        // 게시글 원본 데이터 조회
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 수정: 단일 게시글 조회 오류"));

        // files가 null이 아니면 사진을 저장하고 경로 저장
        if (files != null) {
            for(MultipartFile file : files) {
                String originalName = file.getOriginalFilename();
                imagePath = "/images/post/" + time.getFormattedCurrentTime() + originalName;
                Path savePath = Paths.get(uploadPath, time.getFormattedCurrentTime() + originalName);

                try {
                    Files.createDirectories((savePath.getParent()));
                    file.transferTo(savePath);
                } catch (IOException e) {
                    System.out.println(e + "이미지 저장을 위한 경로를 찾을 수 없음.");
                }
            }

            // 원본과 새로 저장된 사진의 경로값이 다르면 새로운 경로로 엔티티 값 변경
            if(!Objects.equals(postEntity.getPostImagePath(), imagePath)){
                postEntity.modifyPostImagePath(imagePath);
            }
        }

        // 원본과 새로운 제목이 차이가 있으면 새로운 제목으로 엔티티 값 변경
        if (!Objects.equals(data.getTitle(), postEntity.getTitle())) {
            postEntity.modityTitle(data.getTitle());
        }

        // 원본과 새로운 내용이 차이가 있으면 새로운 내용으로 엔티티 값 변경
        if (!Objects.equals(data.getContent(), postEntity.getContent())) {
            postEntity.modifyContent(data.getContent());
        }

        // 수정 게시글 DB 저장
        postRepository.save(postEntity);
    }


    /**
     * 게시글 삭제 로직
     * @param postId 게시글 id
     */
    @Override
    public void deletePost(Long postId) {
        // 게시글 삭제를 위해서 엔티티 조회(soft 삭제)
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 삭제: 게시글 엔티티 조회 오류"));

        // Base엔티티의 소프트 삭제 메서드를 호출
        postEntity.softDelete();

        // 소프트 삭제를 진행한 엔티티 DB에 반영
        postRepository.save(postEntity);
    }
}
