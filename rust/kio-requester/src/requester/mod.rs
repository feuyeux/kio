use crate::common::{HelloRequest, HelloResponse, HelloToken, HelloUser};
use rsocket_rust_messaging::Requester;
use std::error::Error;

const MIME_AUTH: &str = "message/x.rsocket.authentication.bearer.v0";

pub async fn sign_in(req: &Requester, admin: HelloUser) -> HelloToken {
    let res: HelloToken = req
        .route("signin.v1")
        .data(admin)
        .retrieve_mono()
        .await
        .block()
        .expect("SignIn Error")
        .expect("Tokens is not existed");
    info!("SignIn << [Request-Response]: {:#?}", res);
    return res;
}

pub async fn refresh(req: &Requester, token: &str) -> HelloToken {
    let res: HelloToken = req
        .route("refresh.v1")
        .data(token.to_owned())
        .retrieve_mono()
        .await
        .block()
        .expect("Refresh failed!")
        .expect("No result returns");
    info!("Refresh << [Request-Response]: {:#?}", res);
    return res;
}

pub async fn hire(req: &Requester, token: &str, request: HelloRequest) {
    debug!("preflight hire: token={}", token);
    let result: Result<Option<HelloResponse>, Box<dyn Error>> = req
        .route("hire.v1")
        .metadata_raw(token, MIME_AUTH)
        .data(request)
        .retrieve_mono()
        .await
        .block();
    match result {
        Ok(v) => info!("Hire << [Request-Response]: {:#?}", v),
        Err(e) => error!("{}", e.to_string())
    }
}

pub async fn info(req: &Requester, token: &str, id: i64) {
    let result: Result<Option<HelloResponse>, Box<dyn Error>> = req
        .route("info.v1")
        .metadata_raw(token, MIME_AUTH)
        .data(id)
        .retrieve_mono()
        .await
        .block();
    match result {
        Ok(v) => info!("Info << [Request-Response]: {:#?}", v),
        Err(e) => error!("{}", e.to_string())
    }
}

pub async fn list(req: &Requester, token: &str) {
    let res: Vec<HelloResponse> = req
        .route("list.v1")
        .metadata_raw(token, MIME_AUTH)
        .retrieve_flux()
        .block()
        .await
        .expect("List Error");
    info!("List << [Request-Response]: {:#?}", res);
}

pub async fn fire(req: &Requester, token: &str, request: HelloRequest) {
    let res: HelloResponse = req
        .route("fire.v1")
        .metadata_raw(token, MIME_AUTH)
        .data(request)
        .retrieve_mono()
        .await
        .block()
        .expect("Fire Error")
        .expect("No result returns");
    info!("Fire << [Request-Response]: {:#?}", res);
}

pub async fn sign_out(req: &Requester, token: &str) {
    req.route("signout.v1")
        .metadata_raw(token, MIME_AUTH)
        .retrieve()
        .await
        .expect("SignOut failed!");
    info!("SignOut << [Fire-And-Forget]");
}
