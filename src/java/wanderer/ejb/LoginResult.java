/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wanderer.ejb;

/**
 *
 * @author 23675
 */
public class LoginResult {

    String loginResultType;
    String loginURL;

    public LoginResult() {
    }

    public String getLoginResultType() {
        return loginResultType;
    }

    public void setLoginResultType(String loginResultType) {
        this.loginResultType = loginResultType;
        this.loginURL = this.loginResultType + ".xhtml";
    }

    public String getLoginURL() {
        return loginURL;
    }

    public void setLoginURL(String loginURL) {
        this.loginURL = loginURL;
    }

}
