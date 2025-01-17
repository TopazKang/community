package org.paz.community.vote.comment.repository;

import org.paz.community.vote.post.entity.VotablePostEntity;
import org.paz.community.vote.comment.entity.VotableCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotableCommentRepository extends JpaRepository<VotableCommentEntity, Long> {

    List<VotableCommentEntity> findByVotablePostEntityAndDeletedAtIsNull(VotablePostEntity votablePostEntity);
}
