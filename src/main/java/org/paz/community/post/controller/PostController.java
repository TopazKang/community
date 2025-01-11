package org.paz.community.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.paz.community.post.dto.*;
import org.paz.community.post.service.impl.PostServiceImpl;
import org.springframework.data.domain.Pageable;
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

    @PostMapping(value = "/")
    @Operation(summary = "게시글 작성")
    public ResponseEntity<Void> createPost(@RequestBody CreatePostRequestDto data){
        postService.createPost(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/")
    @Operation(summary = "전체 게시글 조회")
    public ResponseEntity<List<ReadSummaryPostResponseDto>> readAllPost(){
        List<ReadSummaryPostResponseDto> response = postService.readAllPost();

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/paged")
    @Operation(summary = "게시글 페이징 조회")
    public ResponseEntity<ReadSummaryWithPagedPostDto> readAllPostWithPage(Pageable pageable){
        ReadSummaryWithPagedPostDto response = postService.readAllPostWithPage(pageable);

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

    @PostMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "이미지 업로드")
    public ResponseEntity<String> uploadImage(@RequestPart(value = "file", required = false) MultipartFile file){
        String response = postService.uploadImage(file);
        return ResponseEntity.ok(response);
    }
}
