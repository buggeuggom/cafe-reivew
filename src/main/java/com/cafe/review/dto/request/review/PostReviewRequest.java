package com.cafe.review.dto.request.review;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class PostReviewRequest {

    @Pattern(regexp = "^[A-Za-z[0-9]]{5,15}$", message = "아이디는 영문과 숫자로 5~15자로만 가능합니다.")
    private String writerId;
    @Pattern(regexp = "^[A-Za-z[0-9]]{10,20}$", message = "비밀번호는 영문과 숫자로 10~20자로만 가능합니다.")
    private String password;

    @Length(min = 5, message = "제목은 5자 이상입니다.")
    @NotBlank(message = "제목은 빈칸일 수 없습니다.")
    private String title;
    @NotBlank(message = "코멘트는 빈칸일 수 없습니다.")
    private String comment;
    @Range(min = 0, max = 5, message = "맛 평가는 1 ~ 5의 숫자만 가능합니다.")
    private Integer tasteRating;
    @Range(min = 0, max = 5, message = "분위기 평가는 1 ~ 5의 숫자만 가능합니다.")
    private Integer ambienceRating; //분위기
    @Range(min = 0, max = 5, message = "서비스 평가는 1 ~ 5의 숫자만 가능합니다.")
    private Integer serviceRating;

    @Builder
    private PostReviewRequest(String writerId, String password, String title, String comment, Integer tasteRating, Integer ambienceRating, Integer serviceRating) {
        this.writerId = writerId;
        this.password = password;
        this.title = title;
        this.comment = comment;
        this.tasteRating = tasteRating;
        this.ambienceRating = ambienceRating;
        this.serviceRating = serviceRating;
    }
}
