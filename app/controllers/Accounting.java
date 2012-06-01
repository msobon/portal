package controllers;

import models.User;
import play.Logger;
import play.libs.F;
import play.libs.WS;
import play.mvc.Controller;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import play.mvc.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Accounting extends Controller {

    public static Result chargeUser(String email, Long chargeValue, String redirectUrl) {
        User user = User.findByEmail(email);
        user.usedCredits -= chargeValue;
        user.save();
        System.out.println("charged: " + email + "" + chargeValue);

//        HttpClient client = new HttpClient();
//        client.getParams().setParameter("http.useragent", "Test Client");
//
//        BufferedReader br = null;
//
//        PostMethod method = new PostMethod(redirectUrl);
//        method.addParameter("resp", String.valueOf(user.chargeUser(email, chargeValue)));
//
//        try{
//            int returnCode = client.executeMethod(method);
//
//            if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
//                System.err.println("The Post method is not implemented by this URI");
//                // still consume the response body
//                method.getResponseBodyAsString();
//            } else {
//                br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
//                String readLine;
//                while(((readLine = br.readLine()) != null)) {
//                    System.err.println(readLine);
//                }
//            }
//        } catch (Exception e) {
//            System.err.println(e);
//        } finally {
//            method.releaseConnection();
//            if(br != null) try { br.close(); } catch (Exception fe) {}
//        }

        return ok("true");
    }


}
