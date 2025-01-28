package org.paz.community.post.service;

import org.paz.community.member.userDetails.CustomUserDetails;
import org.paz.community.post.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    // 게시글 작성
    void createPost(CreatePostRequestDto data);
    // 전체 게시글 조회
    List<ReadSummaryPostResponseDto> readAllPost();
    // 전체 게시글 페이징 조회
    ReadSummaryWithPagedPostDto readAllPostWithPage(Pageable pageable);
    // 단일 게시글 조회
    ReadOnePostResponseDto readOnePost(Long postId, CustomUserDetails userDetails);
    // 게시글 수정
    void modifyPost(Long postId, ModifyPostRequestDto data);
    // 게시글 삭제
    void deletePost(Long postId);

    String uploadImage(MultipartFile file);
}
