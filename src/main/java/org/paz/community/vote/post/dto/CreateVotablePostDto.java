package org.paz.community.vote.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateVotablePostDto {

    private String title;
    private String content;
    private String tags;
    private String shutter;
    private String iso;
    private String whitebalance;
    private String aperture;
}
