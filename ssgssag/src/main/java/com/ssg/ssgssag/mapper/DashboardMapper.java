package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.dto.BestCategoryDTO;
import com.ssg.ssgssag.dto.BestProductDTO;
import com.ssg.ssgssag.dto.DailyPurchaseCountDTO;
import com.ssg.ssgssag.dto.StatusCountDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DashboardMapper {

    //카드 영역 정보 조회
    //입고, 출고, 주문, 발주, 재고, 창고 수준 조회
    List<StatusCountDTO> selectAllStatusCount();

    List<BestProductDTO> selectBestProducts();

    List<DailyPurchaseCountDTO> selectDailyPurchaseStatistics();

    List<BestCategoryDTO> selectBestCategoryList();
}
