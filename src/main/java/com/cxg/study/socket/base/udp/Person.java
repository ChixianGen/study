package com.cxg.study.socket.base.udp;   // Administrator 于 2019/8/2 创建;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
public class Person implements Serializable {
    private int id;
    private String name;
    private double salary;
}
