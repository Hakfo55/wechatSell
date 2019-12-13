package com.nogang.sell.service;

import com.nogang.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface  OrderService {
    /** 创建订单 */
    OrderDTO create(OrderDTO orderDto);
    /** 查询单个订单 */
    OrderDTO findOne(String orderId);
    /** 买家端查询订单列表 */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);
    /** 取消订单 */
    OrderDTO cancel(OrderDTO orderDto);
    /** 完结订单 */
    OrderDTO finish(OrderDTO orderDto);
    /** 支付订单 */
    OrderDTO pay(OrderDTO orderDto);

    /** 卖家端查询订单列表 */
    Page<OrderDTO> findList(Pageable pageable);
}
