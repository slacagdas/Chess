package com.amirhn;

import com.amirhn.Pieces.PieceTest;
import com.amirhn.Pieces.PieceTypeTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Pieces Suite")
@SelectClasses({ PieceTypeTest.class, PieceTest.class })
public class PiecesSuite {
}