package org.paz.community.vote.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.paz.community.vote.post.dto.*;
import org.paz.community.vote.post.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vote-posts")
public class PostController {

    private final PostService postService;

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "투표 게시글 작성")
    public ResponseEntity<Void> createVotablePost(@RequestPart CreateVotablePostDto data,
                                                  @RequestPart (value="file", required = true) List<MultipartFile> files){
        postService.createVotablePost(data, files);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/")
    @Operation(summary = "투표 전체 게시글 조회")
    public ResponseEntity<List<ReadSummaryVotablePostDto>> readAllVotablePosts(){
        List<ReadSummaryVotablePostDto> response = postService.readAllVotablePost();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/paged")
    @Operation(summary = "투표 게시글 페이징 조회")
    public ResponseEntity<ReadSummaryWithPagedVotablePostDto> readAllVotablePostsPaged(Pageable pageable){
        ReadSummaryWithPagedVotablePostDto response = postService.readAllVotablePostWithPage(pageable);
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "/{postId}")
    @Operation(summary = "투표 게시글 단일 조회")
    public ResponseEntity<ReadOneVotablePostDto> readOneVotablePost(@PathVariable Long postId){
        ReadOneVotablePostDto response = postService.readOneVotablePost(postId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/{postId}")
    @Operation(summary = "투표 게시글 수정")
    public ResponseEntity<Void> updateVotablePost(@PathVariable Long postId,
                                                  @RequestBody ModifyVotablePostDto data){
        postService.modifyVotablePost(postId, data);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{postId}")
    @Operation(summary = "투표 게시글 삭제")
    public ResponseEntity<Void> deleteVotablePost(@PathVariable Long postId){
        postService.deleteVotablePost(postId);

        return ResponseEntity.ok().build();
    }

}
