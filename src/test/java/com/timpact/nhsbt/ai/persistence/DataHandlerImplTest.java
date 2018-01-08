package com.timpact.nhsbt.ai.persistence;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataHandlerImplTest {

    private static final String SQL_SELECT_MISSING = "select count(*) from student_missing";

    private static final String SQL_SELECT_DENSE = "select count(*) from student_dense";

    @Autowired
    private DataHandlerImpl dataHandler;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void replicateData() throws Exception{
        dataHandler.processData();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SQL_SELECT_MISSING);
        if (!rowSet.next()) {
            TestCase.fail();
        }
        int count = rowSet.getInt(1);
        TestCase.assertEquals("The number of record in missing table is invalid.", 2, count);
        rowSet = jdbcTemplate.queryForRowSet(SQL_SELECT_DENSE);
        if (!rowSet.next()) {
            TestCase.fail();
        }
        count = rowSet.getInt(1);
        TestCase.assertEquals("The number of record in dense table is invalid.", 1, count);
    }
}
