package tests.workshop.casesnew

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rule.AppiumTestCase
import rule.ConditionControl
import testdata.TestData

class CarUnavailableGuardTest : AppiumTestCase() {
    @Test
    @DisplayName("Minivan stays offered while cars are unavailable")
    fun testMinivanStaysOfferedWhileUnavailable() {
        step("Search a destination") {
            map.searchDestination(TestData.DESTINATION)
        }
        step("Enable car unavailability") {
            ConditionControl.enable("car_unavailble")
        }
        step("Minivan is still offered on the tariff list") {
            map.assertRideOptionShown(TestData.MINIVAN_RIDE_ID)
        }
        step("The other tariffs remain selectable") {
            map.assertTariffAvailable(1)
            map.assertTariffAvailable(2)
        }
    }
}
