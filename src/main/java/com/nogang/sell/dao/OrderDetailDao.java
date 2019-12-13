package com.nogang.sell.dao;

import com.nogang.sell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDao extends JpaRepository<OrderDetail,String> {
    /**
     * 通过订单ip查找订单详情列表
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
