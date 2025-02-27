package org.paz.community.vote.vote.service;

import lombok.RequiredArgsConstructor;
import org.paz.community.vote.vote.repository.VoteRepository;
import org.springframework.stereotype.Service;

public interface VoteService {
    public void votePost(Long userId, Long postId);
}
