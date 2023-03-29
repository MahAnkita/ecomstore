package com.ankitacodes.Ecom.payloads;

import com.ankitacodes.Ecom.helper.ValidImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto extends BaseDto{

    private Long catId;
    @Size(min = 4, message = "title must be min of 4 characters !!")
    private String title;
    @NotBlank(message="Description is required")
    private String description;
    @ValidImage
    private String cover_image;
}
