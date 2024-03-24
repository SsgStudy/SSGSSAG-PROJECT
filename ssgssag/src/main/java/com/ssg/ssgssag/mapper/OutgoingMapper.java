package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.OutgoingProductVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutgoingMapper {

  List<OutgoingProductVO> selectAllOutgoingProductsWithDetails();

}
