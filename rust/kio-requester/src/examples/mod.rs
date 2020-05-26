use crate::common::{HelloRequest, HelloToken, HelloUser};
use crate::requester::KioRequester;

pub async fn request_as_user(req: &KioRequester) {
    let user = HelloUser {
        user_id: "0000".to_owned(),
        password: "Zero4".to_owned(),
        role: None,
    };
    let tokens = req.sign_in(user).await;
    let tokens2 = req.refresh(&tokens.refresh_token).await;
    req.info(&tokens2.access_token, 1i64).await;
    let hello = HelloRequest {
        id: 17,
        value: "降龍羅漢".to_owned(),
    };
    req.list(&tokens2.access_token).await;
    req.hire(&tokens2.access_token, hello).await;
}

pub async fn request_as_anonymous(req: &KioRequester, tokens: &HelloToken) {
    req.sign_out(&tokens.access_token).await;
// Always failed after sign out
    req.info(&tokens.access_token, 1i64).await;
}

pub async fn request_as_admin(req: &KioRequester) -> HelloToken {
    let admin = HelloUser {
        user_id: "9527".to_owned(),
        password: "KauNgJikCeo".to_owned(),
        role: None,
    };
    let tokens = req.sign_in(admin).await;
    let hello = HelloRequest {
        id: 17,
        value: "降龍羅漢".to_owned(),
    };
    req.hire(&tokens.access_token, hello).await;
    req.info(&tokens.access_token, 1i64).await;
    let hello = HelloRequest {
        id: 17,
        value: "降龍羅漢".to_owned(),
    };
    req.fire(&tokens.access_token, hello).await;
    req.list(&tokens.access_token).await;
    tokens
}