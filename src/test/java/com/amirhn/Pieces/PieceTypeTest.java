package com.amirhn.Pieces;

import static org.junit.jupiter.api.Assertions.*;

import com.amirhn.Game.Color;
import org.junit.jupiter.api.Test;

public class PieceTypeTest {

    @Test
    void lettersMatchChessNotation() {
        assertAll(
                () -> assertEquals('K', PieceType.KING.letter),
                () -> assertEquals('Q', PieceType.QUEEN.letter),
                () -> assertEquals('N', PieceType.KNIGHT.letter));
    }

    @Test
    void valueOfLetterIgnoresCase() {
        assertAll(
                () -> assertEquals(PieceType.QUEEN, PieceType.valueOf('q')),
                () -> assertEquals(PieceType.ROOK, PieceType.valueOf('R')));
    }

    @Test
    void valueOfUnknownLetterReturnsNull() {
        assertNull(PieceType.valueOf('x'));
    }

    @Test
    void getSymbolReturnsWhiteAndBlackFigurines() {
        assertAll(
                () -> assertEquals('\u2654', PieceType.KING.getSymbol(Color.WHITE)),
                () -> assertEquals('\u265A', PieceType.KING.getSymbol(Color.BLACK)));
    }

    @Test
    void getSymbolReturnsQuestionMarkForNullColor() {
        assertEquals('?', PieceType.KING.getSymbol(null));
    }

    @Test
    void valuesAreListedInDeclaredOrder() {
        PieceType[] expected = {
                PieceType.KING,
                PieceType.QUEEN,
                PieceType.KNIGHT,
                PieceType.ROOK,
                PieceType.BISHOP,
                PieceType.PAWN
        };
        assertArrayEquals(expected, PieceType.values());
    }
}