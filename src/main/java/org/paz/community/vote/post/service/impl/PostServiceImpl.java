package org.paz.community.vote.post.service.impl;

import lombok.RequiredArgsConstructor;
import org.paz.community.comment.dto.ReadOneCommentResponseDto;
import org.paz.community.vote.comment.entity.CommentEntity;
import org.paz.community.vote.comment.repository.CommentRepository;
import org.paz.community.member.entity.MemberEntity;
import org.paz.community.member.repository.MemberJpaRepository;
import org.paz.community.vote.post.entity.PostEntity;
import org.paz.community.security.SecurityContextUtil;
import org.paz.community.utils.TimeGetter;
import org.paz.community.vote.post.dto.*;
import org.paz.community.vote.post.repository.PostRepository;
import org.paz.community.vote.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private PostRepository postRepository;
    private MemberJpaRepository memberJpaRepository;
    private CommentRepository commentRepository;

    // 이미지 업로드를 위한 의존성 및 변수 선언
    TimeGetter time = new TimeGetter();
    private String uploadPath = "src/main/resources/static/images/post";
    String imagePath;

    /**
     * 게시글 작성
     * @param createVotablePostDto 게시글 정보를 담은 Dto
     * @param files 게시글 이미지 파일
     */
    @Override
    public void createVotablePost(CreateVotablePostDto createVotablePostDto, List<MultipartFile> files) {
        // 게시글 작성자 정보
        Long userId = SecurityContextUtil.getCurrentUserId();
        MemberEntity memberEntity = memberJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 생성: 멤버 엔티티 조회 오류"));
        // files가 null이 아니면 사진을 저장하고 경로 저장
        if(files != null){
            for(MultipartFile file : files){
                String originalName = file.getOriginalFilename();
                imagePath = "/images/post"+time.getFormattedCurrentTime()+originalName;
                Path savePath = Paths.get(uploadPath, time.getFormattedCurrentTime()+originalName);

                try {
                    Files.createDirectories((savePath.getParent()));
                    file.transferTo(savePath);
                } catch (IOException e) {
                    System.out.println(e + "이미지 저장을 위한 경로를 찾을 수 없음.");
                }
            }
        }
        // 빌더 패턴을 통해서 게시글 엔티티 생성
        // fix: 임시로 빌더패턴에 기본값 0 삼입(count 삼형제) - postService 전체 한번에 수정
        PostEntity postEntity = PostEntity.builder()
                .memberEntity(memberEntity)
                .title(createVotablePostDto.getTitle())
                .content(createVotablePostDto.getContent())
                .postImagePath(imagePath)
                .iso(createVotablePostDto.getIso())
                .shutter(createVotablePostDto.getShutter())
                .whitebalance(createVotablePostDto.getWhitebalance())
                .aperture(createVotablePostDto.getAperture())
                .likesCount(0)
                .hitsCount(0)
                .repliesCount(0)
                .tags(createVotablePostDto.getTags())
                .build();

        // 게시글 DB 저장
        postRepository.save(postEntity);

    }

    /**
     * 전제 게시글 조회 로직
     * @return List<ReadSummaryVotablePostDto> 전체 게시글 조회용 Dto
     */
    @Override
    public List<ReadSummaryVotablePostDto> readAllVotablePost() {
        // 게시글 엔티티에 전체 게시글 정보 조회
        List<PostEntity> postEntities = postRepository.findByDeletedAtIsNull();

        return postEntities.stream()
                .map(ReadSummaryVotablePostDto::new)
                .toList();
    }

    /**
     * 전체 게시글 페이징 조회 로직
     * @param pageable 페이징 처리를 위한 pageable 객체
     * @return ReadSummaryWithPagedVotablePostDto 전체 게시글 조회용 Dto
     */
    @Override
    public ReadSummaryWithPagedVotablePostDto readAllVotablePostWithPage(Pageable pageable) {
        Long count = postRepository.countByDeletedAtIsNull();
        Page<PostEntity> postEntities = postRepository.findByDeletedAtIsNull(pageable);
        List<ReadSummaryVotablePostDto> posts = postEntities.getContent().stream()
                .map(ReadSummaryVotablePostDto::new)
                .toList();

        return new ReadSummaryWithPagedVotablePostDto(count, posts);
    }

    /**
     * 단일 게시글 조회 로직
     * @param postId 게시글 id
     * @return ReadOneVotablePostDto 단일 게시글 조회용 Dto
     */
    @Override
    public ReadOneVotablePostDto readOneVotablePost(Long postId) {
        // 게시글 엔티티에 postId에 해당하는 게시글 조회
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("단일 게시글 조회 오류"));

        // 댓글 엔티티 리스트에 postId에 대한 모든 댓글 조회
        List<CommentEntity> commentEntities = commentRepository.findByPostEntityAndDeletedAtIsNull(postEntity);

        // 댓글 조회용 dto 리스트에 엔티티 기준으로 담은 데이터 할당
        List<ReadOneVotableCommentDto> comments = commentEntities.stream()
                .map(ReadOneVotableCommentDto::new)
                .toList();

        // 게시글 및 댓글 정보 반환(Dto)
        return new ReadOneVotablePostDto(postEntity, comments);
    }

    /**
     * 게시글 수정 로직
     * @param postId 게시글 id
     * @param modifyVotablePostDto 게시글 수정 정보를 담은 Dto
     */
    @Override
    public void modifyVotablePost(Long postId, ModifyVotablePostDto modifyVotablePostDto) {
        // 게시글 원본 데이터 조회
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 수정: 단일 게시글 조회 오류"));

        MemberEntity memberEntity = postEntity.getMemberEntity();
        Long savedId = memberEntity.getId();
        Long authenticatedId = SecurityContextUtil.getCurrentUserId();

        if(savedId == null || authenticatedId == null){
            // fix: 커스텀 예외
            System.out.println("인증상태 불량");
        }

        if(!Objects.equals(savedId, authenticatedId)){
            // fix: 커스텀 예외
            System.out.println("게시물 작성자 불일치");
        }

        // 게시글 제목 수정시 반영 (dirtyChecking)
        if(!Objects.equals(postEntity.getTitle(), modifyVotablePostDto.getTitle())){
            postEntity.modityTitle(modifyVotablePostDto.getTitle());
        }

        // 게시글 내용 수정시 반영
        if(!Objects.equals(postEntity.getContent(), modifyVotablePostDto.getContent())){
            postEntity.modifyContent(modifyVotablePostDto.getContent());
        }

        // 게시글 태그 수정시 반영
        if(!Objects.equals(postEntity.getTags(), modifyVotablePostDto.getTags())){
            postEntity.modifyTags(modifyVotablePostDto.getTags());
        }

        // 수정된 게시글 DB 반영
        postRepository.save(postEntity);
    }

    /**
     * 게시글 삭제 로직
     * @param postId
     */
    @Override
    public void deleteVotablePost(Long postId) {
        // 게시글 삭제를 위해 엔티티 조회(soft 제거)
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 삭제: 게시글 엔티티 조회 오류"));

        MemberEntity memberEntity = postEntity.getMemberEntity();
        Long savedId = memberEntity.getId();
        Long authenticatedId = SecurityContextUtil.getCurrentUserId();

        if(savedId == null || authenticatedId == null){
            // fix - custom exception
            System.out.println("인증상태 불량");
        }

        if(!Objects.equals(savedId, authenticatedId)){
            // fix - custom exception
            System.out.println("게시물 작성자 불일치");
        }

        // BaseEntity의 소프트 삭제 메서드 호출
        postEntity.softDelete();

        // 소프트 삭제 DB에 반영
        postRepository.save(postEntity);

    }
}
