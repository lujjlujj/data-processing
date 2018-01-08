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

import com.timpact.nhsbt.ai.bean.InvalidInputData;
import com.timpact.nhsbt.ai.bean.PredictionResult;
import com.timpact.nhsbt.ai.bean.WebDataFormat;
import com.timpact.nhsbt.ai.bean.WebResponse;
import com.timpact.nhsbt.ai.persistence.DataHandlerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
     * logger
     */
    private Log log = LogFactory.getLog(Application.class);

    @Autowired
    private DataHandlerImpl dataHandler;

    /**
     * Trigger the persistence converison function
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

    /**
     * Trigger the data processing function
     *
     * @return <code>WebResponse</code>
     */
    @RequestMapping(value = "/replicate", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse processData() {
        WebResponse response = new WebResponse();
        response.setCode(WebResponse.CODE_SUCCESS);
        return response;
    }

    /**
     * Trigger file upload
     *
     * @return <code>WebResponse</code>
     */
    @RequestMapping(value = "/predict", method = RequestMethod.POST)
    public @ResponseBody
    WebResponse uploadNPredict(@RequestParam(value = "file") MultipartFile file) throws IOException {
        log.info("Received file:" + file.getName());
        WebResponse response = new WebResponse();
        response.setCode(WebResponse.CODE_INVALID_INPUT);
        List<InvalidInputData> datas = new ArrayList<InvalidInputData>();
        InvalidInputData data = new InvalidInputData();
        data.setId("1");
        data.setColumnNames("Var1, Var2, Var3, Var3");
        datas.add(data);
        response.setReturnValue(datas);
        return response;
    }

    /**
     * Confirm prediction
     *
     * @return <code>WebResponse</code>
     */
    @RequestMapping(value = "/confirmPrediction", method = RequestMethod.POST)
    public @ResponseBody
    WebResponse confirmPrediction() throws IOException {
        WebResponse response = new WebResponse();
        response.setCode(WebResponse.CODE_SUCCESS);
        List<PredictionResult> datas = new ArrayList<PredictionResult>();
        PredictionResult data = new PredictionResult();
        data.setId("2");
        data.setArrestTime("5");
        datas.add(data);
        response.setReturnValue(datas);
        return response;
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
