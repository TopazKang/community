package org.paz.community.member.userDetails;

import lombok.RequiredArgsConstructor;
import org.paz.community.member.entity.MemberEntity;
import org.paz.community.member.entity.UserEntity;
import org.paz.community.member.repository.MemberJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity userData = memberJpaRepository.findByEmail(email);

        UserEntity user = new UserEntity(userData.getId(), userData.getEmail(), userData.getPassword());

        if(user != null){
            return new CustomUserDetails(user);
        }
        return null;
    }
}