package com.amirhn;

import com.amirhn.Moves.CaptureExtraTest;
import com.amirhn.Moves.MoveTest;
import com.amirhn.Moves.PromotionTest;
import com.amirhn.Moves.WalkTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Moves Suite")
@SelectClasses({ MoveTest.class, WalkTest.class, CaptureExtraTest.class, PromotionTest.class })
public class MovesSuite {
}