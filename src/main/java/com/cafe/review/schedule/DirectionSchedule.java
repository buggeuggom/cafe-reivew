package com.cafe.review.schedule;

import com.cafe.review.repository.DirectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DirectionSchedule {

    private final DirectionRepository directionRepository;

    @Transactional
    @Scheduled(cron = "0 0 1 1 * ?", zone = "Asia/Seoul") //매월 1일 1시
    public void autoDelete() {
        directionRepository.deleteByCreatedAtLessThanEqual(LocalDateTime.now().minusMonths(1));
    }

}
