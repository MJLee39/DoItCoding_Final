package com.example.finalpro.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class QnaVO {
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
    private String qna_open;
    private MultipartFile uploadFile;
}
