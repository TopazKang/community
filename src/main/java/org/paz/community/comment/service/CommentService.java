package org.paz.community.comment.service;

import org.paz.community.comment.dto.ReadOneCommentResponseDto;

public interface CommentService {
    // 댓글 작성
    void createComment(String token, Long postId, String comment);

    // 단일 댓글 조회
    ReadOneCommentResponseDto readOneComment(Long commentId);

    //댓글 수정
    void modifyComment(Long commentId, String comment);

    void deleteComment(Long commentId);
}