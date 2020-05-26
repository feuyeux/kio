package main

import (
	"github.com/feuyeux/kio/src/common"
	"github.com/feuyeux/kio/src/requester"
	"log"
)

func main() {
	//log.SetFlags(log.Ldate)
	log.SetFlags(log.Lmicroseconds)
	at, _ := requester.SignIn("9527", "KauNgJikCeo")
	requester.Hire(&common.HelloRequest{Id: 17, Value: "降龍羅漢"}, at)
	requester.Info(1, at)
	responses := requester.List(at)
	log.Printf("List << [Request-Stream] len=%d", len(responses))
	requester.Fire(&common.HelloRequest{Id: 17, Value: "降龍羅漢"}, at)
	responses = requester.List(at)
	log.Println("List << [Request-Stream]")
	for _, next := range responses {
		log.Println(next)
	}
	requester.SignOut(at)
	requester.Info(1, at)

	_, urt := requester.SignIn("0000", "Zero4")
	uat, _ := requester.Refresh(urt)
	requester.Info(1, uat)
	requester.Hire(&common.HelloRequest{Id: 18, Value: "伏虎羅漢"}, uat)
}
