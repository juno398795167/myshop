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
public class UserStatus<T> implements Serializable {
    private Integer core;
    private String  status;
    private T data;

    public UserStatus(Integer core, String status, T data) {
        this.core = core;
        this.status = status;
        this.data = data;
    }
}
