package tests.workshop

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rule.AppiumTestCase
import testdata.TestData

class CompletedRidePriceTest : AppiumTestCase() {
    @Test
    @DisplayName("The completed ride price is the price stored in order history")
    fun testCompletedPriceMatchesHistoryRow() {
        var completedPrice = ""
        step("Search a destination and order the Yellow tariff") {
            map.searchDestination(TestData.DESTINATION)
            map.selectAndOrderRide(1)
        }
        step("The backend finds a driver") {
            map.assertDriverFound()
        }
        step("Complete the ride and read the price it reports") {
            map.completeRide()
            completedPrice = map.readCompletedPrice()
        }
        step("The newest history entry carries the same price") {
            map.returnHomeAfterCompletion()
            drawer.openOrders()
            orders.assertOrderPrice(4, completedPrice)
        }
    }
}
