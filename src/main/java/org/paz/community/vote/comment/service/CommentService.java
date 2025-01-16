package org.paz.community.vote.comment.service;

import org.paz.community.vote.comment.dto.ReadOneVotableCommentDto;

public interface CommentService {
    // 투표 댓글 생성
    void createVotableComment(Long postId, String comment);
    // 댓글 조회
    ReadOneVotableCommentDto readOneVotableComment(Long commentId);
    // 댓글 수정
    void modifyVotableComment(Long commentId, String comment);
    // 댓글 삭제
    void deleteVotableComment(Long commentId);
}
