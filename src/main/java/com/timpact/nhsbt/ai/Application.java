/*
 * Copyright (c) 2017 T-IMPACT Development Team. All rights reserved.
 *
 *  This software is only to be used for the purpose for which it has been
 *  provided. No part of it is to be reproduced, disassembled, transmitted,
 *  stored in a retrieval system, nor translated in any human or computer
 *  language in any way for any purposes whatsoever without the prior written
 *  consent of the Sprinter Development Team.
 *  Infringement of copyright is a serious civil and criminal offence, which can
 *  result in heavy fines and payment of substantial damages.
 */
package com.timpact.nhsbt.ai;

import com.timpact.nhsbt.ai.bean.WebDataFormat;
import com.timpact.nhsbt.ai.bean.WebResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * <code>Application</code>
 * <p>
 */
@SpringBootApplication
@EnableAutoConfiguration
@RestController
public class Application {

    /**
     * Gets current user details
     *
     * @param user
     * @return user object
     */
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    /**
     * Trigger the data converison function
     *
     * @param webDataFormat <code>WebDataFormat</code>
     * @return <code>WebResponse</code>
     */
    @RequestMapping(value = "/trigger", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse convert(@RequestBody WebDataFormat webDataFormat) {
        WebResponse response = new WebResponse();
        response.setCode(WebResponse.CODE_SUCCESS);
        return response;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
