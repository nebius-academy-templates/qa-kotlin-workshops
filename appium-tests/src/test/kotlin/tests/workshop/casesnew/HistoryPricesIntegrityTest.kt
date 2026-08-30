package tests.workshop.casesnew

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rule.AppiumTestCase
import testdata.TestData

class HistoryPricesIntegrityTest : AppiumTestCase() {
    @Test
    @DisplayName("Order history carries no zero-priced rides")
    fun testHistoryHasNoZeroPricedRides() {
        step("Start on the map") {
            map.awaitReady()
        }
        step("Open Order history from the drawer") {
            drawer.openOrders()
        }
        step("Every zero-priced entry is rejected") {
            orders.assertHistoryPrices(TestData.PAST_ORDERS.filterValues { it.startsWith("0.00") })
        }
    }
}
