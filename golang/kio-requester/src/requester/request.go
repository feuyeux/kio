package requester

import (
	"context"
	"github.com/feuyeux/kio-requester/src/common"
	"github.com/rsocket/rsocket-go"
	"github.com/rsocket/rsocket-go/payload"
	"log"
)

var client rsocket.Client

func buildClient() rsocket.Client {
	if client == nil {
		client, _ = BuildClient()
	}
	return client
}

func SignIn(principal, credential string) {
	client := buildClient()
	defer client.Close()
	request := &common.HelloUser{UserId: principal, Password: credential}
	json, _ := common.ToJson(request)
	p := payload.New(json, nil)
	result, err := client.RequestResponse(p).Block(context.Background())
	if err != nil {
		log.Println(err)
	}
	data := result.Data()
	var helloToken common.HelloToken
	common.FromJson(data, &helloToken)
	log.Println("<< [Request-Response] access_token:", helloToken.AccessToken, ", refresh_token:", helloToken.RefreshToken)
}
func Refresh(token string)                  {}
func SignOut()                              {}
func Info(id int64)                         {}
func Hire(helloRequest common.HelloRequest) {}
func Fire(helloRequest common.HelloRequest) {}
func List()                                 {}
