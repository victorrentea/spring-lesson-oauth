#!/bin/bash
# Local Keycloak (no Docker) on http://localhost:8180 - admin/admin
cd "$(dirname "$0")"
V=26.8.1
if [ ! -d dist ]; then
  curl -L https://github.com/keycloak/keycloak/releases/download/$V/keycloak-$V.tar.gz | tar xz && mv keycloak-$V dist
fi
mkdir -p dist/data/import && cp realm.json dist/data/import/
export KC_BOOTSTRAP_ADMIN_USERNAME=admin KC_BOOTSTRAP_ADMIN_PASSWORD=admin
exec dist/bin/kc.sh start-dev --http-port=8180 --import-realm --db=dev-mem
