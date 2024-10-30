package com.example.hotel_details_project.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "question_db")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "aptitude_id")
    private Long aptitudeId;

    @Column(name = "questions")
    private String questions;

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
