package com.liouxb.bi.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liouwb
 */
@Data
@ApiModel(value = "测试数据库连接请求类")
public class TestConnectReq {


    @ApiModelProperty(value = "数据库驱动")
    private String driver;

    @ApiModelProperty(value = "数据库链接")
    private String jbdcUrl;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "数据库密码")
    private String pwd;

    @ApiModelProperty(value = "端口号")
    private int port;

    @ApiModelProperty(value = "服务器地址")
    private String dbServer;

    @ApiModelProperty(value = "数据库类型 1-mysql 2-oracle")
    private String dbType;

    @ApiModelProperty(value = "默认链接数据库")
    private String dataBase;

    @ApiModelProperty(value = "数据库链接名称")
    public String getKey;
}
