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
package com.timpact.nhsbt.ai.persistence;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>DataHandlerImpl</code>
 * <p>
 * <p>
 * Handles the data in database
 * </p>
 */
@Repository
public class DataHandlerImpl {

    @Value("${sql.raw.select.all}")
    private String SQL_QUERY_ALL_RECORDS;

    @Value("${sql.column.id}")
    private String COLUMN_ID;

    @Value("${sql.column.property}")
    private String COLUMN_PROPERTY;

    @Value("${sql.dense.insert}")
    private String SQL_INSERT_DENSE;

    @Value("${sql.missing.insert}")
    private String SQL_INSERT_MISSING;

    @Value("${sql.missing.delete}")
    private String SQL_DELETE_MISSING;

    @Value("${sql.dense.delete}")
    private String SQL_DELETE_DENSE;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Process Raw Data to destination table by eliminating the empty record;
     *
     * @throws Exception if any error occurs
     */
    public void processData() throws Exception {
        jdbcTemplate.execute(SQL_DELETE_MISSING);
        jdbcTemplate.execute(SQL_DELETE_DENSE);
        String[] propertyNames = COLUMN_PROPERTY.split(",");
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SQL_QUERY_ALL_RECORDS);
        while (rowSet.next()) {
            List<String> values = new ArrayList<String>();
            List<String> missingColumns = new ArrayList<String>();
            String id = rowSet.getString(COLUMN_ID);
            values.add(id);
            for (String propertyName : propertyNames) {
                String value = rowSet.getString(propertyName);
                if (StringUtils.isBlank(value)) {
                    missingColumns.add(propertyName);
                }
                values.add(value);
            }
            int count = 0;
            if (missingColumns.size() > 0) {
                count = jdbcTemplate.update(SQL_INSERT_MISSING, id, String.join(",", missingColumns));
            } else {
                count = jdbcTemplate.update(SQL_INSERT_DENSE, values.toArray(new String[values.size()]));
            }
            if (count != 1) {
                throw new Exception(String.format("Failed to replicate the data {id:%s}.", id));
            }
        }
    }
}