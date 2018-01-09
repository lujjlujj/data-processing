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
package com.timpact.nhsbt.ai.integration;

import com.timpact.nhsbt.ai.bean.PredictionResult;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>DefaultOutboundAdapter</code>
 * <p>
 * <p>
 * Integration service for outbound
 * </p>
 */
@Repository
public class DefaultOutboundAdapter {
    /**
     * Triggers a request to prediction service for prediction result.
     *
     * @param values
     * @return
     */
    public List<PredictionResult> predict(List<List<String>> values) {
        List<PredictionResult> datas = new ArrayList<PredictionResult>();
        PredictionResult data = new PredictionResult();
        data.setId("2");
        data.setArrestTime("5");
        datas.add(data);
        return datas;
    }
}
