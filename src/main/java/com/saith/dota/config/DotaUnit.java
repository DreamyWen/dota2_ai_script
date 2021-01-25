package com.saith.dota.config;

import com.beust.jcommander.Parameter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjiawen
 * @version 1.0
 * @date 2021/1/25 2:11 下午
 */
@Data
public class DotaUnit {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = "-input", required = true, description = "dota文件输入路径")
    private String inputPath;

    @Parameter(names = "-output", description = "dota文件输出路径")
    private String outputPath;

    @Parameter(names = "-multiple", description = "ai经验金钱倍数 默认为3")
    private Double multiple = 3D;
}
