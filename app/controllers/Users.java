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
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
@Security.Authenticated(Secured.class)
public class Users extends Controller {


   static Form<User> userForm = form(User.class);

    public static Result users() {
       return ok(
               views.html.users.render(User.all(), userForm, User.findByEmail(Http.Context.current().request().username()))
       );
    }

    public static Result deleteUser(Long id){
        User.delete(id);
        return redirect(routes.Users.users());
    }

    public static Result createUser(){
        Form<User> filledForm = userForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    views.html.users.render(User.all(), filledForm, User.findByEmail(Http.Context.current().request().username()))
            );
        } else {
            User.create(filledForm.get());
            return redirect(routes.Users.users());
        }
    }
}
