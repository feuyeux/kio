package org.feuyeux.kio.pojo.secure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author feuyeux@gmail.com
 */
@Data
public class HelloToken {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
