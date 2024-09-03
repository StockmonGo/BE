package com.pda.core.dto;

import com.pda.core.entity.Traveler;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetailDto implements UserDetails {

    private final Traveler traveler;

    public CustomUserDetailDto(Traveler traveler){
        this.traveler = traveler;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) traveler::getRole);

        return collection;
    }

    @Override
    public String getPassword() {
        return traveler.getPassword();
    }

    @Override
    public String getUsername() {
        return traveler.getNickname();
    }


    public Long getId(){
        return traveler.getId();
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
        return true;
    }
}