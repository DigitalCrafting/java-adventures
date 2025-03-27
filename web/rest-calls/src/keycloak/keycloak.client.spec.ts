import {describe, it} from "vitest";
import {KeycloakClient} from "./keycloak.client";
import {env} from "node:process";
import {TestResourceServerClient} from "./test-resource-server.client";

describe("Call to keycloak", () => {
    it ("should run getGrant", () => {
        KeycloakClient.getGrant(env.TEST_CLIENT_ID);
    })

    it("should run exchangeForAccessToken", async () => {
        await KeycloakClient.exchangeForAccessToken(env.TEST_CLIENT_ID, env.TEST_CLIENT_SECRET, "")
    })

    it("should call userStatusCheck endpoint", async () => {
        const token = await KeycloakClient.getAccessTokenUsingPasswordGrant(env.TEST_CLIENT_ID, env.TEST_CLIENT_SECRET, env.TEST_USERNAME, env.TEST_USER_PASS);
        await TestResourceServerClient.callUserStatusCheck(token);
    })

    it("should call token endpoint", async () => {
        const token = await KeycloakClient.getAccessTokenUsingPasswordGrant(env.TEST_CLIENT_ID, env.TEST_CLIENT_SECRET, env.TEST_USERNAME, env.TEST_USER_PASS);
        await TestResourceServerClient.callToken(token);
    })
})