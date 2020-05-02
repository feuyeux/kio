package common

import (
	"encoding/json"
)

type HelloRequest struct {
	Id    int64  `json:"id"`
	Value string `json:"value"`
}
type HelloResponse struct {
	Id    int64  `json:"id"`
	Value string `json:"value"`
}
type HelloToken struct {
	AccessToken  string `json:"accessToken"`
	RefreshToken string `json:"refreshToken"`
}
type HelloUser struct {
	UserId   string `json:"userId"`
	Password string `json:"password"`
	Role     string `json:"role"`
}
type UserToken struct {
	TokenId string    `json:"tokenId"`
	Token   string    `json:"token"`
	User    HelloUser `json:"user"`
}

func ToJson(v interface{}) ([]byte, error) {
	jsonData, err := json.Marshal(v)
	return jsonData, err
}
func FromJson(jsonData []byte, v interface{}) {
	_ = json.Unmarshal(jsonData, &v)
}
