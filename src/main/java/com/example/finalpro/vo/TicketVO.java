package com.example.finalpro.vo;

import jakarta.persistence.Transient;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TicketVO {
    private int ticketid;
    private int cateid;
    private String placeid;
    private String ticket_name;
    private int price;
    private String ticket_date;
    private int min_age;
    private int runtime;
    private String cast;
    private String content;
    private String img_fname;
    @Transient
    private MultipartFile uploadFile;
    private String img_fname_main;
    @Transient
    private MultipartFile uploadFile_main;
    private String vid_url;
    private String loc;
    private String lat;
    private String lng;
}
