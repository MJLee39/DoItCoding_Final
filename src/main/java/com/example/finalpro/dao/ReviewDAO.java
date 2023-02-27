package com.example.finalpro.dao;

import com.example.finalpro.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewDAO extends JpaRepository<Review, Integer> {


}