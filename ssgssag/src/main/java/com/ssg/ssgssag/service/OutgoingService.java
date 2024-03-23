package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.OutgoingProductVO;
import java.util.List;

public interface OutgoingService {

  List<OutgoingProductVO> getAllOutgoingProductsWithDetails();

}
