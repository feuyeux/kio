package requester

import (
	"context"
	"github.com/feuyeux/kio-requester/src/common"
	"github.com/jjeffcaii/rsocket-messaging-go"
	"github.com/jjeffcaii/rsocket-messaging-go/spi"
	"log"
)

var requester spi.Requester

func init() {
	r, err := messaging.Builder().
		ConnectTCP("127.0.0.1", 7878).
		Build(context.Background())
	if err != nil {
		panic(err)
	}
	requester = r
}

func SignIn(principal, credential string) (string, string) {
	request := &common.HelloUser{UserId: principal, Password: credential}
	helloToken := common.HelloToken{}
	err := requester.
		Route("signin.v1").
		Data(request).
		RetrieveMono().
		BlockTo(context.Background(), &helloToken)
	if err != nil {
		log.Println("SignIn Error", err)
	}
	log.Printf("SignIn << [Request-Response]\r\naccess_token:%s\r\nrefresh_token:%s", helloToken.AccessToken, helloToken.RefreshToken)
	return helloToken.AccessToken, helloToken.RefreshToken
}

func Hire(helloRequest *common.HelloRequest, token string) *common.HelloResponse {
	helloResponse := common.HelloResponse{}
	err := requester.
		Route("hire.v1").
		Metadata(token, "message/x.rsocket.authentication.bearer.v0").
		Data(helloRequest).
		RetrieveMono().
		BlockTo(context.Background(), &helloResponse)
	if err != nil {
		log.Println("Hire Error", err)
	}
	log.Printf("Hire << [Request-Response] HelloResponse:%s", helloResponse)
	return &helloResponse
}

func Refresh(token string) (string, string) {
	log.Println("Refresh >> refresh_token:", token)
	helloToken := common.HelloToken{}
	err := requester.
		Route("refresh.v1").
		Data(token).
		RetrieveMono().
		BlockTo(context.Background(), &helloToken)
	if err != nil {
		log.Println(err)
	}
	log.Printf("Refresh << [Request-Response]\r\naccess_token:%s\r\nrefresh_token:%s", helloToken.AccessToken, helloToken.RefreshToken)
	return helloToken.AccessToken, helloToken.RefreshToken
}

func SignOut()                              {}
func Info(id int64)                         {}
func Fire(helloRequest common.HelloRequest) {}
func List()                                 {}
