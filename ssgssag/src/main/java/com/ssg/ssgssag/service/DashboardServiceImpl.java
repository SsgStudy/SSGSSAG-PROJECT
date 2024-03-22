package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.StatusCountDTO;
import com.ssg.ssgssag.mapper.DashboardMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private final DashboardMapper dashboardMapper;

    @Override
    public List<StatusCountDTO> getAllStatusCount() {
        return dashboardMapper.selectAllStatusCount();
    }
}
