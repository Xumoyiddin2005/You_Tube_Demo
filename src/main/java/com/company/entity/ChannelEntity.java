package com.company.entity;

import com.company.enums.ChannelStatus;
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
@Table(name = "channel")
public class ChannelEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "photo_id")
    private String photoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id",insertable = false,updatable = false)
    private AttachEntity photo;

    @Column(name = "banner_id")
    private String bannerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id",insertable = false,updatable = false)
    private AttachEntity banner;

    @Column(name = "profile_id")
    private Integer profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(length = 500,name = "telegram_url")
    private String telegramUrl;

    @Column(length = 500,name = "instagram_url")
    private String instagramUrl;

    @Column(length = 500,name = "website_url")
    private String websiteUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChannelStatus status = ChannelStatus.ACTIVE;

    @Column(nullable = false)
    private Boolean visible = Boolean.FALSE;

    @Column(nullable = false,name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    public ChannelEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
