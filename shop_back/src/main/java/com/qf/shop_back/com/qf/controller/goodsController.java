package com.qf.shop_back.com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        return "redirect:/goods/goodsList";
    }



  /*  public static void main(String[] args) {
       *//* Integer[] a = {3, 5, 7, 9};
        Integer[] b = {6, 10, 16};

        Integer[] c = new Integer[a.length + b.length];
        for (int x = 0, y = 0, z = 0; z < c.length; z++) {

            if ((y == b.length) || (x < a.length && a[x] < b[y])) {
                c[z] = a[x];
                x++;


            } else if ((x == a.length) || (y < b.length && a[x] >= b[y])) {
                c[z] = b[y];
                y++;

            }


            System.out.println(Arrays.toString(c));
        }


    }*//*
        Integer[] aa = new Integer[200000];
        Integer[] bb = new Integer[200000];
        for (Integer i = 0; i < aa.length; i++) {
            aa[i] = (int) (Math.random() * 100000);

        }
        sort(aa,bb,0,aa.length-1);
        System.out.println(Arrays.toString(bb));

    }
    public static void sort (Integer[]a, Integer[]b, Integer begin, Integer end){

        if (begin == end) {
            return;
        }
        Integer mid = (end - begin) / 2;
        sort(a, b, begin, mid);
        sort(a, b, mid + 1, end);


        for (Integer x = 0, y = 0, z = 0; ; ) {
            if ((y == end + 1) || (x <= mid && (a[x] < a[y]))) {
                b[z] = a[x];
                x++;
            } else if ((x == mid) || (y < end && (a[x] >= a[y]))) {
                b[z] = a[y];
                y++;
            }
        }

        for(Integer z = 0,k = begin;k<=end;z++,k++){
            a[k]=b[z];
        }

    }*/
    }
