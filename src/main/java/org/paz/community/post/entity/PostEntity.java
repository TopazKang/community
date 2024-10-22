package org.paz.community.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.paz.community.comment.entity.CommentEntity;
import org.paz.community.global.entity.BaseEntity;
import org.paz.community.member.entity.MemberEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="post")
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="post_image_path", nullable = false)
    private String post_image_path;

    @Column(name="likes_count")
    private Integer likes_count;

    @Column(name="hits_count")
    private Integer hits_count;

    @Column(name="replies_count")
    private Integer replies_count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "postEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> commentEntities;
}
