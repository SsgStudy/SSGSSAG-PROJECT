package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.OutgoingProductVO;
import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.mapper.IncomingMapper;
import com.ssg.ssgssag.mapper.OutgoingMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class OutgoingServiceImpl implements OutgoingService{

  @Autowired
  private final OutgoingMapper outgoingMapper;

  @Override
  public List<OutgoingProductVO> getAllOutgoingProductsWithDetails() {
    return outgoingMapper.selectAllOutgoingProductsWithDetails();
  }

}
