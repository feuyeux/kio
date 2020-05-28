package example

import (
	"github.com/feuyeux/kio/src/common"
	"github.com/feuyeux/kio/src/requester"
	"log"
)

func RequestAsAdmin() string {
	at, _ := requester.SignIn("9527", "KauNgJikCeo")
	requester.Hire(at, &common.HelloRequest{Id: 17, Value: "降龍羅漢"})
	requester.Info(at, 1)
	responses := requester.List(at)
	log.Printf("List << [Request-Stream] len=%d", len(responses))
	requester.Fire(at, &common.HelloRequest{Id: 17, Value: "降龍羅漢"})
	responses = requester.List(at)
	log.Println("List << [Request-Stream]")
	for _, next := range responses {
		log.Println(next)
	}
	return at
}

func RequestAsUser() {
	_, urt := requester.SignIn("0000", "Zero4")
	uat, _ := requester.Refresh(urt)
	requester.Info(uat, 1)
	responses := requester.List(uat)
	log.Printf("List << [Request-Stream] len=%d", len(responses))
	requester.Hire(uat, &common.HelloRequest{Id: 18, Value: "伏虎羅漢"})
}

func RequestAsAnonymous(at string) {
	requester.SignOut(at)
	requester.Info(at, 1)
}
