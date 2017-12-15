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
 * <code>WebDataFormat</code>
 *
 * <p>
 * The web input data format bean
 * </p>
 */
public class WebDataFormat {
    /**
     * Type of data format like json, xml, csv.
     */
    private String type;

    /**
     * The getter of type
     *
     * @return type value
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type value
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}
