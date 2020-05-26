use std::str;

use serde::{Deserialize, Serialize};

#[derive(Serialize, Deserialize, Debug)]
pub struct HelloRequest {
    pub id: i64,
    pub value: String,
}

#[derive(Serialize, Deserialize, Debug)]
pub struct HelloResponse {
    pub id: i64,
    pub value: String,
}

#[derive(Serialize, Deserialize, Debug)]
pub struct HelloToken {
    pub access_token: String,
    pub refresh_token: String,
}

#[derive(Serialize, Deserialize, Debug)]
pub struct HelloUser {
    pub user_id: String,
    pub password: String,
    pub role: Option<String>,
}

#[derive(Serialize, Deserialize, Debug)]
pub struct UserToken {
    pub token_id: String,
    pub token: String,
    pub user: HelloUser,
}
