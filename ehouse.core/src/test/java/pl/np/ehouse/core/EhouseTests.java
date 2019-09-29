package pl.np.ehouse.core;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({"pl.np.ehouse.core.util","pl.np.ehouse.core.message"})
class EhouseTests {
}
