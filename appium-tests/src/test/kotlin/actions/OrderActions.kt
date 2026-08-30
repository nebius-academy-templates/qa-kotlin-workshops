package actions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import pages.MapPage
import pages.OrderHistoryPage

object OrderActions {
    /** Asserts each expected order price on the history screen ("29.70 €" - no tilde). */
    fun assertHistoryPrices(expected: Map<Int, String>) {
        OrderHistoryPage.title.waitFor(15)
        expected.forEach { (id, price) ->
            assertEquals(price, OrderHistoryPage.orderPrice(id).text, "price of order $id")
        }
    }

    fun assertHistoryRoutes(expected: Map<Int, String>) {
        OrderHistoryPage.title.waitFor(15)
        expected.forEach { (id, route) ->
            assertEquals(route, OrderHistoryPage.orderRoute(id).text, "route of order $id")
        }
    }

    /** Asserts the inline history load error (shown instead of the list; no retry control here). */
    fun assertHistoryError(expected: String) {
        OrderHistoryPage.title.waitFor(15)
        assertEquals(expected, OrderHistoryPage.errorLabel.text, "order history error")
    }

    fun assertOrderPrice(
        orderId: Int,
        expected: String,
    ) {
        OrderHistoryPage.title.waitFor(15)
        assertEquals(expected, OrderHistoryPage.orderPrice(orderId).text, "price of order $orderId")
    }

    private var rememberedPrice: String = ""

    /** Keeps the fare a completed ride reported, for a later history comparison. */
    fun rememberCompletedPrice(price: String) {
        rememberedPrice = price
    }

    fun assertHistoryPriceMatchesRemembered(orderId: Int) {
        OrderHistoryPage.title.waitFor(15)
        assertEquals(
            rememberedPrice,
            OrderHistoryPage.orderPrice(orderId).text,
            "price of order $orderId",
        )
    }

    fun assertOrderAbsent(orderId: Int) {
        OrderHistoryPage.title.waitFor(15)
        assertFalse(OrderHistoryPage.orderPrice(orderId).isPresent(), "order $orderId should be absent")
    }

    fun returnToRideForm() {
        OrderHistoryPage.backButton.click()
        MapPage.destinationField.waitFor()
        MapPage.pullToRefresh.waitFor()
        assertFalse(MapPage.drawerOrdersItem.isPresent(), "side drawer should be closed")
    }
}
