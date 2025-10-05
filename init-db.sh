#!/bin/bash
set -euo pipefail

: "${POSTGRES_USER:?POSTGRES_USER not set}"
: "${POSTGRES_DB:?POSTGRES_DB not set}"

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<'EOSQL'
CREATE DATABASE chat_test;
GRANT ALL PRIVILEGES ON DATABASE chat_test TO myuser;
EOSQL