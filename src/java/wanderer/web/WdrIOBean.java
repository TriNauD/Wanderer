/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wanderer.web;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author 23675
 */
@Named
@RequestScoped
public class WdrIOBean {

    @EJB
    private wanderer.ejb.facade.CityFacade cityFacade;
    @EJB
    private wanderer.ejb.facade.SpotFacade spotFacade;
    @EJB
    private wanderer.ejb.facade.UserFacade userFacade;
    String inputString;
    boolean isLogin = false;//默认没有登录
    String styleStr = "display: inline";//默认显示注册和登录按钮
    String styleStrUserName = "display: none";
    String loginValue = "Log in";

    //刷新登录状态
    //如果没有登录，显示注册和登录图标
    //如果已经登录，显示用户名
    public void flushLogStatus() {
        System.out.println("wanderer.web.WdrIOBean.flushLogStatus()");
        isLogin = userFacade.isLoginStatus();//从ejb拿到登录状态
        if (isLogin == false) {
            //如果没有登录
            System.out.println("wanderer.web.WdrIOBean.flushLogStatus() isLogin:" + isLogin);
            styleStr = "display: inline";
            styleStrUserName = "display: none";
        } else {
            System.out.println("wanderer.web.WdrIOBean.flushLogStatus() isLogin:" + isLogin);
            //如果有登录
            styleStr = "display: none";
            styleStrUserName = "display: inline";
        }
    }

    //搜索函数，输入存在类里，输出跳转的页面url
    public String processSearch() {
        System.out.println("wanderer.web.WdrIOBean.processSearch()");
        System.out.println("wanderer.web.WdrIOBean.processSearch() inputString:" + inputString);

        if (cityFacade.getCityListByName(inputString).size() > 0) {
            System.out.println("wanderer.web.WdrIOBean.processSearch() goto city.");
            return "city.xhtml";
        } else if (spotFacade.getSpotListByName(inputString).size() > 0) {
            System.out.println("wanderer.web.WdrIOBean.processSearch() goto spot.");
            return "spot.xhtml";
        } else {
            System.out.println("wanderer.web.WdrIOBean.processSearch() goto nothing.");
            return "error.xhtml";
        }
    }
    
    public String getLoginValue() {
        return loginValue;
    }

    public void setLoginValue(String loginValue) {
        this.loginValue = loginValue;
    }

    public String getStyleStrUserName() {
        return styleStrUserName;
    }

    public void setStyleStrUserName(String styleStrUserName) {
        this.styleStrUserName = styleStrUserName;
    }

    public String getStyleStr() {
        return styleStr;
    }

    public void setStyleStr(String styleStr) {
        this.styleStr = styleStr;
    }

    public boolean isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public WdrIOBean getInstance() {
        return this;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }
}
