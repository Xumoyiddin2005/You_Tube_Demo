package com.company.entity;

import com.company.enums.NotificationType;
import com.company.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "subscription")
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;

    @ManyToOne
    @JoinColumn(name = "profile_id",nullable = false,insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(name = "channel_id")
    private String channelId;

    @ManyToOne
    @JoinColumn(name = "channel_id",nullable = false,insertable = false,updatable = false)
    private ChannelEntity channel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type = NotificationType.PERSONALIZED;

    @Column(nullable = false,name = "created_date")
    private LocalDateTime createdDate;
}
