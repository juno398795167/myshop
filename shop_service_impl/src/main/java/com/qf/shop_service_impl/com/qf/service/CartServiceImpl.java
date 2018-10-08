package com.qf.shop_service_impl.com.qf.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.qf.ICartDao;
import com.qf.entity.Cart;
import com.qf.entity.User;
import com.qf.entity.goods;
import com.qf.service.ICartService;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    ICartDao iCartDao;

    @Autowired
    IGoodsService goodsService;

    @Override
    public List<Cart> queryAllCartByUid(Integer uid) {
        return iCartDao.queryAllCartByUid(uid);
    }

    @Override
    public Integer addCart(Cart cart) {
        return iCartDao.addCart(cart);
    }

    @Override
    public Integer deleteCartById(Integer id) {
        return iCartDao.deleteCartById(id);
    }

    @Override
    public Integer deleteAllCartByUid(Integer uid) {
        return iCartDao.deleteAllCartByUid(uid);
    }

    @Override
    public List<Cart> getCartLists(User user, String cartToken) {
        List<Cart> cartList = null;
        if (user != null) {
            System.out.println("用户ID："+user.getId());
            cartList = queryAllCartByUid(user.getId());

        } else {
            TypeToken<List<Cart>> typeToken = new TypeToken<List<Cart>>() {
            };
            cartList = new Gson().fromJson(cartToken, typeToken.getType());

        }
        if (cartList != null) {
            for (int i = 0; i < cartList.size(); i++) {
                goods goods = goodsService.queryOneById(cartList.get(i).getGid());

                System.out.println(goods);
                cartList.get(i).setGoods(goods);
            }
        }
        return cartList;
    }

    @Override
    public List<Cart> queryCartBygidanduid(Integer[] gid, Integer uid) {
        List<Cart> carts = iCartDao.queryCartBygidanduid(gid, uid);
        for (int i = 0; i <carts.size() ; i++) {
            goods goods = goodsService.queryOneById(carts.get(i).getGid());
            carts.get(i).setGoods(goods);
        }
        return carts;
    }
}
