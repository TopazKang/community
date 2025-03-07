package org.paz.community.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostRequestDto {
    private String title;
    private String content;
    private String category;
    private String tags;
    private String postImagePath;
}
