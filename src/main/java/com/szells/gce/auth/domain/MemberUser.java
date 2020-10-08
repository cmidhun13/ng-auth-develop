package com.szells.gce.auth.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "member_user")
@Data
public class MemberUser {
    @Id
//    @Column(name = "user_id")
    private String user_id;
    private Integer member_id;
    private Boolean is_active;
    private String created_by;
    private LocalDate created_date;
    private String updated_by;
    private LocalDate updated_date;
    private String correlation_id;
}
