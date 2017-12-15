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

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
/**
 * <code>ListXMLConverterImpl</code>
 * <p>
 * <p>
 * The implementation of <code>ListXMLConverter</code>
 * </p>
 */
public class ListXMLConverterImpl implements ListXMLConverter {


    @Override
    public String convertCSV2XML(List<String> data, List<String> headers) throws Exception {
        if (data.size() < headers.size()) {
            throw new Exception("The size of data and headers is not the same.");
        }
        Document document = new Document();
        Element root = new Element("root");
        for (int index = 0; index < headers.size(); index++) {
            Element e = new Element(headers.get(index).replaceAll(" ", ""));
            e.addContent(data.get(index));
            root.addContent(e);
        }
        document.addContent(root);
        Format format = Format.getRawFormat();
        XMLOutputter out = new XMLOutputter(format);
        return out.outputString(document).replaceAll("(\r\n|\r|\n|\n\r)","");
    }

    @Override
    public List<String> convertXML2CSV(String xml, List<String> headers) throws Exception {
        List<String> result = new ArrayList<String>();
        for (int index = 0; index < headers.size(); index++) {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new StringReader(xml));
            Element element = doc.getRootElement();
            String text = element.getChildText(headers.get(index).replaceAll(" ",""));
            result.add(text);
        }
        return result;
    }
}
