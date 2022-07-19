package com.company.entity.comment;

import com.company.entity.ProfileEntity;
import com.company.enums.CommentLikeType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment_like")
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;

    @ManyToOne
    @JoinColumn(name = "profile_id",nullable = false,insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "comment_id",nullable = false,insertable = false,updatable = false)
    private CommentEntity comment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentLikeType type;

    @Column(nullable = false,name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
