package com.cxg.study.socket.netty.serial;   // Administrator 于 2019/8/7 创建;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
public class SerialObj implements Serializable {
    private String name;
    private Integer salary;
}
