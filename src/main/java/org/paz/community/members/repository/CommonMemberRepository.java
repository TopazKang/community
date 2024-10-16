package org.paz.community.members.repository;

import org.paz.community.members.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CommonMemberRepository implements MemberRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public CommonMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //RowMapper 선언
    public class LoginRowMapper implements RowMapper<Member> { // 로그인 반환
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException{
            int id = rs.getInt("id");
            String password = rs.getString("password");
            String profile_image_path = rs.getString("profile_image_path");

            return Member.withLoginReturn(id, password, profile_image_path);
        }
    }
    public class InfoRowMapper implements RowMapper<Member>{ // 회원정보 조회 후 반환
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException{
            String nickname = rs.getString("nickname");
            String email = rs.getString("email");

            return new Member(nickname, email);
        }
    }

    // 레포지토리 메서드
    // 회원가입
    @Override
    public void createUser(Member member) {
        String sql = "insert into members values(null, ?, ?, ?, ?, now(), now(), null)";
        jdbcTemplate.update(sql, member.getNickname(), member.getEmail(), member.getPassword(), member.getProfile_image_path());
    }
    // 이메일 중복 확인
    @Override
    public int emailChk(Member member) {
        String sql = "select count(*) from members where email = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, member.getEmail());
    }
    // 닉네임 중복 확인
    @Override
    public int nicknameChk(Member member) {
        String sql = "select count(*) from members where nickname = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, member.getNickname());
    }
    // 로그인을 위한 비밀번호 조회 및 반환
    @Override
    public Member login(Member member) {
        String sql = "select id, password, profile_image_path from members where email = ? and deleted_at is null";
        return jdbcTemplate.queryForObject(sql, new Object[]{member.getEmail()}, new LoginRowMapper());
    }
    // 회원정보 조회
    @Override
    public Member readInfo(Member member) {
        String sql = "select nickname, email from members where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{member.getId()}, new InfoRowMapper());
    }
    // 회원정보 수정
    @Override
    public void modifyInfo(Member member) {
        StringBuilder sql = new StringBuilder("update members set ");
        Map<String, Object> updates = new HashMap<>();

        if(member.getNickname() != null) {
            sql.append("nickname = ?, ");
            updates.put("nickname", member.getNickname());
        }

        if(member.getProfile_image_path() != null) {
            sql.append("profile_image_path = ?, ");
            updates.put("profile_image_path", member.getProfile_image_path());
        }

        sql.delete(sql.length() - 2, sql.length());
        sql.append("where id = ?");
        updates.put("id", member.getId());

        Object[] params = updates.values().toArray();

        jdbcTemplate.update(sql.toString(), params);
    }
    // 비밀번호 변경
    @Override
    public void changePassword(Member member) {
        String sql = "update members set password = ?, updated_at = now() where id = ?";
        jdbcTemplate.update(sql, member.getPassword(), member.getId());
    }
    // 회원 탈퇴
    @Override
    public void deleteMember(Member member) {
        String sql = "update members set deleted_at = now() where id = ?";
        jdbcTemplate.update(sql, member.getId());
    }
}
