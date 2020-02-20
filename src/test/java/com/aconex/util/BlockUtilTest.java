package com.aconex.util;

import com.aconex.model.Block;
import com.aconex.model.LandType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class BlockUtilTest {

    @Test
    void test_convertToBlockArray_null() {
        Block[][] blocks = BlockUtil.convertToBlockArray(null);
        assertThat(blocks, nullValue());
    }

    @Test
    void test_convertToBlockArray() {
        Block[][] blocks = BlockUtil.convertToBlockArray(Arrays.asList(
            "roTt",
            "oooo"
        ));

        assertThat(blocks.length, equalTo(2));
        assertThat(blocks[0][0].getLandType().getCode(), equalTo(LandType.ROCKY.getCode()));
        assertThat(blocks[0][1].getLandType().getCode(), equalTo(LandType.PLAIN.getCode()));
        assertThat(blocks[0][2].getLandType().getCode(), equalTo(LandType.RESERVED.getCode()));
        assertThat(blocks[0][3].getLandType().getCode(), equalTo(LandType.TREE.getCode()));

        assertThat(blocks[1][0].getLandType().getCode(), equalTo(LandType.PLAIN.getCode()));
        assertThat(blocks[1][1].getLandType().getCode(), equalTo(LandType.PLAIN.getCode()));
        assertThat(blocks[1][2].getLandType().getCode(), equalTo(LandType.PLAIN.getCode()));
        assertThat(blocks[1][3].getLandType().getCode(), equalTo(LandType.PLAIN.getCode()));
    }
}