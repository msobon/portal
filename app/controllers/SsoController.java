package controllers;

import models.User;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 21.01.13
 * Time: 20:52
 * To change this template use File | Settings | File Templates.
 */
public class SsoController extends Controller {
    public static Result authenticate(String email, String password) {
        Logger.debug("Authenticating: " + email);
        String authResult= User.authenticate(email, password);
        response().setCookie("ssoToken",authResult);
        return ok(authResult);
    }

    public static Result validate(String email, String token) {
        Logger.debug("Validating token: " + token);

        String result = User.validateToken(email, token) ? "ok" : "err";
        Logger.debug("Validation: " + result);
        return ok(result);
    }

    public static Result logout(String email) {
        Logger.debug("logout: " + email);

        User user = User.findByEmail(email);
        user.ssoToken="";
        user.save();

        response().discardCookies("ssoToken");
        return ok("ok");
    }

}
