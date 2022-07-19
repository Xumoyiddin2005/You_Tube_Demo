package com.company.entity.video;

import com.company.entity.TagEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_tag")
public class VideoTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "video_id")
    private String videoId;

    @ManyToOne
    @JoinColumn(name = "video_id",nullable = false,insertable = false,updatable = false)
    private VideoEntity video;

    @Column(name = "tag_id")
    private Integer tagId;

    @ManyToOne
    @JoinColumn(name = "tag_id",nullable = false,insertable = false,updatable = false)
    private TagEntity tag;

    @Column(nullable = false,name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
