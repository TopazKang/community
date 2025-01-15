package org.paz.community.vote.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ReadSummaryWithPagedVotablePostDto {
    private Long count;
    private List<ReadSummaryVotablePostDto> posts;

    public ReadSummaryWithPagedVotablePostDto(Long count, List<ReadSummaryVotablePostDto> posts){
        this.count = count;
        this.posts = posts;
    }
}
