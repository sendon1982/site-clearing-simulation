package com.aconex.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class FileUtilTest {

    @Test
    void test_readFileByJdk8() throws Exception {
        List<String> resultList = FileUtil.readFileByJdk8("site_test.txt");

        assertThat(resultList, notNullValue());
        assertThat(resultList, hasSize(5));

        assertThat(resultList.get(0), equalTo("ootooooooo"));
        assertThat(resultList.get(1), equalTo("oooooooToo"));
        assertThat(resultList.get(2), equalTo("rrrooooToo"));
        assertThat(resultList.get(3), equalTo("rrrroooooo"));
        assertThat(resultList.get(4), equalTo("rrrrrtoooo"));
    }
}