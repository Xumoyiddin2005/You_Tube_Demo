package com.company.entity;

import com.company.enums.PlaylistStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Getter
//@Setter
//@Entity
//@Table(name = "playlist")
//public class PlaylistEntity {
//    @Id
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")
//    @Column(name = "id", unique = true)
//    private String id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private Integer order;
//
//    @Column(name = "preview_id")
//    private String previewId;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "preview_id",insertable = false,updatable = false)
//    private AttachEntity preview;
//
//    @Column(name = "channel_id")
//    private String channelId;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "channel_id",insertable = false,updatable = false)
//    private ChannelEntity channel;
//
//    @Column(name = "created_date",nullable = false)
//    private LocalDateTime createdDate = LocalDateTime.now();
//
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;

@Getter
@Setter
@Entity
@Table(name = "playlist")
public class PlaylistEntity extends BaseEntity{
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "channel_id")
    private String channelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false,insertable = false,updatable = false)
    private ChannelEntity channel;

    @Column(name = "order_number")
    private Integer order;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaylistStatus status = PlaylistStatus.ACTIVE;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "preview_id")
    private String previewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preview_id", nullable = false,insertable = false,updatable = false)
    private AttachEntity preview;

}
