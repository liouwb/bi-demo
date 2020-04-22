package com.liouxb.bi.entity.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liouwb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = " 返回基础类")
public class BasicResp<T> {
    @ApiModelProperty(value = "请求状态")
    private String status;

    @ApiModelProperty(value = "返回code 0000-成功")
    private String respCode;

    @ApiModelProperty(value = "返回信息")
    private String respMsg;

    @ApiModelProperty(value = "返回数据")
    private T respData;


}
