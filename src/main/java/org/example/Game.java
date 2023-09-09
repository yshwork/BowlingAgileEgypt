package org.example;

import org.jetbrains.annotations.Nullable;

import java.rmi.UnexpectedException;


public class Game {
    private static final int framesCount = 10;
    final private Frame[] frames;
    private int currentFrameIndex;

    private Frame getFrameOrCreate(int frameIndex) {
        if (frames[frameIndex] == null) {
            return frames[frameIndex] = new Frame();
        }
        return frames[frameIndex];
    }

    private @Nullable Frame getFrameSafe(int index) {
        if (index < 0 || index > framesCount - 1) return null;
        return getFrameOrCreate(index);
    }

    private Frame currentFrame() {
        return getFrameSafe(currentFrameIndex);
    }


    private @Nullable Frame getFrameByRollOffset(int offset) {
        int frameOffset = currentFrameIndex;
        Frame frame;
        do {
            frame = getFrameSafe(frameOffset);
            if (frame == null) return null;
            offset += frame.rollsCount();
            frameOffset--;
        } while (offset < 0);
        return frame;
    }

    Game() {
        frames = new Frame[framesCount];
    }

    private void moveToNextFrameIfDone() {
        if (currentFrame().done()) {
            currentFrameIndex++;
        }
    }


    public void roll(int pins) throws Exception {
        if (!canRoll()) {
            throw new Exception("Game ended you can not roll anymore");
        }

        if (isBonusRoll()) {
            //Deal with every bonus roll as separate Frame
            Frame lastFrame = getFrameSafe(framesCount-1);
            if(lastFrame == null) throw new UnexpectedException("Unexpected frame null value");
            lastFrame.addBonus(pins);
            currentFrameIndex++;
            return;
        }

        Frame firstPreviousRollFrame = getFrameByRollOffset(-1);
        if (firstPreviousRollFrame != null && firstPreviousRollFrame.isBonus()) {
            firstPreviousRollFrame.addBonus(pins);
        }
        Frame secondPreviousRollFrame = getFrameByRollOffset(-2);
        if (secondPreviousRollFrame != null && secondPreviousRollFrame.isStrike()) {
            secondPreviousRollFrame.addBonus(pins);
        }


        currentFrame().roll(pins);

        moveToNextFrameIfDone();
    }


    public int score() {
        int total = 0;
        for (int i = 0; i <= Math.min(framesCount-1, currentFrameIndex); i++) {
            Frame frame = getFrameSafe(i);
            if (frame == null) throw new ArrayIndexOutOfBoundsException();
            total += frame.score();
        }
        return total;
    }

    private boolean isBonusRoll() {
        return currentFrameIndex >= 10;
    }

    boolean canRoll() throws UnexpectedException {
        if (currentFrameIndex == 10) {
            Frame frame = getFrameSafe(currentFrameIndex - 1);
            if (frame == null) throw new UnexpectedException("Unexpected null value for frame");
            return frame.isBonus();
        }else if(currentFrameIndex == 11){
            Frame frame = getFrameSafe(currentFrameIndex - 2);
            if (frame == null) throw new UnexpectedException("Unexpected null value for frame");
            return frame.isStrike();
        }
        return currentFrameIndex < 10;
    }

}
