package com.amirhn.Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ColorTest {

    @Test
    void directionsPointInOppositeWays() {
        assertAll(() -> assertEquals(1, Color.WHITE.direction), () -> assertEquals(-1, Color.BLACK.direction));
    }

    @Test
    void oppositeSwapsColors() {
        assertAll(
                () -> assertEquals(Color.BLACK, Color.WHITE.opposite()),
                () -> assertEquals(Color.WHITE, Color.BLACK.opposite()));
    }

    @Test
    void valueOfPieceCharUsesLetterCase() {
        assertAll(
                () -> assertEquals(Color.WHITE, Color.valueOfPieceChar('K')),
                () -> assertEquals(Color.BLACK, Color.valueOfPieceChar('k')));
    }

    @Test
    void valueOfPieceCharReturnsNullForNonLetter() {
        assertNull(Color.valueOfPieceChar('7'));
    }

    @Test
    void valueOfCharIgnoresCase() {
        assertAll(
                () -> assertEquals(Color.WHITE, Color.valueOf('w')),
                () -> assertEquals(Color.BLACK, Color.valueOf('B')));
    }

    @Test
    void valueOfCharReturnsNullForUnknownLetter() {
        assertNull(Color.valueOf('x'));
    }

    @Test
    void fromStringParsesFullNamesIgnoringCase() {
        assertAll(
                () -> assertEquals(Color.WHITE, Color.fromString("white")),
                () -> assertEquals(Color.BLACK, Color.fromString("BLACK")));
    }

    @Test
    void fromStringParsesSingleLetter() {
        assertEquals(Color.WHITE, Color.fromString("w"));
    }

    @Test
    void fromStringReturnsNullForUnknownText() {
        assertAll(() -> assertNull(Color.fromString("red")), () -> assertNull(Color.fromString("")));
    }

    @Test
    void fromStringThrowsOnNull() {
        assertThrows(NullPointerException.class, () -> Color.fromString(null));
    }
}