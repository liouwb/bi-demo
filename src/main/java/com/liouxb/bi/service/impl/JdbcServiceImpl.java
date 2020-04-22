package com.liouxb.bi.service.impl;

import com.liouxb.bi.db.DBPool;
import com.liouxb.bi.db.SqlCommandUtil;
import com.liouxb.bi.entity.req.ExecSqlReq;
import com.liouxb.bi.entity.req.TestConnectReq;
import com.liouxb.bi.entity.resp.BasicResp;
import com.liouxb.bi.entity.resp.ResultSetResp;
import com.liouxb.bi.service.JdbcService;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author liouwb
 */
@Service
public class JdbcServiceImpl implements JdbcService {
    @Autowired
    private DBPool dbPool;
    @Autowired
    private SqlCommandUtil sqlCommandUtil;

    /**
     * 测试数据库连接
     *
     * @param req
     * @return
     */
    @Override
    public BasicResp testConnect(TestConnectReq req) {
        // 1、获取ComboPooledDataSource
        ComboPooledDataSource pool = dbPool.getComboPooledDataSource(req);

        // 2、测试连接
        boolean success = dbPool.testConnection(pool);

        BasicResp resp = new BasicResp();
        resp.setStatus("200");
        if (success) {
            resp.setRespCode("0000");
            resp.setRespMsg("请求成功");
            resp.setRespData("测试通过");
        } else {
            resp.setRespCode("9999");
            resp.setRespMsg("请求成功");
            resp.setRespData(" 测试失败");
        }

        return resp;
    }

    /**
     * 添加数据库连接
     *
     * @param req
     * @return
     */
    @Override
    public BasicResp addConnection(TestConnectReq req) {
        BasicResp resp = new BasicResp();
        resp.setStatus("200");

        // 1、获取ComboPooledDataSource
        ComboPooledDataSource pool = dbPool.getComboPooledDataSource(req);

        // 2、测试连接
        boolean success = dbPool.testConnection(pool);

        // 3、测试通过的话存储数据库连接
        if (success) {
            if (!DBPool.dbMap.containsKey(req.getKey)) {
                DBPool.dbMap.put(req.getKey, pool);
            } else {
                resp.setRespCode("9999");
                resp.setRespMsg("请求成功");
                resp.setRespData(" 已添加过该数据库");
            }
        }

        resp.setRespCode("0000");
        resp.setRespMsg("请求成功");
        resp.setRespData("添加数据库成功");

        return resp;
    }

    /**
     * 执行sql
     *
     * @param req
     * @return
     */
    @Override
    public BasicResp execSql(ExecSqlReq req) throws SQLException {
        BasicResp resp = new BasicResp();
        resp.setStatus("200");


        // 1、获取数据源
        ComboPooledDataSource ds = (ComboPooledDataSource) dbPool.getDataSource(req.getKey());
        if (ds == null) {
            resp.setRespCode("9999");
            resp.setRespMsg("数据源不存在");
            resp.setRespData("null");

            return resp;
        }

        // 2、获取数据库连接
        Connection con = ds.getConnection();

        // 3、执行sql
        ResultSetResp rsp = sqlCommandUtil.getResultSet(con, req.getSqlCommand());

        resp.setRespCode("0000");
        resp.setRespMsg("执行sql成功");
        resp.setRespData(rsp);

        return resp;
    }

    @Override
    public BasicResp<ResultSetResp> closeConnect(String key) {
        dbPool.destroy(key);

        BasicResp resp = new BasicResp();
        resp.setStatus("200");
        resp.setRespCode("0000");
        resp.setRespMsg("关闭连接成功");
        resp.setRespData("关闭连接成功");

        return resp;
    }
}
