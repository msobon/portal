package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import static play.data.Form.*;

import models.*;
import views.html.*;


public class Application extends Controller {

    static Form<User> userForm = form(User.class);

    // -- Authentication

    public static class Login {

        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }

    /**
     * Login page.
     */
    public static Result login() {
        if (request().cookies().get("ssoToken") != null) {
            String ssoToken = request().cookies().get("ssoToken").value();
            User u = User.findByToken(ssoToken);
            if (u != null) {
                session("email", u.email);
                return redirect(routes.MyApps.myApps());
            }
        }
        return ok(login.render(form(Login.class)));
    }

    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            Logger.debug("invalid login " + loginForm.globalError().toString());
            return badRequest(login.render(loginForm));
        } else {
            User user = User.findByEmail(loginForm.get().email);
            user.generateSSOToken();
            response().setCookie("ssoToken", user.ssoToken);
            session("email", loginForm.get().email);

            Http.Cookie redirectUrlCookie = request().cookie("redirectUrl");
            if (redirectUrlCookie == null) {
                return redirect(routes.MyApps.myApps());
            } else {
                String url = redirectUrlCookie.value();
                response().discardCookie("redirectUrl");  //TODO dodac kontrole redirectow
                Logger.debug("redirecting to:" + url);

                return redirect(url);
            }

        }
    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        Logger.debug("logout: " + session("email"));

        User user = User.findByEmail(session("email"));
        user.ssoToken = "";
        user.save();

        session().clear();
        flash("success", "You've been logged out");
        response().discardCookies("ssoToken");

        return redirect(routes.Application.login());
    }

    public static Result register() {
        //TODO validation
        Form<User> filledForm = userForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(register.render(filledForm));
        } else {
            User.create(filledForm.get());

            return redirect(routes.Application.login());
        }
    }
}
