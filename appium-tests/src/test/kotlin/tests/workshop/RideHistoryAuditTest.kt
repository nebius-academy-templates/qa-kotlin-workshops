package tests.workshop

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rule.AppiumTestCase
import testdata.TestData

class RideHistoryAuditTest : AppiumTestCase() {
    @Test
    @DisplayName("A completed ride records its fare")
    fun testCompletedRideFareIsRecorded() {
        step("Order and complete a Yellow ride") {
            map.searchDestination(TestData.DESTINATION)
            map.selectAndOrderRide(1)
            map.assertDriverFound()
            map.completeRide()
        }
        step("The completion screen reports a fare") {
            orders.rememberCompletedPrice(map.readCompletedPrice())
        }
    }

    @Test
    @DisplayName("The audited fare reaches order history")
    fun testAuditedFareReachesHistory() {
        step("Order and complete a Yellow ride") {
            map.searchDestination(TestData.DESTINATION)
            map.selectAndOrderRide(1)
            map.assertDriverFound()
            map.completeRide()
        }
        step("The newest history entry carries the audited fare") {
            map.returnHomeAfterCompletion()
            drawer.openOrders()
            orders.assertHistoryPriceMatchesRemembered(4)
        }
    }
}
