package com.company.entity;

import com.company.enums.TagStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TagStatus status = TagStatus.ACTIVE;

    @Column(nullable = false)
    private Boolean visible = Boolean.FALSE;

    @Column(nullable = false,name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
