@echo off
rem Local Keycloak (no Docker) on http://localhost:8180 - admin/admin
cd /d "%~dp0"
set V=26.8.1
if not exist dist (
  curl -L -o kc.tar.gz https://github.com/keycloak/keycloak/releases/download/%V%/keycloak-%V%.tar.gz
  tar -xzf kc.tar.gz && move keycloak-%V% dist && del kc.tar.gz
)
if not exist dist\data\import mkdir dist\data\import
copy /y realm.json dist\data\import\ >nul
set KC_BOOTSTRAP_ADMIN_USERNAME=admin
set KC_BOOTSTRAP_ADMIN_PASSWORD=admin
dist\bin\kc.bat start-dev --http-port=8180 --import-realm --db=dev-mem
