package org.paz.community.vote.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.paz.community.global.entity.BaseEntity;
import org.paz.community.member.entity.MemberEntity;
import org.paz.community.vote.post.entity.PostEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="comment_votable")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="comment", nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MemberEntity memberEntity;

    // 댓글 수정을 위한 setter 로직

    /**
     * votable 게시판 댓글 수정
     * @param comment 댓글 수정을 위한 텍스트
     */
    public void modifyComment(String comment){
        this.comment = comment;
    }
}
