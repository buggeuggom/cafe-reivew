package com.cafe.review.dto.request.review;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class DeleteReviewRequest {

    @Pattern(regexp = "^[A-Za-z[0-9]]{10,20}$", message = "비밀번호는 영문과 숫자로 10~20자로만 가능합니다.")
    private String password;

    @Builder
    private DeleteReviewRequest(String password) {
        this.password = password;
    }
}
