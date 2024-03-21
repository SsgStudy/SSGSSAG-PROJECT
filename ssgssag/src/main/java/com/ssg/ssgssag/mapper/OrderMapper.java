package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.OrderDetailVO;
import com.ssg.ssgssag.domain.OrderProductVO;
import com.ssg.ssgssag.domain.OrderVO;
import com.ssg.ssgssag.domain.ProductVO;
import com.ssg.ssgssag.dto.OrderDetailDTO;
import com.ssg.ssgssag.dto.OrderProductDTO;
import com.ssg.ssgssag.dto.OrderReadSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
//  발주 등록
    Long selectLastOrderSeq();
    OrderProductVO selectProductInventory(Map<String, Object> map); // 발주 상세 입력 정보 불러옴
    String selectProductSupplier(@Param("productCd") String productCd); // 매입거래처 내의 상품인지 확인
    int insertOrder(OrderVO order);
    int insertOrderDetail(List<OrderDetailVO> orderDetails);

// 발주 조회
    List<OrderProductDTO> selectOrderDetailByDateOrString(OrderReadSearchDTO orderReadSearch);
    List<OrderProductDTO> selectOrderDetailByOrderSeq(Long orderSeq);


// 발주 확정
        int updateOrderStatusByOrderSeq(@Param("pkOrderSeq") List<Long> orderSeq);

//    int insertOrder(OrderVO order);
//    int insertOrderDetail(OrderDetailVO orderDetail);
//    ProductVO selectOrderProductByProductCd(String productCd);
//
//    List<OrderDetailDTO> selectOrderDetailByDateAndString(OrderDetailDTO orderDetail); // 발주 상세 조회
//
//    List<OrderVO> getAllOrdersWithDetails();
//    List<OrderVO> getAllOrdersStatusProgress();
//    List<ProductVO> getAllProductQuantity();
//    OrderDetailVO getOneOrderInformation(Long orderSeq);


}
