package controllers;

import io.javalin.Javalin;

public class RestMain {

  public static void main(String[] args) throws Exception {
    Javalin app = Javalin.create();
    app.port(getAssignedPort());
    app.start();
    PacemakerRestService service = new PacemakerRestService();
    configRoutes(app, service);
  }

  static void configRoutes(Javalin app, PacemakerRestService service) {

    app.get("/users", ctx -> {
      service.listUsers(ctx);
    });
    
    app.post("/users", ctx -> {
      service.createUser(ctx);
    });
    
    app.get("/users/:id", ctx -> {
      service.listUser(ctx);
    });
    
    app.get("/users/:id/activities", ctx -> {
      service.getActivities(ctx);
    });
    
    

    app.post("/users/:id/activities", ctx -> {
      service.createActivity(ctx);
    });
    
    app.get("/users/:id/activity/:activityId", ctx -> {
      service.getActivity(ctx);
    });
    
    app.get("/users/:id/activities/:sortBy", ctx -> {
      service.listActivities(ctx);
    });
    
    app.get("/users/:id/activities/:activityId/locations", ctx -> {
      service.getActivityLocations(ctx);
    });

    app.post("/users/:id/activities/:activityId/locations", ctx -> {
      service.addLocation(ctx);
    });
    
    app.post("/users/:id/friends/:friendId/follow", ctx -> {
      service.followAFriend(ctx);
    });
    app.post("/users/:id/friends/:friendId/unfollow", ctx -> {
      service.unfollowAFriend(ctx);
    });
    
    
    app.get("/users/:id/friends", ctx -> {
      service.listAllFriends(ctx);
    });
    
    app.delete("/users", ctx -> {
      service.deleteUsers(ctx);
    });

    app.delete("/users/:id", ctx -> {
      service.deleteUser(ctx);
    });
    app.delete("/users/:id/activities", ctx -> {
      service.deleteActivities(ctx);
    });
    
    app.post("/users/:id/friends/:friendEmail/message", ctx -> {
      service.messageAFriend(ctx);
    });
    
    app.post("/users/:id/friends/message", ctx -> {
      service.messageAllFriend(ctx);
    });
    
    app.get("/users/:id/messages", ctx -> {
      service.listAllMessages(ctx);
    });
    
    app.post("/users/:id/status/:status", ctx -> {
      service.updateActivationStatus(ctx);
    });
    
    app.post("/users/:id/roles/:role", ctx -> {
      service.updateRole(ctx);
    });
    
  }
  
  private static int getAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 7000;
  }
}