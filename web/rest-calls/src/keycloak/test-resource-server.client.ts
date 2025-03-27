export class TestResourceServerClient {
    static async callUserStatusCheck(accessToken: string) {
        const url = `http://localhost:8081/users/status/check`;
        const resp = await fetch(url, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        })
        console.log(`${resp.status}\n`)
        console.log(`${resp.headers}\n`)
        const body = await resp.text();
        console.log(`${body}\n`)
    }

    static async callToken(accessToken: string) {
        const url = `http://localhost:8081/token`;
        const resp = await fetch(url, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        })
        const body = await resp.text();
        console.dir(body)
    }
}