package com.dxunited.core.auth.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Member {
    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email_id")
    private String emailId;
}
