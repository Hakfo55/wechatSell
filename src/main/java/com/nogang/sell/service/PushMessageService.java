package com.nogang.sell.service;

import com.nogang.sell.dto.OrderDTO;
import com.nogang.sell.entity.OrderMaster;

/**
 * 推送消息
 */
public interface PushMessageService {
    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);

    /**
     * 新下单
     * @param orderDTO
     */
    void newOrder(OrderDTO orderDTO);
}
