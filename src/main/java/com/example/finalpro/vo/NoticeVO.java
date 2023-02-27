package com.example.finalpro.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class NoticeVO {
    private int notice_no;
    private String notice_title;
    private String notice_content;
    private String notice_hit;
    private Date notice_date;
    private String notice_category;
    private String notice_fname;
    private MultipartFile uploadFile;
}
