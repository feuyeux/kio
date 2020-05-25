#[macro_use]
extern crate log;

use kio::common::*;
use kio::requester::*;
use rsocket_rust_messaging::*;

// Initialize logger
fn init_log() {
    env_logger::builder().format_timestamp_millis().init();
}

// Example of basic kio functionalities.
//
// Run as below:
// $ cargo run --example request
//
// Or if you want debug:
// $ RUST_LOG=debug cargo run --example request
#[tokio::main]
async fn main() {
    init_log();
    let req = Requester::builder()
        .connect_tcp("127.0.0.1", 7878)
        .build()
        .await
        .expect("Connect failed!");

    info!("+++ Connect Success! +++");

    let admin = HelloUser {
        user_id: "9527".to_owned(),
        password: "KauNgJikCeo".to_owned(),
        role: None,
    };

    let tokens = sign_in(&req, admin).await;
    let hello = HelloRequest {
        id: 17,
        value: "降龍羅漢".to_owned(),
    };

    // unsupported message/x.rsocket.composite-metadata.v0 from metadata
    hire(&req, &tokens.access_token, hello).await;
    // no api to handle the result from stream
    list(&req, &tokens.access_token).await;
}
