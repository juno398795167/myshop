package com.qf.service;

import com.qf.entity.goods;

import java.util.List;

public interface IGoodsService {
    public List<goods> queryAll();

    goods addGoods(goods goods);

    List<goods> queryNew();

    goods queryOneById(Integer id);
}
