package com.ankitacodes.Ecom.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@MappedSuperclass
public  class BaseEntity implements Serializable {

    @Column(name="Is_Active_Switch")
    private Character isActive;
    @Column(name="Created_At", updatable=false)
    @CreationTimestamp
    private LocalDate createdAt;
    @Column(name="Updated_At",insertable=false)
    @UpdateTimestamp
    private LocalDate  updatedAt;
}
