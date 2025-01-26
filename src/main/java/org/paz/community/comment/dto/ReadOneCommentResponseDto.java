package org.paz.community.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.paz.community.comment.entity.CommentEntity;

import java.time.LocalDateTime;

@Getter
public class ReadOneCommentResponseDto {
    private Long commentId;
    private String comment;
    private LocalDateTime commentCreatedAt;
    private LocalDateTime commentUpdatedAt;
    private Long userId;
    private String userNickname;
    private String userImage;
    private Boolean isOwner;

    public ReadOneCommentResponseDto(CommentEntity commentEntity, Boolean isOwner){
        this.commentId = commentEntity.getId();
        this.comment = commentEntity.getComment();
        this.commentCreatedAt = commentEntity.getCreatedAt();
        this.commentUpdatedAt = commentEntity.getUpdatedAt();
        this.userId = commentEntity.getMemberEntity().getId();
        this.userNickname = commentEntity.getMemberEntity().getNickname();
        this.userImage = commentEntity.getMemberEntity().getProfileImagePath();
        this.isOwner = isOwner;
    }
}
