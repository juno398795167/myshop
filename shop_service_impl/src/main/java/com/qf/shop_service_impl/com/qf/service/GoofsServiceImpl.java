package com.qf.shop_service_impl.com.qf.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.qf.IGoodsDao;
import com.qf.entity.goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
@Service
public class GoofsServiceImpl implements IGoodsService {
    @Autowired
    IGoodsDao iGoodsDao;
    @Override
    public List<goods> queryAll() {
        System.out.println("实现类被调用了");
        List<goods> list = iGoodsDao.queryAll();
        return list;
    }

    @Override
    public goods addGoods(goods goods) {
        iGoodsDao.addGoods(goods);
        return goods;
    }

    @Override
    public List<goods> queryNew() {

        return iGoodsDao.queryNew();
    }
}
