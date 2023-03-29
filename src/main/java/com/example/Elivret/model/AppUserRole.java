package com.example.Elivret.model;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
  ROLE_ADMIN,
  Maitre_apprentissage,
  Tuteur,
  Alternant;

  public String getAuthority() {
    return name();
  }

}
