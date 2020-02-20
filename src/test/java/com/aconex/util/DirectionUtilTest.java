package com.aconex.util;

import com.aconex.model.Direction;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class DirectionUtilTest {

    @Test
    void test_getDirectionAfterLeft_EastBecomeNorth() {
        Direction direction = DirectionUtil.getDirectionAfterLeft(Direction.EAST);
        assertThat(direction, equalTo(Direction.NORTH));
    }

    @Test
    void test_getDirectionAfterLeft_SouthBecomeNorth() {
        Direction direction = DirectionUtil.getDirectionAfterLeft(Direction.SOUTH);
        assertThat(direction, equalTo(Direction.EAST));
    }

    @Test
    void test_getDirectionAfterRight_EastBecomeNorth() {
        Direction direction = DirectionUtil.getDirectionAfterRight(Direction.EAST);
        assertThat(direction, equalTo(Direction.SOUTH));
    }

    @Test
    void test_getDirectionAfterRight_SouthBecomeNorth() {
        Direction direction = DirectionUtil.getDirectionAfterRight(Direction.SOUTH);
        assertThat(direction, equalTo(Direction.WEST));
    }
}