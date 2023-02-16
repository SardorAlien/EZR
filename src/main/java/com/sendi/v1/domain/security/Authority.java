package com.sendi.v1.domain.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;


@Entity(name = "authorities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Authority {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

     private String role;

     @ManyToMany(mappedBy = "authorities")
     private Set<User> users;
}

