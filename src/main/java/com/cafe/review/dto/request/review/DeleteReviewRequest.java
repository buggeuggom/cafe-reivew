package com.cafe.review.dto.request.review;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class DeleteReviewRequest {

    @Length(min = 5, max = 15, message = "비밀번호는 5 ~ 15자 입니다.")
    private String password;

    @Builder
    private DeleteReviewRequest(String password) {
        this.password = password;
    }
}
