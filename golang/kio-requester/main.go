package main

import (
	"github.com/feuyeux/kio/src/example"
	"log"
)

func main() {
	log.SetFlags(log.Lmicroseconds)
	at := example.RequestAsAdmin()
	example.RequestAsAnonymous(at)
	example.RequestAsUser()
}
