package org.paz.community.vote.post.repository;

import org.paz.community.vote.post.entity.VotablePostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotablePostRepository extends JpaRepository<VotablePostEntity, Long> {

    List<VotablePostEntity> findByDeletedAtIsNull();

    Page<VotablePostEntity> findByDeletedAtIsNull(Pageable pageable);

    Long countByDeletedAtIsNull();
}
