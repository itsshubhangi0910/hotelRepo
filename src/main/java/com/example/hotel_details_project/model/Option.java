package com.example.hotel_details_project.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "option_db")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "option_id")
    private Long optionId;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "options")
    private String options;


    @Column(name = "is_deleted")
    private Boolean isDeleted=false;

    @Column(name = "is_active")
    private  Boolean isActive;

    @CreationTimestamp
    @Column(updatable = false,name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
