package com.liouxb.bi.utils;

import com.liouxb.bi.exception.RequestParamsValidException;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * 请求参数校验
 *
 * @author liouwb
 */
public class RequestParamsValidUtil {
    public static void validParams(BindingResult result) throws RequestParamsValidException {
        if (result.hasErrors()) {
            throw new RequestParamsValidException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }
}
