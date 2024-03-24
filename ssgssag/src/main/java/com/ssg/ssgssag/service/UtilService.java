package com.ssg.ssgssag.service;


import com.ssg.ssgssag.dto.InventoryShortageDTO;

import java.util.List;

public interface UtilService {
    List<InventoryShortageDTO> findShortageInventory();
}
