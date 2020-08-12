package com.szells.gce.auth.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "LOGIN_FAILURE_ATTEMPT", schema = "GCE")
@Data
public class LoginFailureAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long attemptId;

    private String tenantId;

    private String memberId;

    private Long failCount = 1L;

    @CreationTimestamp
    private Instant dateCreated;

    @UpdateTimestamp
    private Instant dateModified;
}
