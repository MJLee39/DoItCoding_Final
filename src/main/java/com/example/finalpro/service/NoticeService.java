package com.example.finalpro.service;

import com.example.finalpro.dao.NoticeDAO;
import com.example.finalpro.entity.Notice;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class NoticeService {

    @Autowired
    private NoticeDAO dao;

    public List<Notice> findAll(){return dao.findAll();}

    public Optional<Notice> findById(int notice_no){
        return dao.findById(notice_no);
    }

//    public void insert(Notice n){
//        dao.insert(n);
//    }
}
