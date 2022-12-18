package ru.sladkkov.gasstation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    @JsonIgnore
    private String password;

    @Size(min = 2, max = 30, message = "The length of the name should be from 2 to 30 characters")
    @NotEmpty(message = "The name should not be empty")
    @Pattern(regexp = "[A-z]+", message = "The user firstName must consist of Latin letters")
    private String firstName;

    @Size(min = 2, max = 30, message = "The length of the surname should be from 2 to 30 characters")
    @NotEmpty(message = "The last name should not be empty")
    @Pattern(regexp = "[A-z]+", message = "The user lastName must consist of Latin letters")
    private String lastName;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private List<Role> roles;

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
