package com.amirhn.Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    void valuesAreListedInDeclaredOrder() {
        Status[] expected = { Status.ONGOING, Status.CHECKMATE, Status.STALEMATE, Status.DRAW };
        assertArrayEquals(expected, Status.values());
    }

    @Test
    void valueOfParsesExactName() {
        assertEquals(Status.CHECKMATE, Status.valueOf("CHECKMATE"));
    }

    @Test
    void valueOfRejectsUnknownOrWrongCaseNames() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Status.valueOf("RESIGNED")),
                () -> assertThrows(IllegalArgumentException.class, () -> Status.valueOf("checkmate")));
    }
}