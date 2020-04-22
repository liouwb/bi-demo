package com.liouxb.bi.service;

import com.liouxb.bi.entity.req.ExecSqlReq;
import com.liouxb.bi.entity.req.TestConnectReq;
import com.liouxb.bi.entity.resp.BasicResp;
import com.liouxb.bi.entity.resp.ResultSetResp;

import java.sql.SQLException;

/**
 * @author liouwb
 */
public interface JdbcService {
    BasicResp testConnect(TestConnectReq req);

    BasicResp addConnection(TestConnectReq req);

    BasicResp execSql(ExecSqlReq req) throws SQLException;

    BasicResp<ResultSetResp> closeConnect(String key);
}
