package controllers;

import com.avaje.ebean.Ebean;
import models.App;
import models.User;
import play.mvc.*;

@Security.Authenticated(Secured.class)
public class Provisioning extends Controller {

    public static Result provisioning() {
        User performer = User.findByEmail(Http.Context.current().request().username());
        if (performer.isAdmin)
            return ok(
                    views.html.provisioning.render(User.all(), User.findByEmail(Http.Context.current().request().username()))
            );
        else
            return Results.forbidden();
    }

    public static Result approveApp(String email, Long appId) {
        User performer = User.findByEmail(Http.Context.current().request().username());
        if (performer.isAdmin) {
            User.approveRequestedApp(email, appId);
            return redirect(routes.Provisioning.provisioning());
        } else
            return Results.forbidden();
    }

}
