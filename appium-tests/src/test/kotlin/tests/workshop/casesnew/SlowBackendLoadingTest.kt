package tests.workshop.casesnew

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rule.AppiumTestCase
import rule.ConditionControl
import testdata.TestData

class SlowBackendLoadingTest : AppiumTestCase() {
    @Test
    @DisplayName("Slow backend delays tariffs and the app shows its progress")
    fun testSlowBackendDelaysTariffLoading() {
        step("Start on the map (authorized)") {
            map.awaitReady()
        }
        step("Enable slow_backend_response") {
            ConditionControl.enable("slow_backend_response")
        }
        step("Start a search under degraded latency") {
            map.startSearch(TestData.DESTINATION)
        }
        step("The loading indicator shows and tariffs still arrive") {
            map.assertLoadingThenRides()
        }
    }
}
