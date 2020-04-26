package org.feuyeux.kio.pojo.secure;

import lombok.*;

/**
 * @author feuyeux@gmail.com
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class HelloUser {
    private String userId;
    @EqualsAndHashCode.Exclude
    private String password;
    private String role;
}
