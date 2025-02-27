package org.paz.community.vote.vote.service.impl;

import lombok.RequiredArgsConstructor;
import org.paz.community.member.entity.MemberEntity;
import org.paz.community.member.repository.MemberJpaRepository;
import org.paz.community.vote.post.entity.VotablePostEntity;
import org.paz.community.vote.post.repository.VotablePostRepository;
import org.paz.community.vote.post.service.VotablePostService;
import org.paz.community.vote.vote.entity.VoteEntity;
import org.paz.community.vote.vote.repository.VoteRepository;
import org.paz.community.vote.vote.service.VoteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final VotablePostRepository votablePostRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final VotablePostService votablePostService;


    @Override
    public void votePost(Long userId, Long postId) {
        // 사용자 정보 조회
        MemberEntity memberEntity = memberJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("투표: 멤버 엔티티 조회 오류"));
        // 원본 게시글(투표) 조회
        VotablePostEntity votablePostEntity = votablePostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("투표: 포스트(투표) 엔티티 조회 오류"));

        // 투표 엔티티 빌드
        VoteEntity voteEntity = VoteEntity.builder()
                .memberEntity(memberEntity)
                .votablepostEntity(votablePostEntity)
                .build();

        votablePostService.increaseVoteCount(postId);

        // 투표 DB 반영
        voteRepository.save(voteEntity);
    }
}
