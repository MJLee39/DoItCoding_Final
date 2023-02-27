package com.example.finalpro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
    //JPA는 테이블 속성명과 엔티티의 변수명을 똑같이해야 인식함.
    @Id
    private String custid;
    private String pwd;
    private String name;
    private String birth;
    private String email;
    private String phone;
    private String gender;
    private String role;
    private int cateid;
    private String addr;
}
