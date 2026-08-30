package tests.workshop.casesnew

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rule.AppiumTestCase
import rule.ConditionControl
import testdata.TestData

class MinivanAvailabilityContractTest : AppiumTestCase() {
    @Test
    @DisplayName("The backend marks Minivan unavailable and the app disables it")
    fun testBackendMarksMinivanUnavailable() {
        step("Search a destination") {
            map.searchDestination(TestData.DESTINATION)
        }
        step("Minivan is offered") {
            map.assertRideOptionShown(TestData.MINIVAN_RIDE_ID)
        }
        step("Enable car_unavailable") {
            ConditionControl.enable("car_unavailable")
        }
        step("Minivan is disabled, the others stay enabled") {
            map.assertTariffUnavailable(TestData.MINIVAN_RIDE_ID, TestData.RIDE_NAMES - TestData.MINIVAN_RIDE_ID)
        }
        step("Disable the state") {
            ConditionControl.disable("car_unavailable")
        }
        step("Minivan is selectable again") {
            map.assertTariffAvailable(TestData.MINIVAN_RIDE_ID)
        }
    }
}
