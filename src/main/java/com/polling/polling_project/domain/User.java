package com.polling.polling_project.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private Boolean active;
    private Date lastPollTime;
    private Integer votesLimit;

    @ElementCollection(targetClass = EUserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<EUserRole> roles;

    public boolean isAbleToPoll() {
        return
                this.getLastPollTime() == null ||
                        Timestamp.valueOf(LocalDateTime.now().minusMonths(1))
                                .compareTo(this.getLastPollTime()) > 0;
    }

    public boolean isVotesValid(Collection<? extends Integer> votesList) {
        return
                votesList.stream().allMatch(count -> count >= 0) &&
                        votesList.stream().mapToInt(count -> count).sum() <= this.votesLimit;
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
        return this.getActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}
