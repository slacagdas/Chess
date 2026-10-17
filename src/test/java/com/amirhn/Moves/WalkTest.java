package com.amirhn.Moves;

import static org.junit.jupiter.api.Assertions.*;

import com.amirhn.Game.Board;
import com.amirhn.Game.Location;
import com.amirhn.Pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class WalkTest {

    private Board board;
    private Piece pawn;

    private static Location at(String square) {
        return Location.valueOf(square);
    }

    @BeforeEach
    void setUp() {
        board = new Board(8, 8);
        pawn = Piece.generate('P', at("a2"));
        board.setPiece(pawn);
    }

    @Test
    void constructorStoresTypePieceAndLocations() {
        Walk walk = new Walk(pawn, at("a3"));
        assertAll(
                () -> assertEquals(MoveType.WALK, walk.type),
                () -> assertSame(pawn, walk.piece),
                () -> assertEquals(at("a2"), walk.getStartpointLocation()),
                () -> assertEquals(at("a3"), walk.getEndpointLocation()));
    }

    @Test
    void applyOnBoardMovesThePiece() {
        Walk walk = new Walk(pawn, at("a3"));
        boolean applied = walk.applyOnBoard(board);
        assertAll(
                () -> assertTrue(applied),
                () -> assertNull(board.getPiece(at("a2"))),
                () -> assertSame(pawn, board.getPiece(at("a3"))),
                () -> assertTrue(pawn.hasMoved()));
    }

    @Test
    void applyOnBoardFailsWhenDestinationIsOccupied() {
        board.setPiece(Piece.generate('p', at("a3")));
        Walk walk = new Walk(pawn, at("a3"));
        assertAll(
                () -> assertFalse(walk.applyOnBoard(board)),
                () -> assertSame(pawn, board.getPiece(at("a2"))),
                () -> assertFalse(pawn.hasMoved()));
    }

    @Test
    void applyOnBoardFailsWhenDestinationIsOutsideTheBoard() {
        Walk walk = new Walk(pawn, Location.valueOf(8, 0));
        assertAll(
                () -> assertFalse(walk.applyOnBoard(board)),
                () -> assertSame(pawn, board.getPiece(at("a2"))));
    }

    @Test
    void undoOnBoardRestoresThePieceAndItsMoveCount() {
        Walk walk = new Walk(pawn, at("a3"));
        walk.applyOnBoard(board);
        walk.undoOnBoard(board);
        assertAll(
                () -> assertSame(pawn, board.getPiece(at("a2"))),
                () -> assertNull(board.getPiece(at("a3"))),
                () -> assertEquals(at("a2"), pawn.getLocation()),
                () -> assertFalse(pawn.hasMoved()));
    }

    @ParameterizedTest
    @CsvSource({ "R,a1,a5,Ra1-a5", "N,b1,c3,Nb1-c3", "P,e2,e4,Pe2-e4" })
    void toStringShowsPieceLetterAndBothSquares(
            String letter, String from, String to, String expected) {
        Piece piece = Piece.generate(letter.charAt(0), at(from));
        assertEquals(expected, new Walk(piece, at(to)).toString());
    }
}