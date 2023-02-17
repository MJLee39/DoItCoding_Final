package com.example.finalpro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "qna")
public class Qna {
    @Id
    private int qna_no;
    private String custid;
    private int ticketid;
    private String qna_title;
    private String qna_content;
    private int qna_hit;
    private Date qna_date;
    private String qna_category;
    private String qna_fname;
    private String qna_answer;
    private char qna_open;
}
