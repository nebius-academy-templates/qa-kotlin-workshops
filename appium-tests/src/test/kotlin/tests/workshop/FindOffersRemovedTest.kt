package tests.workshop

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rule.AppiumTestCase
import testdata.TestData

class FindOffersRemovedTest : AppiumTestCase() {
    @Test
    @DisplayName("Tariffs load automatically and no Find offers button remains")
    fun testNoFindOffersButtonRemains() {
        step("Type a destination") {
            map.searchDestination(TestData.DESTINATION)
        }
        step("Tariffs appear and the old button is gone") {
            map.assertTariffs(TestData.RIDE_NAMES, TestData.TARIFF_PRICES_ON_MAP)
            map.assertFindOffersRemoved()
        }
    }
}
