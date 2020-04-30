alias b := build
alias s := run-responder
alias q := run-requester

build:
    cd {{invocation_directory()}}; mvn clean package -DskipTests -U

run-responder:
    cd {{invocation_directory()}}/kio-responder; mvn spring-boot:run

run-requester:
    cd {{invocation_directory()}}/kio-requester; mvn spring-boot:run

test:
    bash {{invocation_directory()}}/test_kio.sh

system-info:
	@echo "This is an {{arch()}} machine".