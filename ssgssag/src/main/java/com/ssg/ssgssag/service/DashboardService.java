package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.StatusCountDTO;
import java.util.List;

public interface DashboardService {

    List<StatusCountDTO> getAllStatusCount();

}
