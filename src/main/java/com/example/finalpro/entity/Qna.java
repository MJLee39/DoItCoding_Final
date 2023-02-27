package com.example.finalpro.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Entity
@Data
@Table(name = "qna")
public class Qna {
    //JPA는 테이블 속성명과 엔티티의 변수명을 똑같이해야 인식함.
    @Id
    private int qna_no;
    @ManyToOne
    @JoinColumn(name = "ticketid", insertable = true, updatable = true)
    private Ticket ticket;
    @ManyToOne
    @JoinColumn(name = "custid", insertable = true, updatable = true)
    private Customer customer;
    private String qna_title;
    private String qna_content;
    private int qna_hit;
    private Date qna_date;
    private String qna_category;
    private String qna_fname;
    private String qna_answer;
    private String qna_open;
    @Transient
    private MultipartFile uploadFile;
}
