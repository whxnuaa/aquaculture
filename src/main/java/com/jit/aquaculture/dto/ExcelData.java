package com.jit.aquaculture.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@Data
public class ExcelData<T> implements Serializable {
    // 表头
    private List<String> titles;
    // 数据
    private List<List<T>> rows;
    // 页签名称
    private String name;

}
