package org.paz.community.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.paz.community.comment.dto.ReadOneCommentResponseDto;
import org.paz.community.comment.service.impl.CommentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping(value="/{postId}")
    @Operation(summary = "댓글 작성")
    public ResponseEntity<Void> createComment(@RequestHeader("Authorization") String token,
                                              @PathVariable Long postId,
                                              @RequestPart String comment){
        commentService.createComment(token, postId, comment);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/{commentId}")
    @Operation(summary = "단일 댓글 조회")
    public ResponseEntity<ReadOneCommentResponseDto> readOneComment(@PathVariable Long commentId){
        ReadOneCommentResponseDto response = commentService.readOneComment(commentId);

        return ResponseEntity.ok(response);
    }

    @PutMapping(value="/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<Void> modifyComment(@PathVariable Long commentId,
                                              @RequestPart String comment){
        commentService.modifyComment(commentId, comment);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value="/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);

        return ResponseEntity.ok().build();
    }
}
