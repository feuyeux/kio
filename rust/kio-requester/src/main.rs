use kio::common::*;
use kio::requester::*;
use tokio::runtime::Runtime;

fn main() {
    let mut client_runtime = Runtime::new().unwrap();
    client_runtime.block_on(run_request());
}

async fn run_request() {
    let request_coon = RequestCoon::new().await;
    let admin = HelloUser { user_id: "9527".to_owned(), password: "KauNgJikCeo".to_owned(), role: "".to_owned() };
    let tokens = request_coon.sign_in(admin).await;

    let hello = HelloRequest { id: 17, value: "降龍羅漢".to_owned() };
    request_coon.hire(tokens.access_token.clone(), hello).await;

    request_coon.info(tokens.access_token.clone(), "10".to_owned()).await;

    let hello = HelloRequest { id: 17, value: "降龍羅漢".to_owned() };
    request_coon.fire(tokens.access_token.clone(), hello).await;

    // no api to handle the result from stream
    //list(&req, &tokens.access_token).await;
}
