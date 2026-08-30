package tests.workshop

import client.ApiSpec
import client.toApiResponse
import io.restassured.RestAssured.given
import model.ErrorResponse
import model.RideOptionsResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rule.ApiTestCase
import testdata.ApiTestData

class ForgedTokenRejectionTest : ApiTestCase() {
    @Test
    @DisplayName("A forged bearer token is rejected")
    fun testForgedTokenRejected() {
        step("Send a token the server never issued: HTTP 401 with the shared copy") {
            val actual =
                given()
                    .spec(ApiSpec.request)
                    .header("Authorization", ApiTestData.FORGED_TOKEN)
                    .queryParam("from", ApiTestData.FROM)
                    .queryParam("to", ApiTestData.TO)
                    .get("/rides/options")
                    .toApiResponse(RideOptionsResponse.serializer())
            assertThat(actual.statusCode).isEqualTo(401)
            assertThat(actual.error).isEqualTo(ErrorResponse(ApiTestData.TOKEN_ERROR))
        }
    }
}
