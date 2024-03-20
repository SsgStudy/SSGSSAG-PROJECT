package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.IncomingDetailDTO;
import com.ssg.ssgssag.dto.OrderSupplierDTO;
import com.ssg.ssgssag.dto.ProductDTO;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IncomingService {

    List<IncomingDTO> getAllIncomingProductsWithDetails();

    List<IncomingDTO> getAllIncomingProgressProductsWithDetails();

    IncomingDetailDTO getIncomingDetailByCode(String pkIncomingProductSeq);

    void confirmIncomingProducts(List<String> pkIncomingProductSeqs);

    List<IncomingDTO> getAllIncomingProductsWithDetailsByOption(Date startDate, Date endDate,
        String warehouseCd, String supplierNm, String status);

    List<IncomingDTO> getAllUnconfirmIncomingProductsWithDetailsByOption(Date startDate,
        Date endDate,
        String warehouseCd, String supplierNm, String status);

    List<OrderSupplierDTO> getAllOrderSupplierName();
}
