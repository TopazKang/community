package org.paz.community.vote.post.service;

import org.paz.community.vote.post.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    // 게시물 작성
    void createVotablePost(CreateVotablePostDto createVotablePostDto, List<MultipartFile> files);
    // 게시물 전체 조회
    List<ReadSummaryVotablePostDto> readAllVotablePost();
    // 게시물 페이지 조회
    ReadSummaryWithPagedVotablePostDto readAllVotablePostWithPage(Pageable pageable);
    // 게시물 단일 조회
    ReadOneVotablePostDto readOneVotablePost(Long postId);
    // 게시물 수정
    void modifyVotablePost(Long postId, ModifyVotablePostDto modifyVotablePostDto);
    // 게시물 삭제
    void deleteVotablePost(Long postId);

}
