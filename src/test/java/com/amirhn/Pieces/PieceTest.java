package com.amirhn.Pieces;

import static org.junit.jupiter.api.Assertions.*;

import com.amirhn.Game.Color;
import com.amirhn.Game.Location;
import org.junit.jupiter.api.Test;

public class PieceTest {

    private final Location e4 = Location.valueOf("e4");

    @Test
    void generateCreatesTheMatchingSubclass() {
        assertAll(
                () -> assertInstanceOf(King.class, Piece.generate(PieceType.KING, Color.WHITE, e4)),
                () -> assertInstanceOf(Queen.class, Piece.generate(PieceType.QUEEN, Color.WHITE, e4)),
                () -> assertInstanceOf(Rook.class, Piece.generate(PieceType.ROOK, Color.WHITE, e4)),
                () -> assertInstanceOf(Bishop.class, Piece.generate(PieceType.BISHOP, Color.WHITE, e4)),
                () -> assertInstanceOf(Knight.class, Piece.generate(PieceType.KNIGHT, Color.WHITE, e4)),
                () -> assertInstanceOf(Pawn.class, Piece.generate(PieceType.PAWN, Color.WHITE, e4)));
    }

    @Test
    void generateFromUppercaseFenCreatesWhitePiece() {
        Piece piece = Piece.generate('Q', e4);
        assertAll(
                () -> assertEquals(PieceType.QUEEN, piece.type),
                () -> assertEquals(Color.WHITE, piece.color));
    }

    @Test
    void generateFromLowercaseFenCreatesBlackPiece() {
        Piece piece = Piece.generate('n', e4);
        assertAll(
                () -> assertEquals(PieceType.KNIGHT, piece.type),
                () -> assertEquals(Color.BLACK, piece.color));
    }

    @Test
    void generateFromUnknownFenThrows() {
        assertThrows(NullPointerException.class, () -> Piece.generate('x', e4));
    }

    @Test
    void setLocationMarksPieceAsMoved() {
        Piece rook = Piece.generate(PieceType.ROOK, Color.WHITE, Location.valueOf("a1"));
        assertFalse(rook.hasMoved());
        rook.setLocation(Location.valueOf("a5"));
        assertAll(
                () -> assertTrue(rook.hasMoved()),
                () -> assertEquals(Location.valueOf("a5"), rook.getLocation()));
    }

    @Test
    void setLocationAfterRemoveDoesNotCountAsMove() {
        Piece bishop = Piece.generate(PieceType.BISHOP, Color.BLACK, Location.valueOf("c8"));
        bishop.removeLocation();
        assertNull(bishop.getLocation());
        bishop.setLocation(Location.valueOf("f5"));
        assertFalse(bishop.hasMoved());
    }

    @Test
    void setLocationBackUndoesTheMoveCount() {
        Location start = Location.valueOf("b1");
        Piece knight = Piece.generate(PieceType.KNIGHT, Color.WHITE, start);
        knight.setLocation(Location.valueOf("c3"));
        knight.setLocationBack(start);
        assertAll(
                () -> assertFalse(knight.hasMoved()), () -> assertEquals(start, knight.getLocation()));
    }

    @Test
    void pieceCanBeCapturedByOpponent() {
        Piece whiteRook = Piece.generate(PieceType.ROOK, Color.WHITE, Location.valueOf("a1"));
        Piece blackPawn = Piece.generate(PieceType.PAWN, Color.BLACK, Location.valueOf("a7"));
        assertTrue(blackPawn.canBeCapturedBy(whiteRook));
    }

    @Test
    void pieceCannotBeCapturedBySameColorOrNull() {
        Piece whiteRook = Piece.generate(PieceType.ROOK, Color.WHITE, Location.valueOf("a1"));
        Piece whitePawn = Piece.generate(PieceType.PAWN, Color.WHITE, Location.valueOf("a2"));
        assertAll(
                () -> assertFalse(whitePawn.canBeCapturedBy(whiteRook)),
                () -> assertFalse(whitePawn.canBeCapturedBy(null)));
    }

    @Test
    void kingCanNeverBeCaptured() {
        Piece blackKing = Piece.generate(PieceType.KING, Color.BLACK, Location.valueOf("e8"));
        Piece whiteQueen = Piece.generate(PieceType.QUEEN, Color.WHITE, Location.valueOf("d1"));
        assertFalse(blackKing.canBeCapturedBy(whiteQueen));
    }

    @Test
    void getSymbolDependsOnPieceColor() {
        Piece whiteRook = Piece.generate(PieceType.ROOK, Color.WHITE, Location.valueOf("a1"));
        Piece blackRook = Piece.generate(PieceType.ROOK, Color.BLACK, Location.valueOf("a8"));
        assertAll(
                () -> assertEquals('\u2656', whiteRook.getSymbol()),
                () -> assertEquals('\u265C', blackRook.getSymbol()));
    }

    @Test
    void copyIsEqualButIndependentAndHasNoMoveHistory() {
        Piece original = Piece.generate(PieceType.PAWN, Color.WHITE, Location.valueOf("e2"));
        original.setLocation(Location.valueOf("e4"));
        Piece copy = original.copy();
        assertAll(
                () -> assertEquals(original, copy),
                () -> assertNotSame(original, copy),
                () -> assertTrue(original.hasMoved()),
                () -> assertFalse(copy.hasMoved()));
    }
}