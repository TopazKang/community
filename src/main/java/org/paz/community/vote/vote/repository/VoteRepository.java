package org.paz.community.vote.vote.repository;

import org.paz.community.vote.vote.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
}
