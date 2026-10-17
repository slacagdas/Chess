package com.amirhn.Moves;

import static org.junit.jupiter.api.Assertions.*;

import com.amirhn.Game.Board;
import com.amirhn.Game.Color;
import com.amirhn.Game.Location;
import com.amirhn.Pieces.Piece;
import com.amirhn.Pieces.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PromotionTest {

    private Board board;
    private Piece pawn;

    private static Location at(String square) {
        return Location.valueOf(square);
    }

    @BeforeEach
    void setUp() {
        board = new Board(8, 8);
        pawn = Piece.generate('P', at("a7"));
        board.setPiece(pawn);
    }

    @Test
    void constructorCreatesAnUnplacedPromotedPieceOfTheSameColor() {
        PawnPromotion promotion = new PawnPromotion(new Walk(pawn, at("a8")), PieceType.QUEEN);
        assertAll(
                () -> assertEquals(MoveType.PROMOTION, promotion.type),
                () -> assertSame(pawn, promotion.piece),
                () -> assertEquals(PieceType.QUEEN, promotion.promotedPiece.type),
                () -> assertEquals(Color.WHITE, promotion.promotedPiece.color),
                () -> assertNull(promotion.promotedPiece.getLocation()));
    }

    @Test
    void toStringAndLocationsDelegateToTheWrappedMove() {
        PawnPromotion promotion = new PawnPromotion(new Walk(pawn, at("a8")), PieceType.KNIGHT);
        assertAll(
                () -> assertEquals("Pa7-a8=N", promotion.toString()),
                () -> assertEquals(at("a7"), promotion.getStartpointLocation()),
                () -> assertEquals(at("a8"), promotion.getEndpointLocation()));
    }

    @Test
    void applyOnBoardReplacesThePawnWithThePromotedPiece() {
        PawnPromotion promotion = new PawnPromotion(new Walk(pawn, at("a8")), PieceType.QUEEN);
        boolean applied = promotion.applyOnBoard(board);
        Piece onEighthRank = board.getPiece(at("a8"));
        assertAll(
                () -> assertTrue(applied),
                () -> assertNull(board.getPiece(at("a7"))),
                () -> assertSame(promotion.promotedPiece, onEighthRank),
                () -> assertEquals(PieceType.QUEEN, onEighthRank.type));
    }

    @Test
    void applyOnBoardFailsForANonPawn() {
        Piece rook = Piece.generate('R', at("b7"));
        board.setPiece(rook);
        PawnPromotion promotion = new PawnPromotion(new Walk(rook, at("b8")), PieceType.QUEEN);
        assertAll(
                () -> assertFalse(promotion.applyOnBoard(board)),
                () -> assertSame(rook, board.getPiece(at("b7"))),
                () -> assertNull(board.getPiece(at("b8"))));
    }

    @Test
    void applyOnBoardFailsWhenTheSquareAheadIsOccupied() {
        board.setPiece(Piece.generate('r', at("a8")));
        PawnPromotion promotion = new PawnPromotion(new Walk(pawn, at("a8")), PieceType.QUEEN);
        assertAll(
                () -> assertFalse(promotion.applyOnBoard(board)),
                () -> assertSame(pawn, board.getPiece(at("a7"))));
    }

    @Test
    void undoOnBoardBringsTheOriginalPawnBack() {
        PawnPromotion promotion = new PawnPromotion(new Walk(pawn, at("a8")), PieceType.QUEEN);
        promotion.applyOnBoard(board);
        promotion.undoOnBoard(board);
        assertAll(
                () -> assertSame(pawn, board.getPiece(at("a7"))),
                () -> assertNull(board.getPiece(at("a8"))),
                () -> assertNull(promotion.promotedPiece.getLocation()),
                () -> assertFalse(pawn.hasMoved()));
    }

    @Test
    void promotionCanBeCombinedWithACapture() {
        Piece blackRook = Piece.generate('r', at("b8"));
        board.setPiece(blackRook);
        PawnPromotion promotion = new PawnPromotion(new Capture(pawn, blackRook), PieceType.KNIGHT);
        boolean applied = promotion.applyOnBoard(board);
        Piece onB8 = board.getPiece(at("b8"));
        assertAll(
                () -> assertEquals("Pa7xb8=N", promotion.toString()),
                () -> assertTrue(applied),
                () -> assertEquals(PieceType.KNIGHT, onB8.type),
                () -> assertEquals(Color.WHITE, onB8.color),
                () -> assertNull(blackRook.getLocation()));
    }
}