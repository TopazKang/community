package org.paz.community.vote.post.dto;

import lombok.Getter;
import org.paz.community.vote.post.entity.VotablePostEntity;

@Getter
public class ReadSummaryVotablePostDto {
    private Long postId;
    private String postImagePath;
    private int likesCount;

    public ReadSummaryVotablePostDto(VotablePostEntity votablePostEntity){
        this.postId = votablePostEntity.getId();
        this.postImagePath = votablePostEntity.getPostImagePath();
        this.likesCount = votablePostEntity.getLikesCount();
    }
}
