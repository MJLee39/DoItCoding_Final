package com.example.finalpro.dao;

import com.example.finalpro.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeDAO extends JpaRepository<Notice, Integer> {

//    @Modifying
//    @Query(value="insert into notice n(n.notice_no,n.notice_category,n.notice_title,n.notice_content,n.notice_fname) values(seq_notice_no,:#{#n.notice_category},:#{#n.notice_title},:#{#n.notice_content},:#{#n.notice_fname})", nativeQuery = true)
//    @Transactional
//    public void insert(@Param("n") Notice n);
}
