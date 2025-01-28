package org.paz.community.vote.post.dto;

import lombok.Getter;
import org.paz.community.vote.comment.dto.ReadOneVotableCommentDto;
import org.paz.community.vote.post.entity.VotablePostEntity;

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

    public ReadOneVotablePostDto(VotablePostEntity votablePostEntity, List<ReadOneVotableCommentDto> comments) {
        this.postId = votablePostEntity.getId();
        this.postTitle = votablePostEntity.getTitle();
        this.postContent = votablePostEntity.getContent();
        this.postImagePath = votablePostEntity.getPostImagePath();
        this.tags = votablePostEntity.getTags();
        this.shutter = votablePostEntity.getShutter();
        this.iso = votablePostEntity.getIso();
        this.whitebalance = votablePostEntity.getWhitebalance();
        this.aperture = votablePostEntity.getAperture();
        this.likesCount = votablePostEntity.getLikesCount();
        this.hitsCount = votablePostEntity.getHitsCount();
        this.repliesCount = votablePostEntity.getRepliesCount();
        this.createdAt = votablePostEntity.getCreatedAt();
        this.updatedAt = votablePostEntity.getUpdatedAt();
        this.userId = votablePostEntity.getMemberEntity().getId();
        this.userNickname = votablePostEntity.getMemberEntity().getNickname();
        this.userImage = votablePostEntity.getMemberEntity().getProfileImagePath();
        this.comments = comments;
    }



}
