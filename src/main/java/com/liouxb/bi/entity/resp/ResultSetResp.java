package com.liouxb.bi.entity.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liouwb
 */
@Data
@ApiModel(value = " 数据库执行结果集返回类")
public class ResultSetResp {
    @ApiModelProperty(value = "数据库结果集")
    List<Map<String, Object>> list = new ArrayList<>();
}
