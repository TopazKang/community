package org.paz.community.vote.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.paz.community.global.entity.BaseEntity;
import org.paz.community.member.entity.MemberEntity;
import org.paz.community.vote.comment.entity.VotableCommentEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="post_votable")
public class VotablePostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="post_image_path", nullable = false)
    private String postImagePath;

    @Column(name="likes_count", nullable = false)
    private Integer likesCount = 0;

    @Column(name="hits_count", nullable = false)
    private Integer hitsCount = 0;

    @Column(name="replies_count", nullable = false)
    private Integer repliesCount = 0;

    @Column(name="votes_count", nullable = false)
    private Integer votesCount = 0;

    @Column(name="tags")
    private String tags;

    @Column(name="shutter", nullable = false)
    private String shutter;

    @Column(name="iso", nullable = false)
    private String iso;

    @Column(name="whitebalance", nullable = false)
    private String whitebalance;

    @Column(name="aperture", nullable = false)
    private String aperture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "votablePostEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VotableCommentEntity> commentEntities;



    // 수정을 위한 setter 로직

    /**
     * 게시글 제목 수정 Setter
     * @param modifiedTitle 제목 수정 데이터
     */
    public void modityTitle(String modifiedTitle){
        this.title = modifiedTitle;
    }

    /**
     * 게시글 내용 수정 Setter
     * @param modifiedContent 내용 수정 데이터
     */
    public void modifyContent(String modifiedContent){
        this.content = modifiedContent;
    }

    /**
     * 게시글 이미지 경로 수정 Setter
     * @param modifiedPath 이미지 경로 수정 데이터
     */
    public void modifyPostImagePath(String modifiedPath){
        this.postImagePath = modifiedPath;
    }

    /**
     * 게시글 태그 배열 수정 Setter
     * @param tags 태그 배열 수정 데이터
     */
    public void modifyTags(String tags){
        this.tags = tags;
    }

    /**
     * 게시글 득표 수 추가 Setter
     */
    public void increaseVotesCount() {
        this.votesCount = this.votesCount + 1;
    }
}
