package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.IncomingDetailDTO;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class IncomingServiceTest {

    @Autowired
    private final IncomingService incomingService;

    @Autowired
    public IncomingServiceTest(IncomingService incomingService) {
        this.incomingService = incomingService;
    }

    @Test
    public void getAllIncomingProductsWithDetailsTest() {
        List<IncomingDTO> incomingDTOList = incomingService.getAllIncomingProductsWithDetails();
        log.info(incomingDTOList);
    }

    @Test
    public void getIncomingDetailByCodeTest() {
        String pkIncomingProductSeq = "1";
        IncomingDetailDTO detail = incomingService.getIncomingDetailByCode(pkIncomingProductSeq);
        log.info(detail);
    }


}
