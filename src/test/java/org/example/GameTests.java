package org.example;

import org.junit.*;
import org.junit.rules.ExpectedException;

public class GameTests {
    Game game;

    @Before
    public void setup() {
        game = new Game();
    }


    @Test
    public void scoreIsZeroWhenStartTheGame() {
        Assert.assertEquals(0, game.score());
    }

    @Test
    public void scoreIsNotChangedWhenRollIsMissed() throws Exception {
        int score = game.score();
        game.roll(0);
        Assert.assertEquals(score, game.score());
    }

    @Test
    public void ifFrameIsOpenScoreAddedIsKnockedPinsCount() throws Exception {
        game.roll(4);
        game.roll(5);
        Assert.assertEquals(4 + 5, game.score());
    }

    @Test
    public void ifFrameIsSpareAddNextRollScoreToFrame() throws Exception {
        game.roll(4);
        game.roll(6);
        game.roll(3);
        game.roll(2);
        Assert.assertEquals((4 + 6 + 3) + (3 + 2), game.score());
    }

    @Test
    public void spareFromDifferentFramesNotCounted() throws Exception {
        game.roll(3);
        game.roll(6);
        game.roll(4);
        game.roll(3);
        Assert.assertEquals((3 + 6) + (4 + 3), game.score());
    }

    @Test
    public void ifFrameIsStrikeAddNextTwoRollsScoreToFrame() throws Exception {
        game.roll(10);
        game.roll(3);
        game.roll(6);
        Assert.assertEquals((10 + 3 + 6) + (3 + 6), game.score());
    }

    @Test
    public void behaveCorrectlyWhenTwoConsecutiveStrike() throws Exception {
        game.roll(10);
        game.roll(10);
        game.roll(3);
        game.roll(6);
        Assert.assertEquals((10 + 10 + 3) + (10 + 3 + 6) + (3 + 6), game.score());
    }

    @Test
    public void throwExceptionWhenRollAfterGameEnds() throws Exception {
        for (int i = 0; i < 20; i++) {
            game.roll(i % 5);

        }

        Assert.assertThrows(Exception.class, () -> game.roll(1));
    }

    @Test
    public void allowRollOneBonusIfTenthFrameIsSpare() throws Exception {
        for (int i = 0; i < 18; i++) {
            game.roll(i % 5);
        }
        game.roll(7);
        game.roll(3);

        game.roll(4);
    }

    @Test
    public void allowRollOnlyOneBonusIfTenthFrameIsSpare() throws Exception {
        for (int i = 0; i < 18; i++) {
            game.roll(i % 5);
        }
        game.roll(7);
        game.roll(3);

        game.roll(4);
        Assert.assertThrows(Exception.class, () -> game.roll(4));
    }

    @Test
    public void allowRollTwoBonusIfTenthFrameIsStrike() throws Exception {
        for (int i = 0; i < 18; i++) {
            game.roll(i % 5);
        }
        game.roll(10);

        game.roll(3);
        game.roll(4);
    }

    @Test
    public void allowRollOnlyTwoBonusIfTenthFrameIsStrike() throws Exception {
        for (int i = 0; i < 18; i++) {
            game.roll(i % 5);
        }
        game.roll(10);

        game.roll(3);
        game.roll(4);
        Assert.assertThrows(Exception.class, () -> game.roll(4));
    }

    @Test
    public void bonusRollsNotCalculatedAsASeparatedFrame() throws Exception {
        for (int i = 0; i < 18; i++) {
            game.roll(1);
        }

        game.roll(10);

        game.roll(3);
        game.roll(4);

        Assert.assertEquals(18 + (10 + 3 + 4), game.score());
    }

    /**
     * Test full sample number 1
     * sample reference: <a href="https://templatelab.com/wp-content/uploads/2021/03/bowling-score-sheet-08.jpg">link</a>
     */
    @Test
    public void testSample1() throws Exception {
        //Frame 1
        game.roll(5);
        game.roll(5);
        Assert.assertEquals(10, game.score());
        //Frame 2
        game.roll(4);
        Assert.assertEquals(18, game.score());
        game.roll(5);
        Assert.assertEquals(23, game.score());
        //Frame 3
        game.roll(8);
        game.roll(2);
        //Frame 4
        game.roll(10);
        //Frame 5
        game.roll(0);
        game.roll(10);
        //Frame 6
        game.roll(10);
        //Frame 7
        game.roll(6);
        game.roll(2);
        //Frame 8
        game.roll(10);
        //Frame 9
        game.roll(4);
        game.roll(6);
        //Frame 10
        game.roll(10);

        // Strike Bonus
        game.roll(10);
        game.roll(10);
        Assert.assertEquals(179, game.score());
    }
    /**
     * Test full sample number 2
     * sample reference: <a href="https://templatelab.com/wp-content/uploads/2021/03/bowling-score-sheet-08.jpg">link</a>
     */
    @Test
    public void testSample2() throws Exception {
        //Frame 1
        game.roll(5);
        game.roll(5);
        //Frame 2
        game.roll(4);
        game.roll(0);
        //Frame 3
        game.roll(8);
        game.roll(1);
        //Frame 4
        game.roll(10);
        //Frame 5
        game.roll(0);
        game.roll(10);
        //Frame 6
        game.roll(10);
        //Frame 7
        game.roll(10);
        //Frame 8
        game.roll(10);
        //Frame 9
        game.roll(4);
        game.roll(6);
        //Frame 10
        game.roll(10);

        // Strike Bonus
        game.roll(10);
        game.roll(5);
        Assert.assertEquals(186, game.score());
    }
}
