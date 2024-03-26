package com.ssg.ssgssag.service;


import com.ssg.ssgssag.dto.InventoryShortageDTO;

import java.util.List;

public interface UtilService {
    void sendShortageNotificationEmails();
    void sendResetPasswordLink(String email, String tempPassword);
}
