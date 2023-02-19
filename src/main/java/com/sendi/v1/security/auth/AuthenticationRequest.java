package com.sendi.v1.security.auth;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthenticationRequest {
    private String email;
    private String password;
}
