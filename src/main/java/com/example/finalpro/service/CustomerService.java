package com.example.finalpro.service;

import com.example.finalpro.dao.CustomerDAO;
import com.example.finalpro.db.DBManager;
import com.example.finalpro.entity.Customer;
import com.example.finalpro.vo.CustomerVO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerDAO dao;

    public List<Customer> findAll(){return dao.findAll();}

    public Optional<Customer> findCustomerByCustid(String custid){
        return dao.findById(custid);
    }

    // 고객 정보 수정
    public int updateCustomer(CustomerVO customer){
        String birth = customer.getBirth();
        String[] list_birth = birth.split("\\s");
        birth = list_birth[0];
        System.out.println(birth);
        customer.setBirth(birth);

        return DBManager.updateCustomer(customer);
    }

    // 고객 삭제
    public void deleteCustomer(String custid){
        dao.deleteById(custid);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("사용자 로그인 처리");
        System.out.println("username:" + username);
        Optional<Customer> obj = dao.findById(username);
        UserDetails user = null;
        if (obj.isPresent()) {
            Customer c = obj.get();
            user = User.builder().username(username).password(c.getPwd()).roles(c.getRole()).build();
            System.out.println(user);
        } else {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

}
