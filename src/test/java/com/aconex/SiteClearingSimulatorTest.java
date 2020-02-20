package com.aconex;

import com.aconex.model.Block;
import com.aconex.util.BlockUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class SiteClearingSimulatorTest {

    @Test
    void test_gatherAndPrintSiteMapInfo() {
        SiteClearingSimulator simulator = new SiteClearingSimulator();

        Block[][] blocks = BlockUtil.convertToBlockArray(Arrays.asList(
                "roTt",
                "oooo"
        ));

        int total = simulator.gatherAndPrintSiteMapInfo(blocks);
        assertThat(total, equalTo(7));
    }
}