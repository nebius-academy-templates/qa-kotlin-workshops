package tests.workshop

import client.RidesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rule.ApiTestCase
import testdata.ApiTestData

class BrokenRequestRejectionTest : ApiTestCase() {
    @Test
    @DisplayName("A request with no token and no route is rejected")
    fun testBrokenRequestRejected() {
        step("No token, no 'to' param: the API rejects the call") {
            val actual = RidesApi.options(null, ApiTestData.FROM, null)
            assertThat(actual.statusCode).isIn(400, 401)
        }
    }
}
