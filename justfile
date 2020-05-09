alias b := build
alias s := run-responder
alias q := run-requester
alias gq := run-golang-requester
alias gq := run-rust-requester

build:
    cd {{invocation_directory()}}; mvn clean install -DskipTests -U

run-responder:
    cd {{invocation_directory()}}/kio-responder; mvn spring-boot:run

run-requester:
    cd {{invocation_directory()}}/kio-requester; mvn spring-boot:run

run-golang-requester:
    cd {{invocation_directory()}}/golang/kio-requester; ./kio

run-rust-requester:
    cd {{invocation_directory()}}/golang/kio-requester; target/release/kio

test:
    bash {{invocation_directory()}}/test_kio.sh

system-info:
	@echo "This is an {{arch()}} machine".