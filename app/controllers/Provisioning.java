package controllers;

import com.avaje.ebean.Ebean;
import models.App;
import models.User;
import play.Logger;
import play.libs.F;
import play.libs.WS;
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

            String appUrl = App.find.ref(appId).uri;
            String username = User.findByEmail(email).name;

            WS.url(appUrl + "/adduser/" + email + "/" + username).get().map(
                    new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            Logger.debug("User created in app");

                            return ok("User created in app");
                        }
                    });

            return redirect(routes.Provisioning.provisioning());
        } else
            return Results.forbidden();
    }

}
