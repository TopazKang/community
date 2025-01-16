package org.paz.community.vote.post.dto;

import lombok.Getter;
import org.paz.community.vote.post.entity.PostEntity;

@Getter
public class ReadSummaryVotablePostDto {
    private Long postId;
    private String postImagePath;
    private int likesCount;

    public ReadSummaryVotablePostDto(PostEntity postEntity){
        this.postId = postEntity.getId();
        this.postImagePath = postEntity.getPostImagePath();
        this.likesCount = postEntity.getLikesCount();
    }
}
