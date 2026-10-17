package com.amirhn.Moves;

import static org.junit.jupiter.api.Assertions.*;

import com.amirhn.Game.Board;
import com.amirhn.Game.Location;
import com.amirhn.Pieces.Piece;
import org.junit.jupiter.api.Test;

public class MoveTest {

    /**
     * Move is abstract, so a minimal subclass is used to test the shared behaviour.
     */
    private static class DummyMove extends Move {
        DummyMove(Piece piece) {
            super(MoveType.CASTLING, piece);
        }

        @Override
        public boolean applyOnBoard(Board board) {
            return true;
        }

        @Override
        protected void undoOnBoard(Board board) {
        }

        @Override
        public Location getStartpointLocation() {
            return null;
        }

        @Override
        public Location getEndpointLocation() {
            return null;
        }
    }

    @Test
    void constructorStoresTypeAndPiece() {
        Piece rook = Piece.generate('R', Location.valueOf("a1"));
        Move move = new DummyMove(rook);
        assertAll(
                () -> assertEquals(MoveType.CASTLING, move.type), () -> assertSame(rook, move.piece));
    }

    @Test
    void isValidApplyOnBoardDependsOnThePieceBeingOnTheBoard() {
        Board board = new Board(8, 8);
        Piece rook = Piece.generate('R', Location.valueOf("a1"));
        Move move = new DummyMove(rook);
        assertFalse(move.isValidApplyOnBoard(board));
        board.setPiece(rook);
        assertTrue(move.isValidApplyOnBoard(board));
    }
}