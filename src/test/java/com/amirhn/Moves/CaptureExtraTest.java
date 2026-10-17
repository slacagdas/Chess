package com.amirhn.Moves;

import static org.junit.jupiter.api.Assertions.*;

import com.amirhn.Game.Board;
import com.amirhn.Game.Location;
import com.amirhn.Pieces.Piece;
import org.junit.jupiter.api.Test;

public class CaptureExtraTest {

    private static Location at(String square) {
        return Location.valueOf(square);
    }

    @Test
    void constructorStoresTypePiecesAndLocations() {
        Piece rook = Piece.generate('R', at("a1"));
        Piece pawn = Piece.generate('p', at("a7"));
        Capture capture = new Capture(rook, pawn);
        assertAll(
                () -> assertEquals(MoveType.CAPTURE, capture.type),
                () -> assertSame(rook, capture.piece),
                () -> assertSame(pawn, capture.capturePiece),
                () -> assertEquals(at("a1"), capture.getStartpointLocation()),
                () -> assertEquals(at("a7"), capture.getEndpointLocation()));
    }

    @Test
    void applyOnBoardRejectsIllegalCaptures() {
        Board board = new Board(8, 8);
        Piece whiteRook = Piece.generate('R', at("a1"));
        Piece whitePawn = Piece.generate('P', at("a2"));
        Piece whiteQueen = Piece.generate('Q', at("d1"));
        Piece blackKing = Piece.generate('k', at("d8"));
        Piece ghost = Piece.generate('p', at("h7"));
        board.setPiece(whiteRook);
        board.setPiece(whitePawn);
        board.setPiece(whiteQueen);
        board.setPiece(blackKing);

        assertAll(
                () -> assertFalse(new Capture(whiteRook, whitePawn).applyOnBoard(board)),
                () -> assertFalse(new Capture(whiteQueen, blackKing).applyOnBoard(board)),
                () -> assertFalse(new Capture(whiteRook, ghost).applyOnBoard(board)),
                () -> assertSame(whitePawn, board.getPiece(at("a2"))),
                () -> assertSame(blackKing, board.getPiece(at("d8"))));
    }

    @Test
    void applyOnBoardClearsTheCapturedPiecesLocation() {
        Board board = new Board(8, 8);
        Piece rook = Piece.generate('R', at("a1"));
        Piece pawn = Piece.generate('p', at("a7"));
        board.setPiece(rook);
        board.setPiece(pawn);
        boolean applied = new Capture(rook, pawn).applyOnBoard(board);
        assertAll(
                () -> assertTrue(applied),
                () -> assertSame(rook, board.getPiece(at("a7"))),
                () -> assertNull(board.getPiece(at("a1"))),
                () -> assertNull(pawn.getLocation()),
                () -> assertTrue(rook.hasMoved()));
    }

    @Test
    void applyThenUndoRestoresBothPieces() {
        Board board = new Board(8, 8);
        Piece rook = Piece.generate('R', at("a1"));
        Piece pawn = Piece.generate('p', at("a7"));
        board.setPiece(rook);
        board.setPiece(pawn);
        Capture capture = new Capture(rook, pawn);
        capture.applyOnBoard(board);
        capture.undoOnBoard(board);
        assertAll(
                () -> assertSame(rook, board.getPiece(at("a1"))),
                () -> assertSame(pawn, board.getPiece(at("a7"))),
                () -> assertEquals(at("a1"), rook.getLocation()),
                () -> assertEquals(at("a7"), pawn.getLocation()),
                () -> assertFalse(rook.hasMoved()));
    }

    @Test
    void toStringUsesTheCaptureNotation() {
        Piece rook = Piece.generate('R', at("a1"));
        Piece pawn = Piece.generate('p', at("a7"));
        assertEquals("Ra1xa7", new Capture(rook, pawn).toString());
    }
}