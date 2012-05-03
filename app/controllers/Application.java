package controllers;

import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;


public class Application extends Controller {
  
  public static Result index() {
      return redirect(routes.Apps.apps());
  }

    // -- Authentication

    public static class Login {

        public String email;
        public String password;

        public String validate() {
            if(User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }

    /**
     * Login page.
     */
//    public static Result login() {
//        return ok(
//                login.render(form(Login.class))
//        );
//    }
//
//    /**
//     * Handle login form submission.
//     */
//    public static Result authenticate() {
//        Form<Login> loginForm = form(Login.class).bindFromRequest();
//        if(loginForm.hasErrors()) {
//            return badRequest(login.render(loginForm));
//        } else {
//            session("email", loginForm.get().email);
//            return redirect(
//                    routes.Projects.index()
//            );
//        }
//    }

    /**
     * Logout and clean the session.
     */
//    public static Result logout() {
//        session().clear();
//        flash("success", "You've been logged out");
//        return redirect(
//                routes.App.login()
//        );
//    }
  
}