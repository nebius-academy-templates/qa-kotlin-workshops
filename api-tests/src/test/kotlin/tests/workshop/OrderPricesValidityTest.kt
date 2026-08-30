package tests.workshop

import client.OrdersApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rule.ApiTestCase

class OrderPricesValidityTest : ApiTestCase() {
    @Test
    @DisplayName("Every order in history carries a positive price")
    fun testAllOrdersCarryValidPrices() {
        val token = obtainToken()
        step("Fetch history: every price is a positive amount") {
            val actual = OrdersApi.orders(token)
            assertThat(actual.statusCode).isEqualTo(200)
            assertThat(actual.body.orders).anySatisfy { order ->
                assertThat(order.priceCents).isGreaterThan(0)
            }
        }
    }
}
