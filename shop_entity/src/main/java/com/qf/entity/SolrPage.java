package com.qf.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class SolrPage<T> implements Serializable{

    private Integer pageNow=1;
    private Integer pageSize=4;
    private Integer pageCount;
    private Integer pageTotal;
    private List<T> data;
}
