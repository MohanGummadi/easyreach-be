# EasyReach Backend

## Running tests

```bash
mvn -q clean verify
```

The JaCoCo coverage report is generated at:
`target/site/jacoco/index.html`

## API contract tools

Refresh the OpenAPI specification before running the API tests:

```bash
scripts/fetch-openapi.sh
```

Generate a Postman collection from the spec:

```bash
scripts/generate-postman.sh
```

Run fast smoke tests with Newman (set `BASE_URL` and `TOKEN` as needed):

```bash
scripts/newman-smoke.sh
```
