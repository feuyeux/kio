package main

import (
	"github.com/feuyeux/kio-requester/src/common"
	"github.com/feuyeux/kio-requester/src/requester"
	"log"
)

func main() {
	//log.SetFlags(log.Ldate)
	log.SetFlags(log.Lmicroseconds)
	_, rt := requester.SignIn("9527", "KauNgJikCeo")
	at, _ := requester.Refresh(rt)
	requester.Hire(&common.HelloRequest{Id: "17", Value: "降龍羅漢"}, at)
}
