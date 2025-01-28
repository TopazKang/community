package org.paz.community.vote.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyVotablePostDto {
    private String title;
    private String content;
    private String tags;
}
