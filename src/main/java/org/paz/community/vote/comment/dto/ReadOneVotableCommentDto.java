package org.paz.community.vote.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.paz.community.vote.comment.entity.VotableCommentEntity;

import java.time.LocalDateTime;

@Getter
public class ReadOneVotableCommentDto {
    private Long commentId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String userNickname;
    private String userProfileImagePath;

    public ReadOneVotableCommentDto(VotableCommentEntity commentEntity){
        this.commentId = commentEntity.getId();
        this.comment = commentEntity.getComment();
        this.createdAt = commentEntity.getCreatedAt();
        this.updatedAt = commentEntity.getUpdatedAt();
        this.userId = commentEntity.getMemberEntity().getId();
        this.userNickname = commentEntity.getMemberEntity().getNickname();
        this.userProfileImagePath = commentEntity.getMemberEntity().getProfileImagePath();
    }
}
