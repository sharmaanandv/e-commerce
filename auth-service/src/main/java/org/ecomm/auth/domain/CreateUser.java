package org.ecomm.auth.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class CreateUser {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
