package com.qf.service;

import com.qf.entity.Cart;
import com.qf.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICartService {
    List<Cart> queryAllCartByUid(Integer uid);
    Integer addCart(Cart cart);
    Integer deleteCartById(Integer id);
    Integer deleteAllCartByUid(Integer uid);
    List<Cart> getCartLists(User user,String cartToken);
    List<Cart> queryCartBygidanduid(Integer[] gid, Integer uid);
}
