package tests.workshop

import io.qameta.allure.AllureId
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rule.AppiumTestCase

@Feature("Onboarding")
class OtpValidationTest : AppiumTestCase() {
    override val startAuthorized = false

    @Test
    @DisplayName("Extra OTP digits beyond four are ignored")
    @AllureId("112")
    fun testOtpInputCapsAtFourDigits() {
        step("Submit a valid phone, land on OTP") {
            onboarding.reachOtp()
        }
        step("Type six digits; the capped code signs in") {
            onboarding.confirmOverflowOtpSignsIn()
        }
    }

    @Test
    @DisplayName("Correct OTP after a wrong attempt signs in")
    @AllureId("113")
    fun testWrongThenCorrectOtpRecovers() {
        step("Enter phone and a wrong OTP, assert the error") {
            onboarding.loginWithWrongOtp()
        }
        step("Clear the code, confirm the valid one") {
            onboarding.recoverWithValidOtp()
        }
    }

    @Test
    @DisplayName("OTP resend is locked by a cooldown")
    @AllureId("114")
    fun testOtpResendLockedByCooldown() {
        step("Submit a valid phone, land on OTP") {
            onboarding.reachOtp()
        }
        step("Assert resend is disabled with a ticking label") {
            onboarding.assertOtpResendLocked()
        }
    }
}
