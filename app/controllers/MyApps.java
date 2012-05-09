package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class MyApps extends Controller {

    public static Result myApps(){
        User performer = User.findByEmail(Http.Context.current().request().username());

        return ok(
                views.html.myapps.render(performer.userApps, performer)
        );
    }
}
