# KIO
kio is a secure io demonstration, for Token-based RSocket Communication.

## Feature

- [x] program language-agnostic interface
- [x] authentication and authorization
- [ ] tracing and accounting
- [ ] kio status code
- [ ] blacklist
- [ ] v1 api

## Design

| api     | description    | interaction model | role          |
| :------ | :------------- | :---------------- | :------------ |
| signin  | take and authenticate principal/credential, sign, save and return Access Token and Refresh Token | Request/Response  | all           |
| signout | take and verify Access Token from Header, revoke it from DB  | Fire-and-Forget   | authenticated |
| refresh | take and verify Refresh Token from payload, sign, save and return Access Token and Refresh Token | Request/Response  | all           |
| info    | take and verify Access Token from Header, authorize and return employee  info | Request/Response  | user,admin    |
| list    | take and verify Access Token from Header, authorize and return list of employee | Request/Stream    | user,admin    |
| hire    | take and verify Access Token from Header, authorize and save new employee | Request/Response  | admin         |
| fire    | take and verify Access Token from Header, authorize and remove the employee | Request/Response  | admin         |

## Workflow

<img src="img/workflow.png" alt="kio_workflow" style="zoom:80%;" />

##### ports

- webflux: http 8989
- rsocket: tcp  7878

## Development
- [x] Java/SpringBoot
- [ ] Golang
- [ ] Rust
- [ ] Nodejs

## Build
```bash
bash build.sh
```

## Run
```bash
bash run_responder.sh
```

```bash
bash run_requester.sh
```

## Test
```bash
curl_test.sh
```
