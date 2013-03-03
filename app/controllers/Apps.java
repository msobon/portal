package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;
import views.html.*;

@Security.Authenticated(Secured.class)
public class Apps extends Controller {

    static Form<App> appForm = form(App.class);

    public static Result apps() {
        return ok(
                apps.render(App.all(), appForm, User.findByEmail(Http.Context.current().request().username()))
        );
    }

    public static Result deleteApp(Long id) {
        User performer = User.findByEmail(Http.Context.current().request().username());
        if (performer.isAdmin) {
            App.delete(id);
            return redirect(routes.Apps.apps());
        } else
            return Results.forbidden();
    }

    public static Result createApp() {
        User performer = User.findByEmail(Http.Context.current().request().username());
        if (performer.isAdmin) {
            Form<App> filledForm = appForm.bindFromRequest();
            if (filledForm.hasErrors()) {
                return badRequest(
                        apps.render(App.all(), filledForm, User.findByEmail(Http.Context.current().request().username()))
                );
            } else {
                App.create(filledForm.get());
                return redirect(routes.Apps.apps());
            }
        } else
            return Results.forbidden();
    }
}
