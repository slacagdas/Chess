package com.amirhn;

import com.amirhn.Game.BoardExtraTest;
import com.amirhn.Game.SceneTest;
import com.amirhn.Game.StatusTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Board Suite")
@SelectClasses({ BoardExtraTest.class, SceneTest.class, StatusTest.class })
public class BoardSuite {
}