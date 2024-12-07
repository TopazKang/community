package org.paz.community.post.service;

import org.paz.community.post.dto.ModifyPostRequestDto;
import org.springframework.web.multipart.MultipartFile;
import org.paz.community.post.dto.CreatePostRequestDto;
import org.paz.community.post.dto.ReadOnePostResponseDto;
import org.paz.community.post.dto.ReadSummaryPostResponseDto;

import java.util.List;

public interface PostService {
    // 게시글 작성
    void createPost(CreatePostRequestDto data, List<MultipartFile> files);
    // 전체 게시글 조회
    List<ReadSummaryPostResponseDto> readAllPost();
    // 단일 게시글 조회
    ReadOnePostResponseDto readOnePost(Long postId);
    // 게시글 수정
    void modifyPost(Long postId, ModifyPostRequestDto data, List<MultipartFile> files);
    // 게시글 삭제
    void deletePost(Long postId);
}
