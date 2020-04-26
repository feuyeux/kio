#!/usr/bin/env bash

test_admin() {
echo "signin as admin:"
curl -s "http://localhost:8989/api/signin?u=9527&p=KauNgJikCeo" | jq
echo

echo "[admin] hire:"
curl "http://localhost:8989/api/hire" \
-H "Content-Type: application/stream+json;charset=UTF-8" \
-d '{"id":"17","value":"降龍羅漢"}'
echo -e "\\n"

echo "[admin] info:"
curl "http://localhost:8989/api/info/17"
echo -e "\\n"

echo "[admin] list:"
curl -s "http://localhost:8989/api/list"| grep data
echo

echo "[admin] fire:"
curl "http://localhost:8989/api/fire" \
-H "Content-Type: application/stream+json;charset=UTF-8" \
-d '{"id":"17","value":"降龙罗汉"}'
echo -e "\\n"

echo "[admin] list:"
curl -s "http://localhost:8989/api/list"| grep data
echo

echo "[admin] signout:"
curl -s "http://localhost:8989/api/signout" -XPOST
echo

echo "[anonymous] info:"
curl "http://localhost:8989/api/info/1"
echo -e "\\n"
}

test_user() {
echo "signin as user"
read accessToken refreshToken < <(echo $(curl -s "http://localhost:8989/api/signin?u=0000&p=Zero4" | jq -r '.accessToken,.refreshToken'))
echo "Access Token  :${accessToken}"
echo -e "Refresh Token :${refreshToken}\\n"

echo "[user] refresh:"
curl -s "http://localhost:8989/api/refresh/${refreshToken}" | jq
echo

echo "[user] info:"
curl "http://localhost:8989/api/info/1"
echo -e "\\n"

echo "[user] list:"
curl -s "http://localhost:8989/api/list" | grep data -c
echo

echo "[user] hire:"
curl -s "http://localhost:8989/api/hire" \
-H "Content-Type: application/stream+json;charset=UTF-8" \
-d '{"id":"18","value":"伏虎羅漢"}' | jq -r ".message"
}

test_admin
test_user