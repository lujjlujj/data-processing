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
package com.timpact.nhsbt.ai.converter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>ListJsonConverterImpl</code>
 * <p>
 * <p>
 * The implementation of <code>ListJsonConverter</code>
 * </p>
 */
public class ListJsonConverterImpl implements ListJsonConverter {


    @Override
    public String convertCSV2JSON(List<String> data, List<String> headers) throws Exception {
        if (data.size() < headers.size()) {
            throw new Exception("The size of persistence and headers is not the same." + data.get(0));
        }
        JSONObject result = new JSONObject();
        for (int index = 0; index < headers.size(); index++) {
            result.put(headers.get(index), data.get(index));
        }
        return result.toString();
    }

    @Override
    public List<String> convertJSON2CSV(String json, List<String> headers) throws Exception {
        List<String> result = new ArrayList<String>();
        JSONObject data = new JSONObject(json);
        for (int index = 0; index < headers.size(); index++) {

            result.add(data.getString(headers.get(index)));
        }
        return result;
    }
}
