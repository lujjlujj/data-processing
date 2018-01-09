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
package com.timpact.nhsbt.ai.bean;

/**
 * <code>WebResponse</code>
 * <p>
 * <p>
 * General Web Response Object
 * </p>
 */
public class WebResponse {

    public static final String CODE_ERROR = "1";

    public static final String CODE_INVALID_INPUT = "2";

    public static final String CODE_SUCCESS = "0";

    public static final String CODE_DATA_ISSUE = "3";
    /**
     * The response code, please refer to CODE_ERROR | CODE_SUCCESS and so on.
     */
    private String code;
    /**
     * The description of response code
     */
    private String description;
    /**
     * The return object of response
     */
    private Object returnValue;

    /**
     * Gets code value
     *
     * @return code value
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code value
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets description value
     *
     * @return description value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description value
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets return value
     *
     * @return return value
     */
    public Object getReturnValue() {
        return returnValue;
    }

    /**
     * Sets return value
     *
     * @param returnValue
     */
    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }
}
