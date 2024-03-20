package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.IncomingDetailDTO;
import com.ssg.ssgssag.dto.ProductDTO;
import java.util.List;

public interface IncomingService {

    List<IncomingDTO> getAllIncomingProductsWithDetails();

    List<IncomingDTO> getAllIncomingProgressProductsWithDetails();

    IncomingDetailDTO getIncomingDetailByCode(String pkIncomingProductSeq);

    void confirmIncomingProducts(List<String> pkIncomingProductSeqs);

}
