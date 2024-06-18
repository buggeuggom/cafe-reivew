package com.cafe.review.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Builder
@AllArgsConstructor
public class CafeSearchRequest {

    private static final int MAX_SIZE = 2_000;

    //조건
    private String roadAddress;
    private String storeName;

    private int page;
    private int size;

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }

}
