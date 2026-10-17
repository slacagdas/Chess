package com.amirhn.Game;

import static org.junit.jupiter.api.Assertions.*;

import com.amirhn.Pieces.King;
import com.amirhn.Pieces.Piece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BoardExtraTest {

    private static final String START = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    @Test
    void fromFenStartPositionHas32Pieces() {
        assertEquals(32, Board.fromFEN(START).getAllPieces().size());
    }

    @Test
    void fromFenPlacesKingsOnTheirStartingSquares() {
        Board board = Board.fromFEN(START);
        Piece whiteKing = board.getPiece(Location.valueOf("e1"));
        Piece blackKing = board.getPiece(Location.valueOf("e8"));
        assertAll(
                () -> assertInstanceOf(King.class, whiteKing),
                () -> assertEquals(Color.WHITE, whiteKing.color),
                () -> assertInstanceOf(King.class, blackKing),
                () -> assertEquals(Color.BLACK, blackKing.color));
    }

    @Test
    void fromFenSupportsNonStandardBoardSize() {
        Board board = Board.fromFEN("4/4/4/4");
        assertAll(
                () -> assertEquals(4, board.rows),
                () -> assertEquals(4, board.columns),
                () -> assertTrue(board.getAllPieces().isEmpty()));
    }

    @Test
    void setupFenReplacesPreviousPieces() {
        Board board = new Board(8, 8);
        board.setPiece(Piece.generate('Q', Location.valueOf("d1")));
        board.setupFEN("8/8/8/8/8/8/8/4K3");
        assertAll(
                () -> assertNull(board.getPiece(Location.valueOf("d1"))),
                () -> assertEquals(1, board.getAllPieces().size()),
                () -> assertInstanceOf(King.class, board.getPiece(Location.valueOf("e1"))));
    }

    @ParameterizedTest
    @CsvSource({ "0,0,true", "7,7,true", "8,0,false", "0,8,false", "-1,3,false", "3,-1,false" })
    void isValidLocationChecksBounds(int row, int column, boolean expected) {
        Board board = new Board(8, 8);
        assertEquals(expected, board.isValidLocation(Location.valueOf(row, column)));
    }

    @Test
    void isValidLocationReturnsFalseForNull() {
        assertFalse(new Board(8, 8).isValidLocation(null));
    }

    @Test
    void isOccupiedReturnsFalseOutsideTheBoard() {
        assertFalse(Board.fromFEN(START).isOccupied(Location.valueOf(10, 10)));
    }

    @Test
    void isValidPieceIsTrueOnlyForThePieceStandingOnTheSquare() {
        Board board = new Board(8, 8);
        Piece placed = Piece.generate('P', Location.valueOf("a2"));
        Piece other = Piece.generate('P', Location.valueOf("a2"));
        board.setPiece(placed);
        assertAll(() -> assertTrue(board.isValidPiece(placed)), () -> assertFalse(board.isValidPiece(other)));
    }

    @Test
    void settingPieceOnOccupiedSquareReplacesIt() {
        Board board = new Board(8, 8);
        Piece first = Piece.generate('P', Location.valueOf("a2"));
        Piece second = Piece.generate('p', Location.valueOf("a2"));
        board.setPiece(first);
        board.setPiece(second);
        assertAll(
                () -> assertEquals(1, board.getAllPieces().size()),
                () -> assertSame(second, board.getPiece(Location.valueOf("a2"))));
    }

    @Test
    void getAllPiecesIsEmptyOnNewBoard() {
        assertTrue(new Board(8, 8).getAllPieces().isEmpty());
    }

    @Test
    void copyIsIndependentFromOriginal() {
        Board board = new Board(8, 8);
        board.setPiece(Piece.generate('R', Location.valueOf("a1")));
        Board copy = board.copy();
        copy.removePiece(copy.getPiece(Location.valueOf("a1")));
        assertAll(
                () -> assertNotSame(board, copy),
                () -> assertTrue(board.isOccupied(Location.valueOf("a1"))),
                () -> assertFalse(copy.isOccupied(Location.valueOf("a1"))));
    }

    @Test
    void toStringContainsHeaderAndEightRanks() {
        String text = new Board(8, 8).toString();
        assertAll(
                () -> assertTrue(text.startsWith(" \ta b c d e f g h ")),
                () -> assertEquals(9, text.split("\n").length),
                () -> assertTrue(text.contains("\n1\t_ _ _ _ _ _ _ _ ")));
    }
}