package kv.polyanskiy.realworld.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

  @Column(name = "id")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "username")
  @Size(min = 3, message = "Name has to contain minimum 3 characters")
  private String username;

  @Column(name = "password")
  @Size(min = 8, message = "The password length should be minimum 8 characters")
  private String password;

  @Column(name = "email")
  @Email(message = "Invalid email")
  private String email;

  @Column(name = "bio")
  private String bio;

  @Column(name = "image")
  private String image;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private Set<Following> following;
}
