package com.timpact.nhsbt.ai;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class ApplicationTest {

    @Test
    public void contextLoads() {
    }

    @Test
    public void alwaysTrue() {
        TestCase.assertTrue(true);
        TestCase.assertFalse(false);
        Integer[] arrays = new Integer[] {2, 5, 3};
        List<Integer> list = Arrays.asList(arrays);
        list.stream().forEach(s -> System.out.println(s));
    }
}

