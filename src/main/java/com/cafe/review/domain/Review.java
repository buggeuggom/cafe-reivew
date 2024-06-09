package com.cafe.review.domain;

import com.cafe.review.dto.request.review.PutReviewRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table
@NoArgsConstructor(access = PROTECTED)
public class Review extends AuditingFields{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    //정보 기입자
    private String writerId;
    private String password;

    // 내용
    private String title;
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;
    @JoinColumn(name = "cafe_id")
    @ManyToOne(fetch = LAZY, optional = false)
    private Cafe cafe;
    @Column(name = "taste_rating", nullable = false)
    private Integer tasteRating; //맛
    @Column(name = "ambience_rating")
    private Integer ambienceRating; //분위기
    @Column(name = "service_rating")
    private Integer serviceRating; //서비스

    @Builder
    private Review(String writerId, String password, String title, String comment, Cafe cafe, Integer tasteRating, Integer ambienceRating, Integer serviceRating) {
        this.writerId = writerId;
        this.password = password;
        this.title = title;
        this.comment = comment;
        this.cafe = cafe;
        this.tasteRating = tasteRating;
        this.ambienceRating = ambienceRating;
        this.serviceRating = serviceRating;
    }

    public void edit(PutReviewRequest request){
        this.title = request.getTitle();
        this.comment = request.getComment();
        this.tasteRating = request.getTasteRating();
        this.ambienceRating = request.getAmbienceRating();
        this.serviceRating = request.getServiceRating();
    }
}
