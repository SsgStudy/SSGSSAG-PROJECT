package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.OrderDetailVO;
import com.ssg.ssgssag.domain.OrderVO;
import com.ssg.ssgssag.domain.ProductVO;
import com.ssg.ssgssag.dto.OrderDetailDTO;
import com.ssg.ssgssag.dto.OrderProductDTO;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    Long selectLastOrderSeq();
    OrderProductDTO selectProductInventory(Map<String, Object> map);
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
//    int updateOrderStatus(Long orderSeq);

}
