package com.liouxb.bi.controller;

import com.liouxb.bi.entity.req.ExecSqlReq;
import com.liouxb.bi.entity.req.TestConnectReq;
import com.liouxb.bi.entity.resp.BasicResp;
import com.liouxb.bi.entity.resp.ResultSetResp;
import com.liouxb.bi.exception.RequestParamsValidException;
import com.liouxb.bi.service.JdbcService;
import com.liouxb.bi.utils.RequestParamsValidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author liouwb
 */
@RestController
@RequestMapping(value = "test", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "测试")
public class TestController {
    @Autowired
    private JdbcService jdbcService;

    /**
     * test get请求
     */
    @ApiOperation(value = "testGet")
    @GetMapping("testGet")
    public BasicResp testGet() {

        BasicResp resp = new BasicResp();
        resp.setStatus("200");
        resp.setRespCode("0000");
        resp.setRespMsg("请求成功");
        resp.setRespData("hello world");
        return resp;
    }

    /**
     * 测试连接
     */
    @ApiOperation(value = "测试连接")
    @PostMapping("testConnet")
    public BasicResp testConnet(@RequestBody @Valid TestConnectReq req, BindingResult result) throws RequestParamsValidException {
        RequestParamsValidUtil.validParams(result);

        return jdbcService.testConnect(req);
    }

    /**
     * 添加数据库链接
     */
    @ApiOperation(value = "添加数据库链接")
    @PostMapping("addConnect")
    public BasicResp addConnect(@RequestBody @Valid TestConnectReq req, BindingResult result) throws RequestParamsValidException {
        RequestParamsValidUtil.validParams(result);

        return jdbcService.addConnection(req);
    }

    /**
     * 执行sql
     */
    @ApiOperation(value = "执行sql")
    @PostMapping("execSql")
    public BasicResp<ResultSetResp> execSql(@RequestBody @Valid ExecSqlReq req, BindingResult result) throws Exception {
        RequestParamsValidUtil.validParams(result);

        return jdbcService.execSql(req);
    }

    /**
     * 关闭数据库连接
     */
    @ApiOperation(value = "关闭数据库连接")
    @GetMapping("closeConnect/{key}")
    public BasicResp<ResultSetResp> closeConnect(@PathVariable String key) {

        return jdbcService.closeConnect(key);
    }
}
