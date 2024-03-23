package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.IncomingDetailDTO;
import com.ssg.ssgssag.dto.OrderSupplierDTO;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IncomingMapper {

    //입고 현황 전체 조회
    List<IncomingDTO> selectAllIncomingProductsWithDetails();

    //입고 현황 기간,창고코드,매입처,입고구분으로 조회
    List<IncomingDTO> selectAllIncomingProductsWithDetailsByOption(
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        @Param("warehouseCd") String warehouseCd,
        @Param("supplierNm") String supplierNm,
        @Param("status") String status
    );

    //미승인 입고 리스트 조회
    List<IncomingDTO> selectAllUnconfirmIncomingProductsWithDetailsByOption(
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        @Param("warehouseCd") String warehouseCd,
        @Param("supplierNm") String supplierNm,
        @Param("status") String status
    );

    //입고 승인 리스트 조회
    List<IncomingDTO> selectAllIncomingProgressProductsWithDetails();


    //입고 상세(상품 정보) 조회
    IncomingDetailDTO selectIncomingDetailByCode(
        @Param("pkIncomingProductSeq") String pkIncomingProductSeq);

    //입고 승인
    void updateIncomingProductStatusToComplete(
        @Param("pkIncomingProductSeq") String pkIncomingProductSeq);
    void updateIncomingProductStatusForRegister(
        @Param("pkIncomingProductSeq") String pkIncomingProductSeq,
        @Param("vzoneCd") String vzoneCd,
        @Param("dtIncomingProductDate") String dtIncomingProductDate);


    //매입 거래처 목록 조회
    List<OrderSupplierDTO> selectAllOrderSupplierName();

}
