package org.paz.community.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.paz.community.comment.dto.ReadOneCommentResponseDto;
import org.paz.community.comment.entity.CommentEntity;
import org.paz.community.comment.repository.CommentRepository;
import org.paz.community.comment.service.CommentService;
import org.paz.community.member.entity.MemberEntity;
import org.paz.community.member.repository.MemberJpaRepository;
import org.paz.community.post.entity.PostEntity;
import org.paz.community.post.repository.PostRepository;
import org.paz.community.utils.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final PostRepository postRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 댓글 작성 로직
     * @param token 댓글 작성자 정보를 위한 token
     * @param postId 댓글의 원본 게시글 정보를 위한 postId
     * @param comment 댓글 내용
     */
    @Override
    public void createComment(String token, Long postId, String comment) {
        // 게시글 작성자 정보
        Long userId = (long) jwtTokenProvider.extractId(token);
        MemberEntity memberEntity = memberJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성: 멤버 엔티티 조회 오류"));

        // 댓글 원본 게시글 정보
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성: 원본 게시글 엔티티 조회 오류"));

        // 빌더 패턴을 통해 댓글 엔티티 생성
        CommentEntity commentEntity = CommentEntity.builder()
                .comment(comment)
                .memberEntity(memberEntity)
                .postEntity(postEntity)
                .build();

        // 댓글 DB 저장
        commentRepository.save(commentEntity);
    }

    /**
     * 댓글 조회 로직
     * @param commentId 댓글 id
     * @return ReadOneCommentResponseDto 단일 댓글 조회용 Dto
     */
    @Override
    public ReadOneCommentResponseDto readOneComment(Long commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("단일 댓글 조회 오류"));

        return new ReadOneCommentResponseDto(commentEntity);
    }

    /**
     * 댓글 수정 로직
     * @param commentId 댓글 id
     */
    @Override
    public void modifyComment(Long commentId, String comment) {
        // 댓글 수정을 위한 원본 엔티티 조회
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정: 댓글 엔티티 조회 오류"));

        // 원본 댓글과 수정 댓글이 일치하지 않으면 엔티티 수정
        if(!Objects.equals(comment, commentEntity.getComment())){
            commentEntity.modifyComment(comment);
        }

        // 수정된 엔티티 DB에 저장
        commentRepository.save(commentEntity);
    }

    /**
     * 댓글 삭제 로직
     * @param commentId 댓글 id
     */
    @Override
    public void deleteComment(Long commentId) {
        // 댓글 삭제를 위해 원본 엔티티 조회(soft 삭제)
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제: 댓글 엔티티 조회 오류"));

        // Base엔티티의 소프트 삭제 메서드 호출
        commentEntity.softDelete();

        // 소프트 삭제가 완료된 엔티티 DB에 반영
        commentRepository.save(commentEntity);
    }
}
