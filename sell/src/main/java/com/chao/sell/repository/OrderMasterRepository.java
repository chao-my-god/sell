package com.chao.sell.repository;

import com.chao.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    //对查询结果分页
    Page<OrderMaster> findByCustomerOpenid(String customerOpenid, Pageable pageable);
}
