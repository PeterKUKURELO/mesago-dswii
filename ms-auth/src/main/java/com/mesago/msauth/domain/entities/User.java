package com.mesago.msauth.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Entity
@Table(name = "T_MGO_USUARIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password;

    @Column(name = "ESTADO", length = 20)
    private String estado;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WORKER_ID", nullable = false, unique = true)
    private Worker worker;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role userRole = this.worker.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userRole.getName().name());
        return List.of(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "ACTIVO".equalsIgnoreCase(this.estado);
    }

}
