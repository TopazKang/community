package org.paz.community.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ReadSummaryWithPagedPostDto {
    private Long count;
    private List<ReadSummaryPostResponseDto> posts;

    public ReadSummaryWithPagedPostDto(Long count, List<ReadSummaryPostResponseDto> posts){
        this.count = count;
        this.posts = posts;
    }
}
