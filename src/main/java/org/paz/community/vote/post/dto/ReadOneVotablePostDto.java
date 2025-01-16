package org.paz.community.vote.post.dto;

import lombok.Getter;
import org.paz.community.vote.comment.dto.ReadOneVotableCommentDto;
import org.paz.community.vote.post.entity.PostEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReadOneVotablePostDto {

    private Long postId;
    private String postTitle;
    private String postContent;
    private String postImagePath;
    private String tags;
    private String shutter;
    private String iso;
    private String whitebalance;
    private String aperture;
    private int likesCount;
    private int hitsCount;
    private int repliesCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String userNickname;
    private String userImage;
    private List<ReadOneVotableCommentDto> comments;

    public ReadOneVotablePostDto(PostEntity postEntity, List<ReadOneVotableCommentDto> comments) {
        this.postId = postEntity.getId();
        this.postTitle = postEntity.getTitle();
        this.postContent = postEntity.getContent();
        this.postImagePath = postEntity.getPostImagePath();
        this.tags = postEntity.getTags();
        this.shutter = postEntity.getShutter();
        this.iso = postEntity.getIso();
        this.whitebalance = postEntity.getWhitebalance();
        this.aperture = postEntity.getAperture();
        this.likesCount = postEntity.getLikesCount();
        this.hitsCount = postEntity.getHitsCount();
        this.repliesCount = postEntity.getRepliesCount();
        this.createdAt = postEntity.getCreatedAt();
        this.updatedAt = postEntity.getUpdatedAt();
        this.userId = postEntity.getMemberEntity().getId();
        this.userNickname = postEntity.getMemberEntity().getNickname();
        this.userImage = postEntity.getMemberEntity().getProfileImagePath();
        this.comments = comments;
    }



}
