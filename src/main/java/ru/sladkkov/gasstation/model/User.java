package ru.sladkkov.gasstation.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    private Long id;

    @Size(min = 2, max = 30, message = "The length of the name should be from 2 to 30 characters")
    @NotEmpty(message = "The name should not be empty")
    @Pattern(regexp = "[A-z]+", message = "The user firstName must consist of Latin letters")
    private String firstName;

    @Size(min = 2, max = 30, message = "The length of the surname should be from 2 to 30 characters")
    @NotEmpty(message = "The last name should not be empty")
    @Pattern(regexp = "[A-z]+", message = "The user lastName must consist of Latin letters")
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
