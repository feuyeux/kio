use rsocket_rust_messaging::Requester;
use rsocket_rust::prelude::Client;
use rsocket_rust::runtime::DefaultSpawner;
use crate::common::{HelloUser, HelloToken};

pub async fn sign_in(req: Requester<Client<DefaultSpawner>>, admin: &HelloUser) {
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
}

pub async fn hire(req: Requester<Client<DefaultSpawner>>) {
    let mut req = req.route("hire.v1");
}

pub async fn info(req: Requester<Client<DefaultSpawner>>) {
    let mut req = req.route("info.v1");
}

pub async fn list(req: Requester<Client<DefaultSpawner>>) {let mut req = req.route("list.v1");}

pub async fn fire(req: Requester<Client<DefaultSpawner>>) {let mut req = req.route("fire.v1");}

pub async fn refresh(req: Requester<Client<DefaultSpawner>>) {let mut req = req.route("refresh.v1");}

pub async fn sign_out(req: Requester<Client<DefaultSpawner>>) {
    let mut req = req.route("signout.v1");
}
