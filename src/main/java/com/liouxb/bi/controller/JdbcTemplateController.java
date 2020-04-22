package com.liouxb.bi.controller;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liouwb
 */
@RestController
@RequestMapping(value = "jdbcTemplate", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "数据库操作")
public class JdbcTemplateController {


}
