package org.paz.community.vote.vote.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.paz.community.member.userDetails.CustomUserDetails;
import org.paz.community.vote.vote.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/{postId}")
    @Operation(summary = "투표")
    public ResponseEntity<Void> votePost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @PathVariable Long postId) {
        voteService.votePost(userDetails.getId(), postId);

        return ResponseEntity.ok().build();
    }

}
