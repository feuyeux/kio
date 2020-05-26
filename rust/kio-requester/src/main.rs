#[macro_use]
extern crate log;
extern crate chrono;

use kio::common::*;
use kio::requester::*;
use rsocket_rust_messaging::*;

fn init_log() {
    use chrono::Local;
    use std::io::Write;
    let env = env_logger::Env::default().filter_or(env_logger::DEFAULT_FILTER_ENV, "info");
    env_logger::Builder::from_env(env)
        .format(|buf, record| {
            /* chrono-0.4.11/src/format/strftime.rs */
            let fmt = "%H:%M:%S%.6f";
            writeln!(
                buf,
                "{} {} [{}] {}",
                Local::now().format(fmt),
                record.level(),
                record.module_path().unwrap_or("<unnamed>"),
                &record.args()
            )
        })
        .init();
}

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

    hire(&req, &tokens.access_token, hello).await;
    info(&req, &tokens.access_token, 1i64).await;

    let hello = HelloRequest {
        id: 17,
        value: "降龍羅漢".to_owned(),
    };
    fire(&req, &tokens.access_token, hello).await;
    list(&req, &tokens.access_token).await;

    sign_out(&req, &tokens.access_token).await;

    // Always failed after sign out
    info(&req, &tokens.access_token, 1i64).await;

    let user = HelloUser {
        user_id: "0000".to_owned(),
        password: "Zero4".to_owned(),
        role: None,
    };
    let tokens = sign_in(&req, user).await;
    let tokens2 = refresh(&req, &tokens.refresh_token).await;
    info(&req, &tokens2.access_token, 1i64).await;
    let hello = HelloRequest {
        id: 17,
        value: "降龍羅漢".to_owned(),
    };
    hire(&req, &tokens2.access_token, hello).await;
}
