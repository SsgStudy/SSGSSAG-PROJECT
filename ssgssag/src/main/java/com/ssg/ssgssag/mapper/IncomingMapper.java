package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.IncomingVO;
import com.ssg.ssgssag.dto.IncomingDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IncomingMapper {

    //발주 입고 리스트 전체 조회
    List<IncomingDTO> selectAllIncomingProductsWithDetails();
}
