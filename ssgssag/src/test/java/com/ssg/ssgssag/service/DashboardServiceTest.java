package com.ssg.ssgssag.service;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class DashboardServiceTest {

    @Autowired
    private final DashboardService dashboardService;

    @Autowired
    public DashboardServiceTest(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Test
    public void getAllStatusCountTest() {
        log.info(dashboardService.getAllStatusCount());
    }
}