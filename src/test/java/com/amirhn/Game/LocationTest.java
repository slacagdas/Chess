package com.amirhn.Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LocationTest {

    @Test
    void constructorStoresRowAndColumn() {
        Location location = new Location(3, 5);
        assertAll(() -> assertEquals(3, location.row), () -> assertEquals(5, location.column));
    }

    @Test
    void valueOfIntCreatesEqualLocation() {
        assertEquals(new Location(2, 6), Location.valueOf(2, 6));
    }

    @ParameterizedTest
    @CsvSource({ "a1,0,0", "h8,7,7", "e4,3,4", "c6,5,2" })
    void valueOfStringParsesSquareNotation(String square, int row, int column) {
        Location location = Location.valueOf(square);
        assertAll(() -> assertEquals(row, location.row), () -> assertEquals(column, location.column));
    }

    @Test
    void toStringReturnsAlgebraicNotation() {
        assertAll(
                () -> assertEquals("a1", new Location(0, 0).toString()),
                () -> assertEquals("h8", new Location(7, 7).toString()));
    }

    @Test
    void byOffsetMovesLocation() {
        assertEquals(new Location(4, 5), new Location(3, 3).byOffset(1, 2));
    }

    @Test
    void a1IsADarkSquare() {
        assertFalse(Location.valueOf("a1").isLight());
    }

    @Test
    void h1IsALightSquare() {
        assertTrue(Location.valueOf("h1").isLight());
    }

    @Test
    void equalLocationsAreEqualButNotTheSameObject() {
        Location first = new Location(1, 1);
        Location second = new Location(1, 1);
        assertAll(() -> assertEquals(first, second), () -> assertNotSame(first, second));
    }

    @Test
    void equalsReturnsFalseForDifferentNullAndOtherType() {
        Location location = new Location(1, 2);
        assertAll(
                () -> assertNotEquals(location, new Location(2, 1)),
                () -> assertFalse(location.equals(null)),
                () -> assertFalse(location.equals("b3")));
    }

    @Test
    void equalLocationsHaveSameHashCode() {
        assertEquals(new Location(4, 4).hashCode(), new Location(4, 4).hashCode());
    }

    @Test
    void valueOfStringThrowsWhenTooShort() {
        assertThrows(StringIndexOutOfBoundsException.class, () -> Location.valueOf("a"));
    }

    @Test
    void valueOfStringThrowsOnNull() {
        assertThrows(NullPointerException.class, () -> Location.valueOf((String) null));
    }
}