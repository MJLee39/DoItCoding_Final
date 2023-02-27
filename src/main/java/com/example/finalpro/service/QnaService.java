package com.example.finalpro.service;

import com.example.finalpro.dao.QnaDAO;
import com.example.finalpro.entity.Qna;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class QnaService {

    @Autowired
    private QnaDAO dao;

    public List<Qna> findAll(){return dao.findAll();}

    public Optional<Qna> findById(int qna_no) {return dao.findById(qna_no);}



//    public void save(Qna q){dao.save(q);}
}
