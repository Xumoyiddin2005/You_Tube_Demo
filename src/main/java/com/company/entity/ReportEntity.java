package com.company.entity;

import com.company.enums.EntityType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "report")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;

    @ManyToOne
    @JoinColumn(name = "profile_id",nullable = false,insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(nullable = false)
    private String content;

    @Column(name = "entity_id",nullable = false)
    private String entityId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityType type;

    @Column(nullable = false,name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
