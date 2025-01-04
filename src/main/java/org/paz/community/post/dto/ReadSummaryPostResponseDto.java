package org.paz.community.post.dto;

import lombok.Getter;
import org.paz.community.post.entity.PostEntity;

import java.time.LocalDateTime;

@Getter
public class ReadSummaryPostResponseDto {
    private Long postId;
    private String postTitle;
    private String postImage;
    private LocalDateTime postCreatedAt;
    private int likesCount;
    private int hitsCount;
    private int repliesCount;
    private Long userId;
    private String userNickname;
    private String userImage;
    private String category;
    private String tags;


    public ReadSummaryPostResponseDto(PostEntity postEntity) {
        this.postId = postEntity.getId();
        this.postTitle = postEntity.getTitle();
        this.postImage = postEntity.getPostImagePath();
        this.postCreatedAt = postEntity.getCreatedAt();
        this.likesCount = postEntity.getLikesCount();
        this.hitsCount = postEntity.getHitsCount();
        this.repliesCount = postEntity.getRepliesCount();
        this.userId = postEntity.getMemberEntity().getId();
        this.userNickname = postEntity.getMemberEntity().getNickname();
        this.userImage = postEntity.getMemberEntity().getProfileImagePath();
        this.category = postEntity.getCategory();
        this.tags = postEntity.getTags();
    }
}
