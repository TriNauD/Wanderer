/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wanderer.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author 23675
 */
@ManagedBean
public class GalleriaPics {
    private List<String> images;
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 5; i++) {
            images.add(i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
}
