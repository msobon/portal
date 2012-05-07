package controllers;

import com.avaje.ebean.Ebean;
import models.App;
import models.User;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class Provisioning extends Controller {

    public static Result provisioning() {
        return ok(
                views.html.provisioning.render(User.all(), User.findByEmail(Http.Context.current().request().username()))
        );
    }

    public static Result approveApp(String userId, Long appId) {
        //TODO spr czy admin
        User user = User.findByEmail(userId);
        App requestedApp = App.find.ref(appId);
        user.requestedApps.remove(requestedApp);


        if (!user.userApps.contains(requestedApp)) {
            //TODO ie dziala podwojny update relacji many to many
            user.userApps.add(requestedApp);
            user.saveManyToManyAssociations("requestedApps");
        }
        Ebean.saveManyToManyAssociations(user,"userApps");
        return redirect(routes.Provisioning.provisioning());
    }

}
