package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import models.Activity;
import models.Location;
import models.User;


public class PacemakerAPI {

  private Map<String, User> emailIndex = new HashMap<>();
  private Map<String, User> userIndex = new HashMap<>();
  private Map<String, Activity> activitiesIndex = new HashMap<>();

  public PacemakerAPI() {
  }

  public Collection<User> getUsers() {//k
    return userIndex.values();
  }

  public void deleteUsers() {//k
    userIndex.clear();
    emailIndex.clear();
  }

  public User createUser(String firstName, String lastName, String email, String password) {//k
    User user = new User(firstName, lastName, email, password);
    emailIndex.put(email, user);
    userIndex.put(user.id, user);
    return user;
  }
  
  protected User createUser(String firstName, String lastName, String email, String password,String role) {
    User user = new User(firstName, lastName, email, password,role);
    emailIndex.put(email, user);
    userIndex.put(user.id, user);
    return user;
  }

  public Activity createActivity(String id, String type, String location, double distance) {//k
    Activity activity = null;
    Optional<User> user = Optional.fromNullable(userIndex.get(id));
    if (user.isPresent()) {
      activity = new Activity(type, location, distance);
      user.get().activities.put(activity.id, activity);
      activitiesIndex.put(activity.id, activity);
    }
    return activity;
  }

  public Activity getActivity(String activityId) {//k
    return activitiesIndex.get(activityId);
  }

  public Collection<Activity> getActivities(String userId) {
    Collection<Activity> activities = null;
    Optional<User> user = Optional.fromNullable(userIndex.get(userId));
    if (user.isPresent()) {
      activities = user.get().activities.values();
    }
    return activities;
  }

  public List<Activity> listActivities(String userId, String sortBy) {
    List<Activity> activities = new ArrayList<>();
    activities.addAll(userIndex.get(userId).activities.values());
    switch (sortBy) {
      case "type":
        activities.sort((a1, a2) -> a1.type.compareTo(a2.type));
        break;
      case "location":
        activities.sort((a1, a2) -> a1.location.compareTo(a2.location));
        break;
      case "distance":
        activities.sort((a1, a2) -> Double.compare(a1.distance, a2.distance));
        break;
    }
    return activities;
  }

  public void addLocation(String id, double latitude, double longitude) {
    Optional<Activity> activity = Optional.fromNullable(activitiesIndex.get(id));
    if (activity.isPresent()) {
      activity.get().route.add(new Location(latitude, longitude));
    }
  }

  public User getUserByEmail(String email) {
    return emailIndex.get(email);
  }

  public User getUser(String id) {
    return userIndex.get(id);
  }

  public User deleteUser(String id) {
    Optional<User> user = Optional.fromNullable(userIndex.get(id));
    User userReturn = null;
    if (user.isPresent()) {
      userReturn = userIndex.remove(id);
      userReturn = emailIndex.remove(userReturn.email);
    }
    return userReturn;
  }

  public List<Location> getLocations(String id)
  {
    List<Location> locations = null;
    Optional<Activity> activity = Optional.fromNullable(activitiesIndex.get(id));
    if (activity.isPresent()) {
      locations = activity.get().route;
    }
    return locations;
  }
  public void deleteActivities(String id) {
    Optional<User> user = Optional.fromNullable(userIndex.get(id));
    if (user.isPresent()) {
      user.get().activities.values().forEach(activity -> activitiesIndex.remove(activity.getId()));
      user.get().activities.clear();
    }
  }
  public User followAFriend(String loggedUserEmail , String friendEmail) {
    Optional<User> friendUser = Optional.fromNullable(emailIndex.get(friendEmail));
    Optional<User> loggedUser = Optional.fromNullable(emailIndex.get(loggedUserEmail));
    if (friendUser.isPresent()) {
      if (!friendUser.get().getFriendList().contains(loggedUserEmail)) {
        friendUser.get().getFriendList().add(loggedUserEmail);
      }
      if (!loggedUser.get().getFriendList().contains(friendEmail)) {
        loggedUser.get().getFriendList().add(friendEmail);
      }
      
      //loggedUser.get().getFriendList().add(friendEmail);
    }
    return loggedUser.get();
  }
  
  public User unfollowAFriend(String loggedUserEmail , String friendEmail) {
    Optional<User> friendUser = Optional.fromNullable(emailIndex.get(friendEmail));
    Optional<User> loggedUser = Optional.fromNullable(emailIndex.get(loggedUserEmail));
    if (friendUser.isPresent()) {
      friendUser.get().getFriendList().remove(loggedUserEmail);
      loggedUser.get().getFriendList().remove(friendEmail);
    }
    return loggedUser.get();
  }
  
  public Collection<User> listAllFriends(String userId) {
    Collection<User> friends = new ArrayList<>();
    Optional<User> user = Optional.fromNullable(userIndex.get(userId));
    if (user.isPresent()) {
      List<String> friendsEmails = user.get().getFriendList();
      System.out.println("friendsEmails>>" + friendsEmails );
      friendsEmails.forEach(email -> friends.add(emailIndex.get(email)));
    }
    return friends;
  }
  
  public User messageAFriend(String friendEmail,String message) {
    Collection<User> friends = null;
    Optional<User> tUser = Optional.fromNullable(emailIndex.get(friendEmail));
    if (tUser.isPresent()) {
      tUser.get().friendMessages.add(message);
    }
    return tUser.get();
  }  
  
  public Collection<String> listAllMessages(String userId) {
    Collection<String> messages = null;
    Optional<User> user = Optional.fromNullable(userIndex.get(userId));
    if (user.isPresent()) {
      messages = user.get().friendMessages;
    }
    return messages;
  } 
  
  
  public User messageAllFriend(String fromUserId,String message) {
    Collection<User> friends = null;
    Optional<User> fromUser = Optional.fromNullable(userIndex.get(fromUserId));
    if (fromUser.isPresent()) {
      fromUser.get().getFriendList().forEach(email -> messageAFriend(email,message));
    }
    return fromUser.get();
  }

  public User updateActivationStatus(String userId, String status)
  {
    User userReturn = null;
    Optional<User> user = Optional.fromNullable(userIndex.get(userId));
    if (user.isPresent()) {
      userReturn = user.get();
      userReturn.setEnabled(((status.equalsIgnoreCase("Y")) ? true : false));
    }
    return userReturn;
  }

  public User updateRole(String userId, String role)
  {
    User userReturn = null;
    Optional<User> user = Optional.fromNullable(userIndex.get(userId));
    if (user.isPresent()) {
      userReturn = user.get();
      userReturn.setRole(role);
    }
    System.out.println(userReturn);
    return userReturn;
  } 
 
  
  
}