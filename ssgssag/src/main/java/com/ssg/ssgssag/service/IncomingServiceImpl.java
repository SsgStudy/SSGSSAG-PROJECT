package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.mapper.IncomingMapper;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class IncomingServiceImpl implements IncomingService {

    @Autowired
    private final IncomingMapper incomingMapper;

    @Override
    public List<IncomingDTO> getAllIncomingProductsWithDetails() {
        return incomingMapper.selectAllIncomingProductsWithDetails();
    }
}
