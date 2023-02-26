package com.examplatform.gateway.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleIdGenerator")
    @SequenceGenerator(name = "roleIdGenerator", sequenceName = "roleIdSequence", allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleName roleName;

    public Role(RoleName roleName) {
        this.roleName = roleName;
        if (roleName == RoleName.ROLE_STUDENT) {
            this.id = 1L;
        } else {
            this.id = 2L;
        }
    }
}
