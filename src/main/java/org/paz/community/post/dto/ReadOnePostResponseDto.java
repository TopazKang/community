package org.paz.community.post.dto;

import lombok.Getter;
import org.paz.community.comment.dto.ReadOneCommentResponseDto;
import org.paz.community.post.entity.PostEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReadOnePostResponseDto {
    private Long postId;
    private String postTitle;
    private String postImage;
    private String category;
    private String tags;
    private String postContent;
    private LocalDateTime postCreatedAt;
    private LocalDateTime postUpdatedAt;
    private int likesCount;
    private int hitsCount;
    private int repliesCount;
    private Long userId;
    private String userNickname;
    private String userImage;
    private Boolean isOwner;
    private List<ReadOneCommentResponseDto> comments;

    public ReadOnePostResponseDto(PostEntity postEntity, Boolean isOwner, List<ReadOneCommentResponseDto> comments) {
        this.postId = postEntity.getId();
        this.postTitle = postEntity.getTitle();
        this.postImage = postEntity.getPostImagePath();
        this.category = postEntity.getCategory();
        this.tags = postEntity.getTags();
        this.postContent = postEntity.getContent();
        this.postCreatedAt = postEntity.getCreatedAt();
        this.postUpdatedAt = postEntity.getUpdatedAt();
        this.likesCount = postEntity.getLikesCount();
        this.hitsCount = postEntity.getHitsCount();
        this.repliesCount = postEntity.getRepliesCount();
        this.userId = postEntity.getMemberEntity().getId();
        this.userNickname = postEntity.getMemberEntity().getNickname();
        this.userImage = postEntity.getMemberEntity().getProfileImagePath();
        this.isOwner = isOwner;
        this.comments = comments;
    }
}
