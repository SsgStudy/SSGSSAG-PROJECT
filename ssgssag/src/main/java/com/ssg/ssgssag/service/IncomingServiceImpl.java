package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.IncomingDetailDTO;
import com.ssg.ssgssag.dto.ProductDTO;
import com.ssg.ssgssag.mapper.IncomingMapper;
import java.util.Date;
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

    @Override
    public List<IncomingDTO> getAllIncomingProgressProductsWithDetails() {
        return incomingMapper.selectAllIncomingProgressProductsWithDetails();
    }

    @Override
    public IncomingDetailDTO getIncomingDetailByCode(String pkIncomingProductSeq) {
        return incomingMapper.selectIncomingDetailByCode(pkIncomingProductSeq);
    }

    @Override
    public void confirmIncomingProducts(List<String> pkIncomingProductSeqs) {
        for (String pk : pkIncomingProductSeqs) {
            incomingMapper.updateIncomingProductStatusToComplete(pk);
        }
    }

    @Override
    public List<IncomingDTO> getAllIncomingProductsWithDetailsByOption(Date startDate, Date endDate,
        String warehouseCd, String supplierNm, String status) {
        return incomingMapper.selectAllIncomingProductsWithDetailsByOption(startDate, endDate,
            warehouseCd, supplierNm, status);
    }

}
