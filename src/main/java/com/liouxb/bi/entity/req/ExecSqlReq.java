package com.liouxb.bi.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liouwb
 */
@Data
@ApiModel(value = "执行sql请求类")
public class ExecSqlReq {
    @ApiModelProperty(value = "数据库标识")
    private String key;

    @ApiModelProperty(value = "登录账号")
    private String userName;

    @ApiModelProperty(value = "执行sql语句")
    private String sqlCommand;
}
