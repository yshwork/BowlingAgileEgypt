package org.example;

public class Frame {
    private int[] rolls;
    private int rollIndex;
    // Score for next roll when spare or for next two rolls when strike
    private int bonus;

    Frame() {
        rolls = new int[2];
    }

    void roll(int pins) throws Exception {
        if (done()) {
            throw new Exception("Frame is done can't roll more in the same frame");
        }
        rolls[rollIndex] = pins;
        rollIndex++;
    }

    private int rollsScore() {
        int total = 0;
        for (int roll : rolls) {
            total += roll;
        }
        return total;
    }

    boolean done() {
        return rollsScore() == 10 || rollIndex == 2;
    }


    int score() {
        int score = rollsScore();
        if (isStrike() || isSpare()) {
            return score + bonus;
        }
        return score;
    }

    boolean isStrike() {
        return rolls[0] == 10;
    }

    boolean isSpare() {
        return !isStrike() && (rolls[0] + rolls[1] == 10);
    }

    boolean isBonus(){
        return isSpare() || isStrike();
    }
    void addBonus(int score) {
        bonus += score;
    }
    boolean isFirstRoll() {
        return rollIndex == 0;
    }
//
    int rollsCount(){
        return rollIndex;
    }
}
