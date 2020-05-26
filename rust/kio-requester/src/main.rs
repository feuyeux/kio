extern crate chrono;
extern crate log;

use kio::common::HelloToken;
use kio::examples::*;
use kio::requester::*;

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
    let req: KioRequester = KioRequester::new().await;
    let tokens: HelloToken = request_as_admin(&req).await;
    request_as_anonymous(&req, &tokens).await;
    request_as_user(&req).await;
}
