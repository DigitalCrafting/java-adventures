export class KeycloakClient {

    static async getGrant(clientId: string) {
        const url = `http://localhost:8080/realms/apps-developer/protocol/openid-connect/auth?`;
        const queryParams = new URLSearchParams({
            client_id: clientId,
            response_type: "code",
            scope: "openid profile",
            redirect_uri: "http://localhost:8083/callback",
            state: "sasdasdasd"
        }).toString();

        console.log(url + queryParams)
    }

    static async exchangeForAccessToken(clientId: string, clientSecret: string, code: string) {
        const url = 'http://localhost:8080/realms/apps-developer/protocol/openid-connect/token';
        const resp = await fetch(url, {
            method: "POST",
            body: new URLSearchParams({
                grant_type: "authorization_code",
                client_id: clientId,
                client_secret: clientSecret,
                code,
                redirect_uri: "http://localhost:8083/callback",
                scope: "read_custom_scope"
            })
        });
        const body = await resp.json();

        console.dir(body);
    }

    static async getAccessTokenUsingPasswordGrant(clientId: string, clientSecret: string, username: string, password: string) {
        const url = 'http://localhost:8080/realms/apps-developer/protocol/openid-connect/token';
        const resp = await fetch(url, {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                grant_type: "password",
                client_id: clientId,
                client_secret: clientSecret,
                username,
                password,
            })
        });
        const body = await resp.json();
        return body.access_token;
    }

}