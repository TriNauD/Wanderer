/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wanderer.ejb;

import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 23675
 */
@Stateless
public class WdrSessionBean {

    private static final Logger logger = Logger.getLogger("Learning.web.Result");

//    public Result search(final String inputString) {
//        //预设城市与景点
//        WdrCity[] wdrCitys = new WdrCity[2];
//        wdrCitys[0] = new WdrCity("SH");
//        wdrCitys[1] = new WdrCity("BJ");
//        WdrSpot[] wdrSpots = new WdrSpot[2];
//        wdrSpots[0] = new WdrSpot("CHM");
//        wdrSpots[1] = new WdrSpot("GG");
// 
//        //查询
//        Result result = new Result();
//        for (int i = 0; i < wdrCitys.length; i++) {
//            if (inputString.equals(wdrCitys[i].getCityName())) {
//                result.setResultType("city");
//                result.setKey(wdrCitys[i].getCityName());
//                break;
//            } else if (inputString.equals(wdrSpots[i].getSpotName())) {
//                result.setResultType("spot");
//                
//                
//                result.setKey(wdrSpots[i].getSpotName());
//                break;
//            } else {
//                result.setResultType("error");
//            }
//        }
//
//        return result;
//        /*//预设被检索city/数据
//        String[] cityName = {"BJ", "SH"};
//        String[] spotName = {"CHM", "GG"};
//        Result result = new Result();
//        //搜索        
//        if (Arrays.binarySearch(cityName, input_string) >= 0) {
//            result.setResultType("city");
//        } else if (Arrays.binarySearch(spotName, input_string) >= 0) {
//            result.setResultType("spot");
//        } else {
//            result.setResultType("error");
//        }
//        return result;*/
//    }

    public boolean loginJudge(final String inputUserName, final String inputUserPassword) {
       
        //查询
        boolean isfound = false;
        
        
        
        return isfound;
        //预设用户名和密码
//        WdrUser[] userList = new WdrUser[3];
//        userList[0] = new WdrUser("a", "123");
//        userList[1] = new WdrUser("b", "456");
//        userList[2] = new WdrUser("c", "789");
//        //查询
//        boolean isfound = false;
//        for (int i = 0; i < userList.length; i++) {
//            if ((userList[i].getUserName().equals(inputUserName))
//                    && (userList[i].getUserPassWord().equals(inputUserPassword))) {
//                isfound = true;
//            }
//        }
//        return isfound;
    }
}
