extern crate log;

use env_logger::Env;

use kio::common::*;
use kio::examples::*;
use kio::requester::*;

// Initialize logger
fn init_log() {
    env_logger::from_env(Env::default().default_filter_or("info")).format_timestamp_millis().init();
}

// Example of basic kio functionalities.
//
// Run as below:
// $ RUST_LOG=info cargo run --example request
//
// Or if you want debug:
// $ RUST_LOG=debug cargo run --example request
#[tokio::main]
async fn main() {
    init_log();
    let req: KioRequester = KioRequester::new().await;
    let tokens: HelloToken = request_as_admin(&req).await;
    request_as_anonymous(&req, &tokens).await;
    request_as_user(&req).await;
}
