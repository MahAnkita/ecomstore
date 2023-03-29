package com.ankitacodes.Ecom.payloads;

import com.ankitacodes.Ecom.helper.ValidImage;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDto {

    private Long userId;
    @NotBlank
    @Size(min = 2, message = "Username must be min of 2 characters !!")
    private String name;
    @Email(message = "Email address is not valid !!")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @NotBlank(message = "Email is required !!")
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;

    @NotBlank
    @Size(min = 4, max = 6, message = "gender must be min of 4 chars and max of 6 chars !!")
    private String gender;
    @NotBlank
    private String about;
    @ValidImage
    private String imageName;
}
