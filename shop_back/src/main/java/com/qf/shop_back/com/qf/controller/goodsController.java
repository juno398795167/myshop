package com.qf.shop_back.com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import com.qf.entity.goods;
import com.qf.service.IGoodsService;

import com.qf.utils.httpClientUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/goods")
public class goodsController {
    @Value("${image.path}")
    String path;
    @Reference
    private IGoodsService iGoodsService;
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping(value = "/goodsList")
    public String goodsList(Model model) {

        System.out.println("稍微试试哈");

        List<goods> list = iGoodsService.queryAll();

        model.addAttribute("path",path);
        model.addAttribute("goodsList", list);
        return "goodsList";
    }
    @RequestMapping("/addGoods")
    public String addGoods(MultipartFile file,goods goods) throws IOException {


        StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), "JPG", null);
        String fullPath = storePath.getFullPath();
        System.out.println("图片地址:"+fullPath);
        goods.setGimage(fullPath);
        String path = storePath.getPath();
        System.out.println("实体类goods："+goods);
        com.qf.entity.goods goods1 = iGoodsService.addGoods(goods);
        System.out.println("主键回填："+goods1.getId());
       String s = httpClientUtils.sendJsonPost("http://localhost:8083/solr/addGoods", new Gson().toJson(goods1));
        //System.out.println("打印打印打印："+s);
        return "redirect:/goods/goodsList";
    }

    @RequestMapping("/queryNew")
    @ResponseBody
    public String queryNew(){
        List<goods> list = iGoodsService.queryNew();
        System.out.println(list);
        return "queryNew("+new Gson().toJson(list) +")";
    }
    }
