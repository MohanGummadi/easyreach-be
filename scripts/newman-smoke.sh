#!/bin/bash
set -euo pipefail
BASE_URL=${BASE_URL:-http://localhost:8080}
TOKEN=${TOKEN:-}
newman run postman/Easyreach.postman.json --env-var baseUrl=$BASE_URL --env-var token="$TOKEN"
