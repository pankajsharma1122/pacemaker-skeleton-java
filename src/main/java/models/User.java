package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.base.Objects;

public class User implements Serializable {

  public String id;
  public String firstname;
  public String lastname;
  public String email;
  public String password;
  public String role = "USER";
  public Boolean enabled = new Boolean(true);
  public List<String> friendList = new ArrayList<>();
  public List<String> friendMessages = new ArrayList<>();

  public Map<String, Activity> activities = new HashMap<>();

  public User() {
  }

  public String getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getEmail() {
    return email;
  }
  

  public String getPassword()
  {
    return password;
  }
  

  public List<String> getFriendList()
  {
    return friendList;
  }

  public void setFriendList(List<String> friendList)
  {
    this.friendList = friendList;
  }

  

  public List<String> getFriendMessages()
  {
    return friendMessages;
  }

  public void setFriendMessages(List<String> friendMessages)
  {
    this.friendMessages = friendMessages;
  }

  public User(String firstName, String lastName, String email, String password) {
    this.id = UUID.randomUUID().toString();
    this.firstname = firstName;
    this.lastname = lastName;
    this.email = email;
    this.password = password;
  }
  
  public User(String firstName, String lastName, String email, String password,String role) {
    this.id = UUID.randomUUID().toString();
    this.firstname = firstName;
    this.lastname = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof User) {
      final User other = (User) obj;
      return Objects.equal(firstname, other.firstname)
          && Objects.equal(lastname, other.lastname)
          && Objects.equal(email, other.email)
          && Objects.equal(password, other.password)
          && Objects.equal(activities, other.activities);
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return toStringHelper(this).addValue(id)
        .addValue(firstname)
        .addValue(lastname)
        .addValue(password)
        .addValue(email)
        .addValue(email)
        .addValue(email)
        .addValue(activities)
        .addValue(friendList)
        .addValue(friendMessages)
        .toString();
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.id, this.lastname, this.firstname, this.email, this.password);
  }

  public String getRole()
  {
    return role;
  }

  public void setRole(String role)
  {
    this.role = role;
  }

  public Boolean getEnabled()
  {
    return enabled;
  }

  public void setEnabled(Boolean enabled)
  {
    this.enabled = enabled;
  }
  
  
}