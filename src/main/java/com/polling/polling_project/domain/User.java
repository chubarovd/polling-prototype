package com.polling.polling_project.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "user_table")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private Boolean active;
    private String lastPollTime;
    private Integer votesLimit;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getId() {
        return id;
    }
    public User setId (Long id) {
        this.id = id;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public String getUsername() {
        return username;
    }
    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Boolean isActive() {
        return active;
    }
    public User setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Integer getVotesLimit() {
        return votesLimit;
    }
    public User setVotesLimit(Integer votesLimit) {
        this.votesLimit = votesLimit;
        return this;
    }

    public String getLastPollTime() {
        return lastPollTime;
    }
    public User setLastPollTime(String lastPollTime) {
        this.lastPollTime = lastPollTime;
        return this;
    }

    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    @Override
    public boolean isEnabled () {
        return isActive ();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return getRoles ();
    }
}
