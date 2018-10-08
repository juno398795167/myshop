package com.qf;

import com.qf.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICartDao {
    List<Cart> queryAllCartByUid(Integer uid);
    Integer addCart(Cart cart);
    Integer deleteCartById(Integer id);
    Integer deleteAllCartByUid(Integer uid);
    List<Cart> queryCartBygidanduid(@Param("gid") Integer[] gid, @Param("uid") Integer uid);
    List<Cart> queryByCid(@Param("cids") Integer[] cids);

}
