use std::error::Error;

use rsocket_rust_messaging::Requester;

use crate::common::{HelloRequest, HelloResponse, HelloToken, HelloUser};

const MIME_AUTH: &str = "message/x.rsocket.authentication.bearer.v0";

pub struct KioRequester {
    pub requester: Requester
}

impl KioRequester {
    pub async fn new() -> KioRequester {
        KioRequester {
            requester: Requester::builder()
                .connect_tcp("127.0.0.1", 7878)
                .build()
                .await
                .expect("Connect failed!")
        }
    }
    pub async fn sign_in(&self, admin: HelloUser) -> HelloToken {
        let res: HelloToken = self.requester
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

    pub async fn refresh(&self, token: &str) -> HelloToken {
        let res: HelloToken = self.requester
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

    pub async fn hire(&self, token: &str, request: HelloRequest) {
        debug!("preflight hire: token={}", token);
        let result: Result<Option<HelloResponse>, Box<dyn Error>> = self.requester
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

    pub async fn info(&self, token: &str, id: i64) {
        let result: Result<Option<HelloResponse>, Box<dyn Error>> = self.requester
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

    pub async fn list(&self, token: &str) {
        let res: Vec<HelloResponse> = self.requester
            .route("list.v1")
            .metadata_raw(token, MIME_AUTH)
            .retrieve_flux()
            .block()
            .await
            .expect("List Error");
        info!("List << [Request-Response]: {:#?}", res);
    }

    pub async fn fire(&self, token: &str, request: HelloRequest) {
        let res: HelloResponse = self.requester
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

    pub async fn sign_out(&self, token: &str) {
        self.requester.route("signout.v1")
            .metadata_raw(token, MIME_AUTH)
            .retrieve()
            .await
            .expect("SignOut failed!");
        info!("SignOut << [Fire-And-Forget]");
    }
}
