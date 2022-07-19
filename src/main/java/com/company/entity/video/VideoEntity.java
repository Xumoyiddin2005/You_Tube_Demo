package com.company.entity.video;

import com.company.entity.AttachEntity;
import com.company.entity.CategoryEntity;
import com.company.entity.ChannelEntity;
import com.company.enums.VideoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "video")
public class VideoEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;

    @Column(name = "attach_id")
    private String attachId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id",insertable = false,updatable = false)
    private AttachEntity attach;

    @Column(name = "preview_id")
    private String previewId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preview_id",insertable = false,updatable = false)
    private AttachEntity preview;

    @Column(name = "channel_id")
    private String channelId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id",insertable = false,updatable = false)
    private ChannelEntity channel;

    @Column(nullable = false,name = "shared_count")
    private Integer sharedCount = 0;

    @Column(nullable = false,name = "view_count")
    private Integer viewCount = 0;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VideoStatus status = VideoStatus.PUBLIC;

    @Column(nullable = false)
    private Boolean visible = Boolean.FALSE;

    @Column(name = "created_date",nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    public VideoEntity(String id) {
        this.id = id;
    }
}
