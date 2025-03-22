import {describe, it} from "vitest";
import {KeycloakClient} from "./keycloak.client";

describe("Call to keycloak", () => {
    it ("should run getGrant", () => {
        KeycloakClient.getGrant("test-app-client");
    })

    it("should run exchangeForAccessToken", async () => {
        await KeycloakClient.exchangeForAccessToken("test-app-client", process.env.TEST_CLIENT_SECRET, "")
    })
})