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
@Table(name = "student_exam")
public class StudentExamScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "student_exam_score_id")
    private Long studentExamScoreId;

    @Column(name = "score")
    private int score;

    @Column(name = "is_deleted")
    private Boolean isDeleted=false

            ;

    @Column(name = "is_active")
    private  Boolean isActive;

    @CreationTimestamp
    @Column(updatable = false,name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
