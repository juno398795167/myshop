package com.qf.service;

import com.qf.entity.Address;

import java.util.List;

public interface IAddressService {
    List<Address> queryAllByUid(Integer uid);
    Address addAddress(Address address);
}
