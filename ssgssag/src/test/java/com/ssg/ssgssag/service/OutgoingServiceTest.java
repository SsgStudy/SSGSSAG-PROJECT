package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.OutgoingProductVO;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class OutgoingServiceTest {

  @Autowired
  private final OutgoingService outgoingService;


  @Autowired
  public OutgoingServiceTest(OutgoingService outgoingService) {
    this.outgoingService = outgoingService;
  }

  @Test
  public void getAllOutgoingProductsWithDetailsTest() {
    List<OutgoingProductVO> outgoingProductVOList = outgoingService.getAllOutgoingProductsWithDetails();
    log.info(outgoingProductVOList);
  }

}
