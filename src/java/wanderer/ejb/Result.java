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
public class Result {

    public String key;
    String resultType;
    String pageURL;

    public Result() {
        this.resultType = "error";
        this.pageURL = "";
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
        this.pageURL = resultType + ".xhtml";
        //this.pageURL = resultTypeToURL(resultType);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPageURL() {
        return pageURL;
    }

    /*
    *如果city、spot、error这几个xhtml需要更改名字 不能用resultType+".xhtml" 则可以用这个方法
    public String resultTypeToURL(String resultType){
        String URL;
        if ( resultType == "city"){
            URL = "city.xhtml";
        } 
        else if ( resultType == "spot" ){
            URL = "spot.xhtml";
        }
        else{
            URL = "error.xhtml";
        }
        return URL; 
    }*/
}
