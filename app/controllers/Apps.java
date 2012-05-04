package controllers;

import models.App;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 03.05.12
 * Time: 12:59
 * To change this template use File | Settings | File Templates.
 */
@Security.Authenticated(Secured.class)
public class Apps extends Controller {

    static Form<App> appForm = form(App.class);

    public static Result apps() {
        Http.Context.current.get().request().username();
        return ok(
                views.html.apps.render(App.all(), appForm, User.findByEmail(Http.Context.current().request().username()) )
        );
    }

    public static Result deleteApp(Long id){
        App.delete(id);
        return redirect(routes.Apps.apps());
    }

    public static Result createApp(){
        Form<App> filledForm = appForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    views.html.apps.render(App.all(), filledForm, User.findByEmail(Http.Context.current().request().username()))
            );
        } else {
            App.create(filledForm.get());
            return redirect(routes.Apps.apps());
        }
    }
}
