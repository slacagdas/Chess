package com.amirhn;

import com.amirhn.Game.ColorTest;
import com.amirhn.Game.LocationTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Board Basics Suite")
@SelectClasses({ LocationTest.class, ColorTest.class })
public class BoardBasicsSuite {
}