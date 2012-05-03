package controllers;


import models.App;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;



/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 03.05.12
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class Users extends Controller {


   static Form<User> userForm = form(User.class);

    public static Result users() {
       return ok(
               views.html.users.render(User.all(), userForm)
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
                    views.html.users.render(User.all(), filledForm)
            );
        } else {
            User.create(filledForm.get());
            return redirect(routes.Users.users());
        }
    }
}
