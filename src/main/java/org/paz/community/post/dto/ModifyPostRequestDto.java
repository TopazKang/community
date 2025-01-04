package org.paz.community.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ModifyPostRequestDto {
    private String title;
    private String content;
    private String category;
    private String tags;
}