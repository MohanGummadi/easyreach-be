#!/bin/bash
set -euo pipefail
curl -s http://localhost:8080/v3/api-docs -o api-tests/src/test/resources/openapi.json
