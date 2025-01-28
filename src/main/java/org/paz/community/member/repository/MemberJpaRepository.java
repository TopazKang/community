package org.paz.community.member.repository;

import org.paz.community.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    MemberEntity findByEmail(String email);

}
