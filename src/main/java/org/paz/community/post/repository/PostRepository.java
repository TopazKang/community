package org.paz.community.post.repository;

import org.paz.community.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByDeletedAtIsNull();

    Page<PostEntity> findByDeletedAtIsNull(Pageable pageable);

    Long countByDeletedAtIsNull();
}
