package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.common.config.PasswordEncoder;
import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.common.exception.ExceptionType;
import com.musubi.teammusubi.common.exception.ResponseException;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRoleEnum role;

    @Column(nullable = false)
    private boolean deActive = false;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public void setPassword(String password, PasswordEncoder passwordEncoder) {
        validatePassword(password);
        this.password = passwordEncoder.encode(password);
    }

    private void validatePassword(String password) {
        if (!Pattern.matches(PASSWORD_PATTERN, password)) {
            throw new ResponseException(ExceptionType.PASSWORD_DENIED);
        }
    }

    public void checkPasswordMatch(String password, String passwordCheck) throws ResponseException {
        if (!password.equals(passwordCheck)) {
            throw new ResponseException(ExceptionType.PASSWORD_MISMATCH);
        }
    }

    public void checkEmailDuplicate(String email, MemberRepository memberRepository) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new ResponseException(ExceptionType.EMAIL_IN_USE);
        }
    }

    public static Member findByEmailOrThrow(String email, MemberRepository memberRepository) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseException(ExceptionType.USER_NOT_FOUND));
    }

    public void deActiveAccount() {
        this.deActive = true;
    }
}
