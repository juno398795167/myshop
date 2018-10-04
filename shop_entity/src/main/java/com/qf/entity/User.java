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
public class User implements Serializable{
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;
    private Integer token;

}
