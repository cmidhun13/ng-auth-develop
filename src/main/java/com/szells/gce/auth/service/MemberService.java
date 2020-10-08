package com.szells.gce.auth.service;

import com.szells.gce.auth.domain.Member;
import com.szells.gce.auth.mail.MessageSender;
import com.szells.gce.auth.model.request.ForgotEmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final JdbcTemplate jdbcTemplate;
    private final MessageSender messageSender;

    public void sendEmail(Long memberId,String hashcode) {
        jdbcTemplate.query("select email_id, CONCAT(fname,' ',lname), hash_cd from member where member_id = ?", (rs, rowNum) -> {
            return messageSender.send(rs.getString(1), rs.getString(2),hashcode);
        }, memberId);
    }

    public Member getUserId(ForgotEmailRequest request) {
        return jdbcTemplate.query("select member_id, email_id from member where fname ~* ? AND lname ~* ?  AND (hash_cd =? OR hash_cd IS NULL)", ps -> {
            ps.setString(1, request.getFirstName());
            ps.setString(2, request.getLastName());
            //ps.setDate(3, Date.valueOf(request.getDob()));
            ps.setString(3, request.getHashCode());
            ps.execute();
        }, rs -> {
            Member member = null;
            while (rs.next()) {
                member = new Member();
                member.setMemberId(rs.getLong(1));
                member.setEmailId(rs.getString(2));
            }
            return member;
        });
    }

    public Long checkIfEmailValid(String email) {
        System.out.println("inside create- Praveen check email");
        return jdbcTemplate.query("select member_id from member where email_id = ?", ps -> {
            ps.setString(1, email);
            ps.execute();
        }, rs -> {
            while (rs.next()) {
                System.out.println("email id is ->"+rs.getLong(1));
                return rs.getLong(1);
            }
            return null;
        });
    }
    public Long getCustomerId(Long memberId) {
        System.out.println("getCustomerId in memership service");
        return jdbcTemplate.query("SELECT customer_id FROM member m join membership ms\n" +
                "on ms.membership_number=m.member_number where m.member_id=?", ps -> {
            ps.setLong(1, memberId);
            ps.execute();
        }, rs -> {
            while (rs.next()) {
                return rs.getLong(1);
            }
            return null;
        });
    }

    public Long findMemberIdByHashCd(String hashCd) {
        return jdbcTemplate.query("select member_id from member where hash_cd = ?", ps -> {
            ps.setString(1, hashCd);
            ps.execute();
        }, rs -> {
            while (rs.next()) {
                return rs.getLong(1);
            }
            return null;
        });
    }
}
