package com.amirhn.Game;

import static org.junit.jupiter.api.Assertions.*;

import com.amirhn.Pieces.Piece;
import org.junit.jupiter.api.Test;

public class SceneTest {

    private Board boardWithRook() {
        Board board = new Board(8, 8);
        board.setPiece(Piece.generate('R', Location.valueOf("a1")));
        return board;
    }

    @Test
    void constructorStoresBoardAndTurn() {
        Board board = new Board(8, 8);
        Scene scene = new Scene(board, Color.BLACK);
        assertAll(
                () -> assertSame(board, scene.getBoard()),
                () -> assertEquals(Color.BLACK, scene.getTurn()));
    }

    @Test
    void equalsIsTrueForSameBoardContentAndTurn() {
        Scene first = new Scene(boardWithRook(), Color.WHITE);
        Scene second = new Scene(boardWithRook(), Color.WHITE);
        assertAll(() -> assertEquals(first, second), () -> assertNotSame(first, second));
    }

    @Test
    void equalsIsFalseForDifferentTurn() {
        assertNotEquals(
                new Scene(boardWithRook(), Color.WHITE), new Scene(boardWithRook(), Color.BLACK));
    }

    @Test
    void equalsIsFalseForDifferentBoard() {
        assertNotEquals(
                new Scene(boardWithRook(), Color.WHITE), new Scene(new Board(8, 8), Color.WHITE));
    }

    @Test
    void equalsIsFalseForNullAndOtherType() {
        Scene scene = new Scene(new Board(8, 8), Color.WHITE);
        assertAll(() -> assertFalse(scene.equals(null)), () -> assertFalse(scene.equals("scene")));
    }
}