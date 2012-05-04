package controllers;

import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;


public class Application extends Controller {
  
  public static Result admin() {


          response().setContentType("text/html");
          return ok("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n" +
                  "        \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                  "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">"+
                  "<html>\n" +
                  "<head>\n" +
                  "    <title>Panel administratora</title>\n" +
                  "</head>\n" +
                  "<body>\n" +
                  "<a href=\"/apps\">Aplikacje</a><br>\n" +
                  "<a href=\"/users\">Użytkownicy</a>\n" +
                  "</body>\n" +
                  "</html>\n");

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
    public static Result login() {
        return ok(
                login.render(form(Login.class))
        );
    }

    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(
                    routes.Application.admin()
            );
        }
    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }
  
}