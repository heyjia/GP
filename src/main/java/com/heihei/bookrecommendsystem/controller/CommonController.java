package com.heihei.bookrecommendsystem.controller;

import com.heihei.bookrecommendsystem.result.Result;
import com.heihei.bookrecommendsystem.util.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    Logger logger = LoggerFactory.getLogger(CommonController.class);
    @RequestMapping(value = "/getPKey")
    public Result<String> getPublicKey(){
        String publicKey = RSAUtil.PUBLIC_KEY;
        logger.info("获取的公钥为：" + publicKey);
        return Result.success(publicKey);
    }
}
