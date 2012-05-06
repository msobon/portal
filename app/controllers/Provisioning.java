package controllers;

import models.App;
import models.User;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class Provisioning extends Controller {

    public static Result provisioning(){
        return ok(
                views.html.provisioning.render(User.all(), User.findByEmail(Http.Context.current().request().username()))
        );
    }

    public static Result approveApp(String userId, Long appId){
        //TODO spr czy admin
        User user = User.findByEmail(userId);
        App requestedApp = App.find.ref(appId);
        user.requestedApps.remove(requestedApp);
        user.saveManyToManyAssociations("requestedApps");

        user.userApps.add(requestedApp);

        return redirect(routes.Provisioning.provisioning());
    }

}
