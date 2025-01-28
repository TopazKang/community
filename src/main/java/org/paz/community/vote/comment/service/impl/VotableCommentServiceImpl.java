package org.paz.community.vote.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.paz.community.member.entity.MemberEntity;
import org.paz.community.member.repository.MemberJpaRepository;
import org.paz.community.security.SecurityContextUtil;
import org.paz.community.vote.comment.dto.ReadOneVotableCommentDto;
import org.paz.community.vote.comment.entity.VotableCommentEntity;
import org.paz.community.vote.comment.repository.VotableCommentRepository;
import org.paz.community.vote.comment.service.VotableCommentService;
import org.paz.community.vote.post.entity.VotablePostEntity;
import org.paz.community.vote.post.repository.VotablePostRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VotableCommentServiceImpl implements VotableCommentService {

    private final VotableCommentRepository votableCommentRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final VotablePostRepository votablePostRepository;

    /**
     * 투표 게시판 댓글 작성 로직
     * @param postId 댓글 작성을 위한 postId
     * @param comment 댓글 작성을 위한 댓글 내용
     */
    @Override
    public void createVotableComment(Long postId, String comment) {
        // 게시글 작성자 정보
        Long userId = SecurityContextUtil.getCurrentUserId();
        MemberEntity memberEntity = memberJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 작성: 엠버 엔티티 조회 오류"));
        // 댓글이 소속된 post 정보
        VotablePostEntity votablePostEntity = votablePostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 작성: 원본 게시글 조회 오류"));
        // 댓글 빌더
        VotableCommentEntity commentEntity = VotableCommentEntity.builder()
                .comment(comment)
                .memberEntity(memberEntity)
                .votablePostEntity(votablePostEntity)
                .build();
        // 댓글 DB 반영
        votableCommentRepository.save(commentEntity);
    }

    /**
     * 댓글 조회 로직
     * @param commentId 조회할 댓글의 id
     * @return ReadOneVotableCommentDto 단일 댓글 조회용 Dto
     */
    @Override
    public ReadOneVotableCommentDto readOneVotableComment(Long commentId) {

        VotableCommentEntity commentEntity = votableCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("단일 댓글 조회 오류(vote)"));

        return new ReadOneVotableCommentDto(commentEntity);
    }

    /**
     * 댓글 수정 로직
     * @param commentId 수정할 댓글 id
     * @param comment 수정할 댓글 내용
     */
    @Override
    public void modifyVotableComment(Long commentId, String comment) {
        VotableCommentEntity commentEntity = votableCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정: 댓글 엔티티 조회 오류(vote)"));

        MemberEntity memberEntity = commentEntity.getMemberEntity();
        Long savedId = memberEntity.getId();
        Long authenticatedId = SecurityContextUtil.getCurrentUserId();

        if(savedId == null || authenticatedId == null) {
            // fix - custom Exception
            System.out.println("인증 상태 불량");
        }
        if(!Objects.equals(savedId, authenticatedId)) {
            // fix - custom Exception
            System.out.println("댓글 작성자 불일치");
        }

        if(!Objects.equals(comment, commentEntity.getComment())) {
            commentEntity.modifyComment(comment);
        }

        votableCommentRepository.save(commentEntity);
    }

    /**
     * 댓글 삭제 로직(vote)
     * @param commentId 삭제할 댓글 id
     */
    @Override
    public void deleteVotableComment(Long commentId) {
        // 댓글 삭제를 위한 원본 조회(soft delete)
        VotableCommentEntity commentEntity = votableCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제: 원본 댓글 조회 오류(vote)"));

        MemberEntity memberEntity = commentEntity.getMemberEntity();
        Long savedId = memberEntity.getId();
        Long authenticatedId = SecurityContextUtil.getCurrentUserId();

        if(savedId == null || authenticatedId == null) {
            // fix - custom Exception
            System.out.println("인증 상태 불량");
        }
        if(!Objects.equals(savedId, authenticatedId)) {
            // fix - custom Exception
            System.out.println("댓글 작성자 불일치");
        }

        commentEntity.softDelete();

        votableCommentRepository.save(commentEntity);
    }
}
