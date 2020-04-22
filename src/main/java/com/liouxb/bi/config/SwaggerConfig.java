package com.liouxb.bi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Value("${enable.swagger}")
    private boolean enableSwagger;

    @Bean
    Docket createRestApi() {
        ResponseMessage requestParameterError = new ResponseMessageBuilder().code(400).message("Request Parameter Error").build();
        ResponseMessage notFound = new ResponseMessageBuilder().code(404).message("Not Found").build();
        ResponseMessage internalServerError = new ResponseMessageBuilder().code(500).message("Internal Server Error").build();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.liouxb.bi.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")

                // 以字符串代替日期格式显示
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)

                // 页面显示信息
                .apiInfo(apiInfo())
                .enable(enableSwagger)

                // 设置全局自定义异常消息返回
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(requestParameterError, notFound, internalServerError))
                .globalResponseMessage(RequestMethod.POST, Arrays.asList(requestParameterError, internalServerError))
                .globalResponseMessage(RequestMethod.PUT, Arrays.asList(requestParameterError, internalServerError))
                .globalResponseMessage(RequestMethod.OPTIONS, Arrays.asList(requestParameterError, internalServerError))
                .globalResponseMessage(RequestMethod.DELETE, Arrays.asList(requestParameterError, internalServerError));
    }


    private ApiInfo apiInfo() {
        return new ApiInfo("DEMO_BI  API",
                "DEMO_BI API 文档",
                "1.0.0",
                "/",
                new Contact("liouwb", "/doc.html", ""),
                "",
                "",
                new ArrayList<>());
    }

}