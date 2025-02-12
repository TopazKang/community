package org.paz.community.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.paz.community.post.dto.CreatePostRequestDto;
import org.paz.community.post.dto.ModifyPostRequestDto;
import org.paz.community.post.dto.ReadOnePostResponseDto;
import org.paz.community.post.dto.ReadSummaryPostResponseDto;
import org.paz.community.post.service.impl.PostServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "게시글 작성")
    public ResponseEntity<Void> createPost(@RequestHeader("Authorization") String token,
                                           @RequestPart(value="request") CreatePostRequestDto data,
                                           @RequestPart(value = "file", required = false) List<MultipartFile> files){
        postService.createPost(token.substring(7), data,files);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/")
    @Operation(summary = "전체 게시글 조회")
    public ResponseEntity<List<ReadSummaryPostResponseDto>> readAllPost(){
        List<ReadSummaryPostResponseDto> response = postService.readAllPost();

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{postId}")
    @Operation(summary = "단일 게시글 조회")
    public ResponseEntity<ReadOnePostResponseDto> readOnePost(@PathVariable Long postId){
        ReadOnePostResponseDto response = postService.readOnePost(postId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "게시글 수정")
    public ResponseEntity<Void> modifyPost(@PathVariable Long postId,
                                           @RequestPart ModifyPostRequestDto data,
                                           @RequestPart (value="file", required = false) List<MultipartFile> files){
        postService.modifyPost(postId, data, files);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{postId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return ResponseEntity.ok().build();
    }
}
