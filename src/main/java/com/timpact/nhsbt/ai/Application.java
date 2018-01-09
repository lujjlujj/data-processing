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

import com.csvreader.CsvReader;
import com.timpact.nhsbt.ai.bean.InvalidInputData;
import com.timpact.nhsbt.ai.bean.WebDataFormat;
import com.timpact.nhsbt.ai.bean.WebResponse;
import com.timpact.nhsbt.ai.integration.DefaultOutboundAdapter;
import com.timpact.nhsbt.ai.persistence.DataHandlerImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private DefaultOutboundAdapter adapter;

    private final static String KEY_INPUT_DATA = "inputData";

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
    WebResponse uploadNPredict(@RequestParam(value = "file") MultipartFile file, HttpSession httpSession) {
        List<InvalidInputData> invalidInputDatas = new ArrayList<InvalidInputData>();
        System.out.println(file.getContentType());
        List<List<String>> datas = new ArrayList<List<String>>();
        WebResponse response = new WebResponse();
        try {
            if (!file.getContentType().equals("text/csv")) {
                throw new Exception(String.format("Invalid input file type %s.", file.getContentType()));
            }
            CsvReader reader = new CsvReader(file.getInputStream(), ',', Charset.forName("UTF-8"));
            List<String> headers = new ArrayList<String>();
            if (reader.readRecord()) {
                headers = Arrays.asList(reader.getValues());
            }

            if (headers.size() > 0) {
                while (reader.readRecord()) {
                    List<String> values = Arrays.asList(reader.getValues());
                    List<String> columnNames = new ArrayList<String>();
                    String id = values.get(0);
                    for (int i = 0; i < values.size() && i < headers.size(); i++) {
                        if (StringUtils.isBlank(values.get(i))) {
                            columnNames.add(headers.get(i));
                        }
                    }
                    if (columnNames.size() > 0) {
                        InvalidInputData data = new InvalidInputData();
                        data.setId(id);
                        data.setColumnNames(String.join(",", columnNames));
                        invalidInputDatas.add(data);
                    } else {
                        datas.add(values);
                    }
                }
            }
        } catch (Exception e) {
            log.error(String.format("Invalid data input for %s.", file.getName()), e);
            response.setCode(WebResponse.CODE_INVALID_INPUT);
            return response;
        }
        httpSession.setAttribute(KEY_INPUT_DATA, datas);
        if (invalidInputDatas.size() > 0) {
            response.setCode(WebResponse.CODE_DATA_ISSUE);
            response.setReturnValue(invalidInputDatas);
        } else {
            response.setReturnValue(adapter.predict(datas));
            response.setCode(WebResponse.CODE_SUCCESS);
        }
        return response;
    }

    /**
     * Confirm prediction
     *
     * @return <code>WebResponse</code>
     */
    @RequestMapping(value = "/confirmPrediction", method = RequestMethod.POST)
    public @ResponseBody
    WebResponse confirmPrediction(HttpSession httpSession) {
        WebResponse response = new WebResponse();
        List<List<String>> datas = (List<List<String>>) httpSession.getAttribute(KEY_INPUT_DATA);
        if (datas == null) {
            response.setCode(WebResponse.CODE_INVALID_INPUT);
        } else {
            response.setCode(WebResponse.CODE_SUCCESS);
            response.setReturnValue(adapter.predict(datas));
        }
        return response;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
