use rsocket_rust::prelude::RSocketFactory;
use rsocket_rust_messaging::*;
use rsocket_rust_transport_tcp::TcpClientTransport;
use kio::common::*;
use tokio::runtime::Runtime;

fn main() {
    let mut client_runtime = Runtime::new().unwrap();
    client_runtime.block_on(run_request());
}

async fn run_request() {
    let socket = RSocketFactory::connect()
        .transport(TcpClientTransport::from("tcp://127.0.0.1:7878"))
        .data_mime_type("application/json")
        .metadata_mime_type("message/x.rsocket.composite-metadata.v0")
        .start()
        .await
        .expect("Connect failed!");
    let requester = Requester::from(socket);

    let admin = HelloUser { user_id: "9527".to_owned(), password: "KauNgJikCeo".to_owned(), role: "".to_owned() };

    let mut req = requester.route("signin.v1");
    req.data(&admin).unwrap();
    let res: HelloToken = req
        .retrieve_mono()
        .await
        .block()
        .expect("Retrieve failed!")
        .expect("Empty result!");

    println!("------> RESPONSE: {:?}", res);
}
