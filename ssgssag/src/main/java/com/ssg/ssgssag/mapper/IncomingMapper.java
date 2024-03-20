package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.IncomingVO;
import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.IncomingDetailDTO;
import com.ssg.ssgssag.dto.ProductDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IncomingMapper {

    //입고 현황 전체 조회
    List<IncomingDTO> selectAllIncomingProductsWithDetails();

    //입고 승인 리스트 조회
    List<IncomingDTO> selectAllIncomingProgressProductsWithDetails();

    //입고 상세(상품 정보) 조회
    IncomingDetailDTO selectIncomingDetailByCode(@Param("pkIncomingProductSeq") String pkIncomingProductSeq);

    //입고 승인
    void updateIncomingProductStatusToComplete(@Param("pkIncomingProductSeq") String pkIncomingProductSeq);

}
