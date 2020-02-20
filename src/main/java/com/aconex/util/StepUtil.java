package com.aconex.util;

import com.aconex.model.Step;
import com.aconex.model.StepEnum;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepUtil {

    private static final String VALID_STEP_REGEX = "a [0-9]+|l|r|q";

    private static final String QUIT_STRING = "q";
    private static final String COMMA = ", ";

    public static List<Step> fetchAndPrintSteps() {
        List<Step> steps = receiveStepFromCommandLine();

        System.out.println("");
        System.out.println("The simulation has ended at your request. These are the commands you issued:");
        System.out.println("");

        StringBuilder stepStringBuilder = new StringBuilder();
        for (Step step : steps) {
            if (stepStringBuilder.length() != 0) {
                stepStringBuilder.append(COMMA);
            }

            if (step.getDistance() > 0) {
                stepStringBuilder.append(step.getName() + " " + step.getDistance());
            } else {
                stepStringBuilder.append(step.getName());
            }
        }

        System.out.println(stepStringBuilder.toString());
        System.out.println("");

        return steps;
    }

    private static List<Step> receiveStepFromCommandLine() {
        List<Step> steps = new ArrayList<>();

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("(l)eft, (r)ight, (a)dvance <n>, (q)uit: ");
            String inputString = scan. nextLine();

            Step step = convertToStep(inputString);
            steps.add(step);

            if (QUIT_STRING.equals(inputString)) {
                break;
            }
        }

        return steps;
    }

    static Step convertToStep(String commandString) {
        Pattern pattern = Pattern.compile(VALID_STEP_REGEX);
        Matcher matcher = pattern.matcher(commandString);

        Step step = null;

        while (matcher.find()) {
            String group = matcher.group();

            if (group.length() == 1) {
                step = new Step(group);
                step.setName(StepEnum.parse(group).getName());
            } else {
                String[] advanceStep = group.split(" ");
                String stepCode = advanceStep[0];
                step = new Step(stepCode);
                step.setName(StepEnum.parse(stepCode).getName());
                step.setDistance(Integer.parseInt(advanceStep[1]));
            }
        }

        return step;
    }
}
