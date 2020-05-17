use rsocket_rust_messaging::Requester;
use crate::common::{HelloUser, HelloToken, HelloRequest, HelloResponse};

//const mineType := "message/x.rsocket.authentication.bearer.v0";

pub struct RequestCoon {
    pub requester: Requester
}

impl RequestCoon {
    pub async fn new() -> RequestCoon {
        RequestCoon {
            requester: Requester::builder()
                .connect_tcp("127.0.0.1", 7878)
                .build()
                .await
                .expect("Connect failed!")
        }
    }

    pub async fn sign_in(&self, admin: HelloUser) -> HelloToken {
        let res = self.requester.route("signin.v1")
            .data(admin)
            .retrieve_mono()
            .await
            .block()
            .expect("SignIn Error")
            .expect("Tokens is not existed");
        println!("SignIn << [Request-Response]: {:#?}", res);
        return res;
    }

    pub async fn refresh(&self, token: String) -> HelloToken {
        let res = self.requester.route("refresh.v1")
            .data(token)
            .retrieve_mono()
            .await
            .block()
            .expect("Hire Error")
            .expect("No result returns");
        println!("ReFresh << [Request-Response]: {:#?}", res);
        return res;
    }

    pub async fn hire(&self, token: String, request: HelloRequest) -> HelloResponse {
        let res = self.requester.route("hire.v1")
            .metadata_raw(token, "message/x.rsocket.authentication.bearer.v0")
            .data(request)
            .retrieve_mono()
            .await
            .block()
            .expect("Hire Error")
            .expect("No result returns");
        println!("Hire << [Request-Response]: {:#?}", res);
        return res;
    }

    pub async fn info(&self, token: String, request: String) -> HelloResponse {
        let res = self.requester.route("info.v1")
            .metadata_raw(token, "message/x.rsocket.authentication.bearer.v0")
            .data(request)
            .retrieve_mono()
            .await
            .block()
            .expect("Hire Error")
            .expect("No result returns");
        println!("Info << [Request-Response]: {:#?}", res);
        return res;
    }

    pub async fn fire(&self, token: String, request: HelloRequest) -> HelloResponse {
        let res = self.requester.route("fire.v1")
            .metadata_raw(token, "message/x.rsocket.authentication.bearer.v0")
            .data(request)
            .retrieve_mono()
            .await
            .block()
            .expect("Hire Error")
            .expect("No result returns");
        println!("Fire << [Request-Response]: {:#?}", res);
        return res;
    }

    /*pub async fn list(&self, token: String) {
        let res = self.requester.route("list.v1")
            .metadata_raw(token, "message/x.rsocket.authentication.bearer.v0")
            .retrieve_flux()
            .await
            .block()
            .expect("Hire Error")
            .expect("No result returns");
        println!("List << [Request-Stream]: {:#?}", res);
    }

    pub async fn sign_out(&self, token: String) {
        let result = self.requester.route("signout.v1")
            .metadata_raw(token, "message/x.rsocket.authentication.bearer.v0")
            .await.block();
        println!("SignOut << [Fire-and-Forget]: {:#?}", result);
    }*/
}