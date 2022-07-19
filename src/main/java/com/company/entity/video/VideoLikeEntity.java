package com.company.entity.video;

import com.company.entity.ProfileEntity;
import com.company.enums.VideoLikeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_like")
@NoArgsConstructor
public class VideoLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;

    @ManyToOne
    @JoinColumn(name = "profile_id",nullable = false,insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(name = "video_id")
    private String videoId;

    @ManyToOne
    @JoinColumn(name = "video_id",nullable = false,insertable = false,updatable = false)
    private VideoEntity video;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VideoLikeType type;

    @Column(nullable = false,name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

}
