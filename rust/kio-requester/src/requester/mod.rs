use rsocket_rust_messaging::Requester;
use rsocket_rust::prelude::Client;
use rsocket_rust::runtime::DefaultSpawner;
use crate::common::{HelloUser, HelloToken, HelloRequest};

//const mineType := "message/x.rsocket.authentication.bearer.v0";

pub async fn sign_in(req: &Requester<Client<DefaultSpawner>>, admin: &HelloUser) -> HelloToken {
    let mut req = req.route("signin.v1");
    req.data(&admin).unwrap();
    //Result<Option<T>, Box<dyn Error>>
    let res: HelloToken = req
        .retrieve_mono()
        .await
        .block()
        .expect("SignIn Error")
        .expect("Tokens is not existed");
    println!("SignIn << [Request-Response]: {:#?}", res);
    return res;
}

pub async fn hire(req: &Requester<Client<DefaultSpawner>>, token: &String, request: &HelloRequest) {
    let mut req = req.route("hire.v1");
    req.metadata_raw(token, "message/x.rsocket.authentication.bearer.v0").unwrap();
    req.data(request).unwrap();
    let res: HelloToken = req
        .retrieve_mono()
        .await
        .block()
        .expect("Hire Error")
        .expect("No result returns");
    println!("Hire << [Request-Response]: {:#?}", res);
}

pub async fn info(req: &Requester<Client<DefaultSpawner>>, token: &String, request: &String) {
    let mut req = req.route("info.v1");
    req.metadata_raw(token, "message/x.rsocket.authentication.bearer.v0").unwrap();
    req.data(request).unwrap();
    let res: HelloToken = req
        .retrieve_mono()
        .await
        .block()
        .expect("Hire Error")
        .expect("No result returns");
    println!("Hire << [Request-Response]: {:#?}", res);
}

pub async fn list(req: &Requester<Client<DefaultSpawner>>, token: &String) {
    let mut req = req.route("list.v1");
    req.metadata_raw(token, "message/x.rsocket.authentication.bearer.v0").unwrap();
    let res: HelloToken = req
        .retrieve_mono()
        .await
        .block()
        .expect("Hire Error")
        .expect("No result returns");
    println!("Hire << [Request-Response]: {:#?}", res);
}

pub async fn fire(req: &Requester<Client<DefaultSpawner>>, token: &String, request: &String) {
    let mut req = req.route("fire.v1");
    req.metadata_raw(token, "message/x.rsocket.authentication.bearer.v0").unwrap();
    req.data(request).unwrap();
    let res: HelloToken = req
        .retrieve_mono()
        .await
        .block()
        .expect("Hire Error")
        .expect("No result returns");
    println!("Hire << [Request-Response]: {:#?}", res);
}

pub async fn refresh(req: &Requester<Client<DefaultSpawner>>, token: &String) {
    let mut req = req.route("refresh.v1");
    req.data(token).unwrap();
    let res: HelloToken = req
        .retrieve_mono()
        .await
        .block()
        .expect("Hire Error")
        .expect("No result returns");
    println!("Hire << [Request-Response]: {:#?}", res);
}

pub async fn sign_out(req: &Requester<Client<DefaultSpawner>>, token: &String) {
    let mut req = req.route("signout.v1");
    req.metadata_raw(token, "message/x.rsocket.authentication.bearer.v0")
        .unwrap();
    let res: HelloToken = req
        .retrieve_mono()
        .await
        .block()
        .expect("SignOut Error")
        .expect("No result returns");
    println!("Hire << [Request-Response]: {:#?}", res);
}
