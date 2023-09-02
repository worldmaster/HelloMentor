package com.kh.hellomentor.admin.model.service;

import com.kh.hellomentor.admin.model.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;


    @Override
    public int selectListCount() {
        return adminDao.selectCountList();
    }

    @Override
    public int selectSListCount() {
        return adminDao.selectSCountList();
    }

    @Override
    public int selectRListCount() {
        return adminDao.selectRCountList();
    }

    @Override
    public int selectIListCount() {
        return adminDao.selectICountList();
    }


}