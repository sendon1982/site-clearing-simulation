package com.aconex;

import com.aconex.model.Block;
import com.aconex.model.Step;
import com.aconex.util.BlockUtil;
import com.aconex.util.FileUtil;
import com.aconex.util.StepUtil;

import java.util.List;

public class MainApp {

    public static void main(String[] args) throws Exception {
//        if (args.length < 1) {
//            throw  new IllegalArgumentException("Input param is invalid!");
//        }
//
//        String filePath = args[0];

        String filePath = "E:/Project/site-clearing-simulation/src/test/resources/site_test.txt";
        List<String> inputList = FileUtil.readAbsolutePathFileByJdk8(filePath);

        Block[][] blocks = BlockUtil.convertToBlockArray(inputList);

        SiteClearingSimulator simulator = new SiteClearingSimulator();

        int totalSquareQuantity = simulator.getTotalSquareQuantityAndPrintWelcomeMessage(blocks);
        List<Step> steps = StepUtil.fetchAndPrintSteps();
        simulator.execute(blocks, steps, totalSquareQuantity);
    }
}
