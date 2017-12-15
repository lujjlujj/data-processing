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

import java.util.List;

/**
 * <code>ListXMLConverter</code>
 * <p>
 * <p>
 * The converter of string format between List to XML
 * </p>
 */
public interface ListXMLConverter {
    /**
     * Converts the data from List to XML
     *
     * @param data    the list of data
     * @param headers the list of headers
     * @return XML String
     * @throws Exception if any error is occurred
     */
    public String convertCSV2XML(List<String> data, List<String> headers) throws Exception;
    /**
     * Converts XML to List
     *
     * @param xml    the XML to the list of data
     * @param headers the list of headers
     * @return the list of data
     * @throws Exception if any error is occurred
     */
    public List<String> convertXML2CSV(String xml, List<String> headers) throws Exception;
}
