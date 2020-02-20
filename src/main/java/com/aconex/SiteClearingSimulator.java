package com.aconex;

import com.aconex.model.*;
import com.aconex.util.DirectionUtil;

import java.util.List;
import java.util.stream.Collectors;

public class SiteClearingSimulator {

    public int getTotalSquareQuantityAndPrintWelcomeMessage(Block[][] blocks) {
        System.out.println("Welcome to the Aconex site clearing simulator. This is a map of the site:");
        System.out.println("");

        int totalSquareQuantity = gatherAndPrintSiteMapInfo(blocks);

        System.out.println("");
        System.out.println("The bulldozer is currently located at the Northern edge of the site, immediately to the West of the site, and facing East.");
        System.out.println("");

        return totalSquareQuantity;
    }

    public int gatherAndPrintSiteMapInfo(Block[][] blocks) {
        int row = blocks.length;
        int col = blocks[0].length;

        int totalSquareQuantity = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (blocks[i][j].getLandType() != LandType.RESERVED) {
                    totalSquareQuantity++;
                }

                System.out.print(blocks[i][j]);
            }

            System.out.println("");
        }
        return totalSquareQuantity;
    }

    public void execute(Block[][] blocks, List<Step> steps, int totalSquareQuantity) {
        ClearingCost clearingCost = new ClearingCost();
        // Add uncleared squares
        addUnClearedSquareItem(totalSquareQuantity, clearingCost, ItemAction.UNCLEARED_SQUARES, totalSquareQuantity * 3);
        // Add destruction of protected tree
        addUnClearedSquareItem(0, clearingCost, ItemAction.DESTRUCTION_OF_PROTECTED_TREE, 0);

        // moves made from command
        calculateCommunicationCost(clearingCost, steps);

        // move bulldozer around based on steps
        moveBulldozer(clearingCost, blocks, steps);

        // Print the result into console
        printResult(clearingCost);
    }

    private void addUnClearedSquareItem(int totalSquareQuantity, ClearingCost clearingCost, ItemAction itemAction, int cost) {
        Item unClearSquareItem = new Item();
        unClearSquareItem.setName(itemAction.getActionName());
        unClearSquareItem.setCost(cost);
        unClearSquareItem.setQuantity(totalSquareQuantity);
        clearingCost.getItems().add(unClearSquareItem);
    }

    private void calculateCommunicationCost(ClearingCost clearingCost, List<Step> steps) {
        List<Step> newSteps = steps.stream().filter(p -> !p.getCode().equals(StepEnum.QUIT.getCode())).collect(Collectors.toList());

        Item communicationCost = new Item();
        communicationCost.setName(ItemAction.COMMUNICATION_OVER_HEAD.getActionName());
        int quantity = newSteps.size();
        communicationCost.setQuantity(quantity);
        communicationCost.setCost(quantity * ItemAction.COMMUNICATION_OVER_HEAD.getCredit());
        clearingCost.getItems().add(communicationCost);
    }


    private void moveBulldozer(ClearingCost clearingCost, Block[][] blocks, List<Step> steps) {
        if (steps == null || steps.isEmpty()) {
            return;
        }

        Item fuelItem = new Item();
        fuelItem.setName(ItemAction.FUEL_USAGE.getActionName());

        int fuelCost = 0;
        int clearedBlockCount = 0;

        // Site rows and columns
        int row = blocks.length;
        int col = blocks[0].length;

        // Initial position of bulldozer
        int currentRow = -1;
        int currentCol = -1;

        // Current direction
        Direction direction = Direction.EAST;

        for (int i = 0; i < steps.size(); i++) {
            boolean firstStep = (i == 0);
            Step step = steps.get(i);
            int distance = step.getDistance();

            if (firstStep && step.getCode().equals(StepEnum.ADVANCE.getCode())) {
                // Move one step to enter the site
                distance = distance - 1;

                currentRow++;
                currentCol++;

                if (blocks[currentRow][currentCol].getLandType() == LandType.PLAIN) {
                    // Plain land and use one unit fuel
                    fuelCost = fuelCost + 1;
                    clearedBlockCount = clearedBlockCount + 1;
                } else if (blocks[currentRow][currentCol].getLandType() == LandType.ROCKY) {
                    // Rocky land as 2 unit fuel except cleared already
                    clearedBlockCount = clearedBlockCount + 1;

                    if (blocks[currentRow][currentCol].isClearedBefore()) {
                        fuelCost = fuelCost + 1;
                    } else {
                        fuelCost = fuelCost + 1;
                        blocks[currentRow][currentCol].setClearedBefore(true);
                    }
                } else if (blocks[currentRow][currentCol].getLandType() == LandType.TREE) {
                    // Tree land will cost 2 unit of fuel and scratch if not stop there (?)
                    clearedBlockCount = clearedBlockCount + 1;

                    if (blocks[currentRow][currentCol].isClearedBefore()) {
                        fuelCost = fuelCost + 1;
                    } else {
                        fuelCost = fuelCost + 2;
                        blocks[currentRow][currentCol].setClearedBefore(true);

                        // Add scratch cost
                        Item scratchItem = new Item();
                        scratchItem.setName(ItemAction.PAINT_DAMAGE_TO_BULLDOZER.getActionName());
                        scratchItem.setQuantity(1);
                        scratchItem.setCost(ItemAction.PAINT_DAMAGE_TO_BULLDOZER.getCredit());

                        clearingCost.getItems().add(scratchItem);
                    }
                } else {
                    // Reserved Tree land will terminate program right now
                    for (Item item : clearingCost.getItems()) {
                        if (item.getName().equals(ItemAction.DESTRUCTION_OF_PROTECTED_TREE.getActionName())) {
                            item.setQuantity(1);
                            item.setCost(ItemAction.DESTRUCTION_OF_PROTECTED_TREE.getCredit());
                            clearingCost.getItems().add(item);

                            break;
                        }
                    }
                    break;
                }
            }

            if (step.getCode().equals(StepEnum.ADVANCE.getCode())) {

                while (distance-- > 0) {
                    // facing east so move to the right
                    switch (direction) {
                        case EAST:
                            currentCol++;
                            break;
                        case WEST:
                            currentCol--;
                            break;
                        case NORTH:
                            currentRow--;
                            break;
                        case SOUTH:
                            currentRow++;
                            break;
                    }

                    if (currentCol < 0 || currentCol >= col || currentRow < 0 || currentRow >= row) {
                        // terminate program
                        return;
                    }

                    if (blocks[currentRow][currentCol].getLandType() == LandType.PLAIN) {
                        clearedBlockCount = clearedBlockCount + 1;

                        fuelCost = fuelCost + 1;
                        // Plain land and use one unit fuel
                    } else if (blocks[currentRow][currentCol].getLandType() == LandType.ROCKY) {
                        // Rocky land as 2 unit fuel except cleared already
                        clearedBlockCount = clearedBlockCount + 1;

                        if (blocks[currentRow][currentCol].isClearedBefore()) {
                            fuelCost = fuelCost + 1;
                        } else {
                            fuelCost = fuelCost + 2;
                            blocks[currentRow][currentCol].setClearedBefore(true);
                        }
                    } else if (blocks[currentRow][currentCol].getLandType() == LandType.TREE) {
                        // Tree land will cost 2 unit of fuel and scratch if not stop there (?)
                        clearedBlockCount = clearedBlockCount + 1;

                        if (blocks[currentRow][currentCol].isClearedBefore()) {
                            fuelCost = fuelCost + 1;
                        } else {
                            fuelCost = fuelCost + 2;
                            blocks[currentRow][currentCol].setClearedBefore(true);

                            // Add scratch cost
                            Item scratchItem = new Item();
                            scratchItem.setName(ItemAction.PAINT_DAMAGE_TO_BULLDOZER.getActionName());
                            scratchItem.setQuantity(1);
                            scratchItem.setCost(ItemAction.PAINT_DAMAGE_TO_BULLDOZER.getCredit());

                            clearingCost.getItems().add(scratchItem);
                        }
                    } else {
                        // Reserved Tree land will terminate program right now
                        // Remove old one
                        for (Item item : clearingCost.getItems()) {
                            if (item.getName().equals(ItemAction.DESTRUCTION_OF_PROTECTED_TREE.getActionName())) {
                                item.setQuantity(1);
                                item.setCost(ItemAction.DESTRUCTION_OF_PROTECTED_TREE.getCredit());
                            }
                        }
                        break;
                    }
                }
            } else if (step.getCode().equals(StepEnum.LEFT.getCode())) {
                direction = DirectionUtil.getDirectionAfterLeft(direction);
            } else if (step.getCode().equals(StepEnum.RIGHT.getCode())) {
                direction = DirectionUtil.getDirectionAfterRight(direction);
            } else {
                // quit command
                break;
            }
        }

        fuelItem.setCost(fuelCost);
        fuelItem.setQuantity(fuelCost);
        clearingCost.getItems().add(fuelItem);

        for (Item item : clearingCost.getItems()) {
            if (item.getName().equals(ItemAction.UNCLEARED_SQUARES.getActionName())) {
                int quantity = item.getQuantity();

                int unclearedBlockCount = quantity - clearedBlockCount;
                item.setQuantity(unclearedBlockCount);
                item.setCost(unclearedBlockCount * 3);
            }
        }
    }

    private void printResult(ClearingCost clearingCost) {
        System.out.println("The costs for this land clearing operation were:");
        System.out.println("");

        System.out.printf("%-48s %16s %16s", "Item", "Quantity", "Cost");
        int totalCost = 0;
        for (Item item : clearingCost.getItems()) {
            System.out.println("");
            System.out.printf("%-48s %16s %16s", item.getName(), item.getQuantity(),  item.getCost());
            totalCost = totalCost + item.getCost();
        }

        System.out.println("");
        System.out.println("----");
        System.out.printf("%-48s %16s %16s", "Total", "", totalCost);

        System.out.println("");
        System.out.println("");
        System.out.println("Thank you for using the Aconex site clearing simulator.");
    }
}
