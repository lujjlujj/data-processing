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
 * <code>PredictionResult</code>
 *
 * <p>
 * The prediction result bean
 * </p>
 */
public class PredictionResult {

    private String id;

    private String arrestTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArrestTime() {
        return arrestTime;
    }

    public void setArrestTime(String arrestTime) {
        this.arrestTime = arrestTime;
    }
}
