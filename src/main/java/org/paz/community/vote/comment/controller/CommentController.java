package org.paz.community.vote.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.paz.community.vote.comment.dto.ReadOneVotableCommentDto;
import org.paz.community.vote.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vote-comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value="/{postId}")
    @Operation(summary = "댓글 작성(투표)")
    public ResponseEntity<Void> createVotableComment(@PathVariable Long postId,
                                                     @RequestBody String comment) {
        commentService.createVotableComment(postId, comment);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/{commentId}")
    @Operation(summary = "댓글 조회(투표)")
    public ResponseEntity<ReadOneVotableCommentDto> readVotableComment(@PathVariable Long commentId) {
        ReadOneVotableCommentDto response = commentService.readOneVotableComment(commentId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정(투표)")
    public ResponseEntity<Void> modifyVotableComment(@PathVariable Long commentId,
                                                     @RequestBody String comment) {
        commentService.modifyVotableComment(commentId, comment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제(투표)")
    public ResponseEntity<Void> deleteVotableComment(@PathVariable Long commentId) {
        commentService.deleteVotableComment(commentId);
        return ResponseEntity.ok().build();
    }
}
