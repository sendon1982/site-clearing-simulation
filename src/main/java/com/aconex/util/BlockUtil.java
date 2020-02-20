package com.aconex.util;

import com.aconex.model.Block;
import com.aconex.model.LandType;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockUtil {

    /**
     * Convert site map into a 2D array of Block
     *
     * @param input
     * @return
     */
    public static Block[][] convertToBlockArray(List<String> input) {
        if (input == null || input.size() == 0) {
            return null;
        }

        int row = input.size();
        int col = input.iterator().next().length();

        Block[][] results = new Block[row][col];

        int i = 0;

        for (String line : input) {
            int j = 0;
            for (String code : line.split("")) {
                Block block = new Block();
                block.setLandType(LandType.parse(code));

                results[i][j++] = block;
            }

            i++;
        }

        return results;
    }
}
