package org.paz.community.comment.repository;

import org.paz.community.comment.entity.CommentEntity;
import org.paz.community.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostEntityAndDeletedAtIsNull(PostEntity postEntity);
}
