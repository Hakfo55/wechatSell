package com.nogang.sell.service;

import com.nogang.sell.entity.SellerInfo;

public interface SellerService {
    /**
     * 通过openid查询用户信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
