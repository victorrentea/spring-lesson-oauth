# spring-lesson-oauth — OAuth2 / OpenID Connect + SSO, minimal

| port | what | role |
|---|---|---|
| 8180 | Keycloak (realm `lesson`, user `user`/`user`, admin console `admin`/`admin`) | identity provider |
| 8081 | app1 — `GET /` greets you and calls ms with your access token | OAuth2 client |
| 8082 | app2 — `GET /` greets you and calls ms **as itself** (client credentials) | OAuth2 client |
| 8083 | ms — `GET /` greets the owner of the access token | resource server |

## Run
```bash
keycloak/start.sh     # Windows: keycloak\start.cmd. First run downloads Keycloak; realm.json is re-imported on every start
mvn package -DskipTests
java -jar app1/target/app1-1.jar   # same for app2, ms
```
Open http://localhost:8081 → Keycloak login → `app1: Hello user (user@example.com, branch B42)! ms: Hello user!`
Then http://localhost:8082 → no password asked (SSO: Keycloak recognizes its own session cookie) → `app2: Hello user! ms: Hello service-account-app2!`

Every app logs the tokens it holds/sends; ms logs the token it receives.
Paste them in https://jwt.io to look inside.

## Test
```bash
mvn test                   # app2 + ms in isolation, no Keycloak needed
mvn -f e2e/pom.xml test    # login once in app1, then enter app2 without password; needs everything running
```
