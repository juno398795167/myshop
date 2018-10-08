package com.qf.shop_service_impl.com.qf.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.IAddressDao;
import com.qf.entity.Address;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    IAddressDao iAddressDao;
    @Override
    public List<Address> queryAllByUid(Integer uid) {
        return iAddressDao.queryAllByUid(uid);
    }

    @Override
    public Address addAddress(Address address) {
        return iAddressDao.addAddress(address);
    }
}
