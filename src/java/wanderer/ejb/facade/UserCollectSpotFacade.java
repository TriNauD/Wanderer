/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wanderer.ejb.facade;

import java.util.List;
import javax.ejb.EJBException;
import wanderer.entity.UserCollectSpot;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import wanderer.entity.City;
import wanderer.entity.User;

/**
 *
 * @author 23675
 */
@Stateless
public class UserCollectSpotFacade extends AbstractFacade<UserCollectSpot> {

    @PersistenceContext(unitName = "WdrTest5PU")
    private EntityManager em;
    private List<UserCollectSpot> loveList;

    public List<UserCollectSpot> getLoveList() {
        return loveList;
    }

    public void setLoveList(List<UserCollectSpot> loveList) {
        this.loveList = loveList;
    }
    
    //输入用户id，取得收藏景点列表
    public List<UserCollectSpot> getItByUserId(User inputID) {
        System.out.println("wanderer.ejb.facade.UserCollectSpotFacade.getItByUserId()");
        loveList = (List<UserCollectSpot>) em.createNamedQuery("UserCollectSpot.findByUserId").setParameter("userId", inputID).getResultList();
        System.out.println("wanderer.ejb.facade.UserCollectSpotFacade.getItByUserId() love list size: " + loveList.size());
        return loveList;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserCollectSpotFacade() {
        super(UserCollectSpot.class);
    }

}
