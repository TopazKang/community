package org.paz.community.vote.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.paz.community.vote.comment.dto.ReadOneVotableCommentDto;
import org.paz.community.vote.comment.service.VotableCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vote-comments")
public class VotableCommentController {

    private final VotableCommentService votableCommentService;

    @PostMapping(value="/{postId}")
    @Operation(summary = "댓글 작성(투표)")
    public ResponseEntity<Void> createVotableComment(@PathVariable Long postId,
                                                     @RequestBody String comment) {
        votableCommentService.createVotableComment(postId, comment);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/{commentId}")
    @Operation(summary = "댓글 조회(투표)")
    public ResponseEntity<ReadOneVotableCommentDto> readVotableComment(@PathVariable Long commentId) {
        ReadOneVotableCommentDto response = votableCommentService.readOneVotableComment(commentId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정(투표)")
    public ResponseEntity<Void> modifyVotableComment(@PathVariable Long commentId,
                                                     @RequestBody String comment) {
        votableCommentService.modifyVotableComment(commentId, comment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제(투표)")
    public ResponseEntity<Void> deleteVotableComment(@PathVariable Long commentId) {
        votableCommentService.deleteVotableComment(commentId);
        return ResponseEntity.ok().build();
    }
}
