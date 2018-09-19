package com.qf.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class goods implements Serializable{
    private Integer id;
    private String title;
    private String ginfo;
    private Integer gcount;
    private Integer tid;
    private double allprice;
    private double price;
    private String gimage;



}
