package controllers;

import io.javalin.Context;
import models.Activity;
import models.Location;
import models.User;

import static models.Fixtures.users;

import java.util.Collection;

public class PacemakerRestService {

  PacemakerAPI pacemaker = new PacemakerAPI();

  PacemakerRestService() {
    users.forEach(
        user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password,user.role));
  }

  public void listUsers(Context ctx) {
    System.out.println("***list users requested :: listUsers***");
    ctx.json(pacemaker.getUsers());
    
  }
  
  public void createUser(Context ctx) {
    User user = ctx.bodyAsClass(User.class);
    User newUser = pacemaker
        .createUser(user.firstname, user.lastname, user.email, user.password);
    ctx.json(newUser);
  }
  
  public void listUser(Context ctx) {
    String id = ctx.param("id");
    System.out.println("id in listUser ::" + id);
    ctx.json(pacemaker.getUser(id));
  }
  
  public void getActivities(Context ctx) {
    String id = ctx.param("id");
    System.out.println("id in getActivities ::" + id);
    User user = pacemaker.getUser(id);
    if (user != null) {
      ctx.json(user.activities.values());
    } else {
      ctx.status(404);
    }
  }

  public void createActivity(Context ctx) {
    String id = ctx.param("id");
    User user = pacemaker.getUser(id);
    System.out.println("ID in createActivity ::" + id);
    if (user != null) {
      Activity activity = ctx.bodyAsClass(Activity.class);
      Activity newActivity = pacemaker
          .createActivity(id, activity.type, activity.location, activity.distance);
      ctx.json(newActivity);
    } else {
      ctx.status(404);
    }
  }

  public void getActivity(Context ctx)
  {
    
    String id = ctx.param("id");
    String activityId = ctx.param("activityId");
    System.out.println("ID ::" + id);
    System.out.println("activityId in getActivity ::" + id);
    //User user = pacemaker.getUser(id);
    Activity activity = pacemaker.getActivity(activityId);
    if (null != activity) {
      ctx.json(activity);
    } else {
      ctx.status(404);
    }
    
  }
  
  public void addLocation(Context ctx) {
    String id = ctx.param("activityId");
    System.out.println("activityId in addLocation ::" + id);
    Activity activity = pacemaker.getActivity(id);
    if (activity != null) {
    Location location = ctx.bodyAsClass(Location.class);
      activity.route.add(location);
      ctx.json(location);
    } else {
      ctx.status(404);
    }
  }
  
  public void getActivityLocations(Context ctx) {
    String id = ctx.param("activityId");
    System.out.println("id in getActivityLocations ::" + id);
    Activity activity = pacemaker.getActivity(id);
    if (activity != null) {
      ctx.json(activity.route);
    } else {
      ctx.status(404);
    }
  }
  
  public void deleteUser(Context ctx) {
    String id = ctx.param("id");
    System.out.println("ID in deleteUser::>" + id);
    User user = pacemaker.deleteUser(id);
    if (null != user) {
      ctx.json(user);
    } else {
      ctx.status(404);
    }
    
  }

  public void deleteUsers(Context ctx) {
    pacemaker.deleteUsers();
    System.out.println(" in deleteUser::>");
    ctx.json(204);
  }
  public void deleteActivities(Context ctx) {
    String id = ctx.param("id");
    pacemaker.deleteActivities(id);
    ctx.json(204);
  }

  public void followAFriend(Context ctx)
  {
    String loggedUserEmail = ctx.param("id");
    String friendEmail = ctx.param("friendId");
    if (null != loggedUserEmail && null != friendEmail) {
      User user = pacemaker.followAFriend(loggedUserEmail, friendEmail);
      ctx.json(user);
    } else {
      ctx.status(404);
    }
    
  }
  public void unfollowAFriend(Context ctx)
  {
    String loggedUserEmail = ctx.param("id");
    String friendEmail = ctx.param("friendId");
    System.out.println("ID in unfollowAFriend::>" + loggedUserEmail);
    if (null != loggedUserEmail && null != friendEmail) {
      User user = pacemaker.unfollowAFriend(loggedUserEmail, friendEmail);
      ctx.json(user);
    } else {
      ctx.status(404);
    }
    
  }

  public void listAllFriends(Context ctx)
  {
    String id = ctx.param("id");
    System.out.println("ID in listAllFriends::>" + id);
    if (null != id) {
      Collection<User> friends = pacemaker.listAllFriends(id);
      ctx.json(friends);
    } else {
      ctx.status(404);
    }
    
  }
  
  

  
  public void listActivities(Context ctx)
  {
    String id = ctx.param("id");
    String sortBy = ctx.param("sortBy");
    System.out.println("id in listActivities ::" + id);
    if (null != id || null == sortBy) {
      ctx.json(pacemaker.listActivities(id, sortBy));
    } else {
      ctx.status(404);
    }
    
  }
  
  public void messageAFriend(Context ctx)
  {
    String tofriendEmail = ctx.param("friendEmail");
    System.out.println("ID in messageAFriend::>" + tofriendEmail);
    if (null != tofriendEmail) {
      String message = ctx.body();
      User user = pacemaker.messageAFriend(tofriendEmail, message);
      ctx.json(user);
    }  else {
      ctx.status(404);
    }
  }
  public void messageAllFriend(Context ctx)
  {
    String userId = ctx.param("id");
    System.out.println("ID in messageAllFriend::>" + userId);
    if (null != userId) {
      String message = ctx.body();
      User user = pacemaker.messageAllFriend(userId, message);
      ctx.json(user);
    }  else {
      ctx.status(404);
    }
  }
  public void listAllMessages(Context ctx)
  {
    String id = ctx.param("id");
    System.out.println("ID in listAllMessages::>" + id);
    if (null != id) {
      Collection<String> messages = pacemaker.listAllMessages(id);
      ctx.json(messages);
    } else {
      ctx.status(404);
    }
    
  }

  public void updateActivationStatus(Context ctx)
  {
    String id = ctx.param("id");
    String status = ctx.param("status");
    System.out.println("ID in updateActivationStatus::>" + id);
    if (null != id) {
      User user = pacemaker.updateActivationStatus(id,status);
      ctx.json(user);
    } else {
      ctx.status(404);
    }
    
  }

  public void updateRole(Context ctx)
  {
    String id = ctx.param("id");
    String role = ctx.param("role");
    System.out.println("ID in updateRole::>" + id);
    System.out.println("role in updateRole::>" + role);
    if (null != id) {
      User user = pacemaker.updateRole(id,role);
      ctx.json(user);
    } else {
      ctx.status(404);
    }
    
  }
  

  
}