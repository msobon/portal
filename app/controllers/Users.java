package controllers;


import models.App;
import models.User;
import play.api.Logger;
import play.data.Form;
import play.mvc.*;


@Security.Authenticated(Secured.class)
public class Users extends Controller {


    static Form<User> userForm = form(User.class);

    public static Result users() {
        User performer = User.findByEmail(Http.Context.current().request().username());
        if (performer.isAdmin)
            return ok(
                    views.html.users.render(User.all(), userForm, performer)
            );
        else
            return Results.forbidden();
    }

    public static Result userDetails(String email) {
        User performer = User.findByEmail(Http.Context.current().request().username());
        if (performer.isAdmin) {
            User user = User.findByEmail(email);
            return ok(views.html.userDetails.render(performer, user));
        } else return Results.forbidden();
    }

    public static Result requestApp(String userId, Long appId) {
        User user = User.findByEmail(userId);
        App requestedApp = App.find.ref(appId);
        if (!user.requestedApps.contains(requestedApp) && !user.userApps.contains(requestedApp)) {
            user.requestedApps.add(requestedApp);
            //TODO refactor to model
            user.saveManyToManyAssociations("requestedApps");
            //TODO add logger
            System.out.println("request added");
        }
        user.save();
        return redirect(routes.Apps.apps());
    }

    public static Result deleteUser(Long id) {
        User performer = User.findByEmail(Http.Context.current().request().username());
        if (performer.isAdmin) {
            User.delete(id);
            return redirect(routes.Users.users());
        } else
            return Results.forbidden();
    }

    public static Result register() {
        Form<User> filledForm = userForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(
                    views.html.addUser.render(filledForm)
            );
        } else {
            User.create(filledForm.get());
            return redirect(routes.Users.users());
        }
    }

    public static Result createUser() {
        Form<User> filledForm = userForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(
                    views.html.users.render(User.all(), filledForm, User.findByEmail(Http.Context.current().request().username()))
            );
        } else {
            User.create(filledForm.get());
            return redirect(routes.Users.users());
        }
    }
}
