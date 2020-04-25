package org.feuyeux.kio.pojo.secure;

import lombok.Data;

/**
 * @author feuyeux@gmail.com
 */
@Data
public class HelloToken {
    private String accessToken;
    private String refreshToken;
}
