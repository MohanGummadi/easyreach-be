#!/bin/bash
set -euo pipefail
mkdir -p postman
npx openapi-to-postmanv2 -s api-tests/src/test/resources/openapi.json -o postman/Easyreach.postman.json -p
