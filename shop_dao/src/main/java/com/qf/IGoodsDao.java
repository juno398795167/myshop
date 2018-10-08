package com.qf;

import com.qf.entity.goods;

import java.util.List;

public interface IGoodsDao {
    List<goods> queryAll();

    Integer addGoods(goods goods);

    List<goods> queryNew();

    goods queryOneById(Integer id);
}
