package requester

import (
	"context"
	"github.com/feuyeux/kio/src/common"
	"github.com/jjeffcaii/rsocket-messaging-go"
	"github.com/jjeffcaii/rsocket-messaging-go/spi"
	"log"
)

var requester spi.Requester
const mineType = "message/x.rsocket.authentication.bearer.v0"

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
	} else {
		log.Printf("SignIn << [Request-Response]\r\naccess_token:%s\r\nrefresh_token:%s", helloToken.AccessToken, helloToken.RefreshToken)
	}
	return helloToken.AccessToken, helloToken.RefreshToken
}

func Hire(helloRequest *common.HelloRequest, token string) *common.HelloResponse {
	helloResponse := common.HelloResponse{}
	err := requester.
		Route("hire.v1").
		Metadata(token, mineType).
		Data(helloRequest).
		RetrieveMono().
		BlockTo(context.Background(), &helloResponse)
	if err != nil {
		log.Println("Hire Error", err)
	} else {
		log.Printf("Hire << [Request-Response] HelloResponse[%d %s]", helloResponse.Id, helloResponse.Value)
	}
	return &helloResponse
}

func Info(id int64, token string) *common.HelloResponse {
	helloResponse := common.HelloResponse{}
	err := requester.
		Route("info.v1").
		Metadata(token, mineType).
		Data(id).
		RetrieveMono().
		BlockTo(context.Background(), &helloResponse)
	if err != nil {
		log.Println("Hire Error", err)
	} else {
		log.Printf("Info << [Request-Response] HelloResponse[%d %s]", helloResponse.Id, helloResponse.Value)
	}
	return &helloResponse
}

func List(token string) []common.HelloResponse {
	responses := make([] common.HelloResponse, 0)
	err := requester.Route("list.v1").
		Metadata(token, mineType).
		RetrieveFlux().BlockToSlice(context.Background(), &responses)
	if err != nil {
		log.Println("Hire Error", err)
	}
	return responses
}

func Fire(helloRequest *common.HelloRequest, token string) *common.HelloResponse {
	helloResponse := common.HelloResponse{}
	err := requester.
		Route("fire.v1").
		Metadata(token, mineType).
		Data(helloRequest).
		RetrieveMono().
		BlockTo(context.Background(), &helloResponse)
	if err != nil {
		log.Println("Fire Error", err)
	} else {
		log.Printf("Filre << [Request-Response] HelloResponse[%d %s]", helloResponse.Id, helloResponse.Value)
	}
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
	} else {
		log.Printf("Refresh << [Request-Response]\r\naccess_token:%s\r\nrefresh_token:%s", helloToken.AccessToken, helloToken.RefreshToken)
	}
	return helloToken.AccessToken, helloToken.RefreshToken
}

func SignOut(token string) {
	_ = requester.
		Route("signout.v1").
		Metadata(token, mineType).
		Retrieve()
}
