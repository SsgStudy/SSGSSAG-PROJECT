package com.ssg.ssgssag.service;

import com.ssg.ssgssag.mapper.PasswordFindMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PasswordFindServiceImpl implements PasswordFindService{

    @Autowired
    private final PasswordFindMapper passwordFindMapper;

    @Override
    public String selectEmailById(String id) {
        return passwordFindMapper.selectEmailById(id);
    }
}
