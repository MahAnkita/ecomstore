package com.ankitacodes.Ecom.payloads;

import lombok.*;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseDto {


    private Character isActive;
}
