package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.common.config.PasswordEncoder;
import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.common.exception.GlobalException;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

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
    private boolean isDeleted = false;

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
            throw new GlobalException("U0001", HttpStatus.BAD_REQUEST, "비밀번호는 최소 8글자 이상이고, 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다.");
        }
    }

    public void checkPasswordMatch(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new GlobalException("U0001", HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
    }

    public void checkEmailDuplicate(String email, MemberRepository memberRepository) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new GlobalException("U0001", HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다.");
        }
    }

    public static Member findByEmailOrThrow(String email, MemberRepository memberRepository) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException("U0002", HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."));
    }

    public void deactiveAccount() {
        this.isDeleted = true;
    }
}
