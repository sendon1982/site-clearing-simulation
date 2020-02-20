package com.aconex.util;

import com.aconex.model.Step;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

class StepUtilTest {

    @Test
    void test_convertToStep_invalid() {
        Step step = StepUtil.convertToStep("aa");
        assertThat(step, nullValue());
    }

    @Test
    void test_convertToStep_advance() {
        Step step = StepUtil.convertToStep("a 4");
        assertThat(step.getCode(), equalTo("a"));
        assertThat(step.getDistance(), equalTo(4));
    }

    @Test
    void test_convertToStep_left() {
        Step step = StepUtil.convertToStep("l");
        assertThat(step.getCode(), equalTo("l"));
        assertThat(step.getDistance(), equalTo(0));
    }

    @Test
    void test_convertToStep_right() {
        Step step = StepUtil.convertToStep("r");
        assertThat(step.getCode(), equalTo("r"));
        assertThat(step.getDistance(), equalTo(0));
    }

    @Test
    void test_convertToStep_quit() {
        Step step = StepUtil.convertToStep("q");
        assertThat(step.getCode(), equalTo("q"));
        assertThat(step.getDistance(), equalTo(0));
    }
}