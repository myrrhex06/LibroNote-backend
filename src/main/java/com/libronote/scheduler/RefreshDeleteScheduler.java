package com.libronote.scheduler;

import com.libronote.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class RefreshDeleteScheduler {

    private final RefreshTokenService refreshTokenService;

    @Scheduled(cron = "*/10 * * * * *")
    public void deleteRefreshToken(){
        log.debug("Delete Refresh Token Scheduler 동작");
        refreshTokenService.deleteRefreshTokenUseYn();
    }
}
