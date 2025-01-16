package org.paz.community.vote.comment.repository;

import org.paz.community.vote.post.entity.PostEntity;
import org.paz.community.vote.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByPostEntityAndDeletedAtIsNull(PostEntity postEntity);
}
