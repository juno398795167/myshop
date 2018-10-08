package com.qf;

import com.qf.entity.Address;

import java.util.List;

public interface IAddressDao {
    List<Address> queryAllByUid(Integer uid);
    Address addAddress(Address address);
    Address queryByid(Integer id);

}
