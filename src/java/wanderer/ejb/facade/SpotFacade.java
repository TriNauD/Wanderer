/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wanderer.ejb.facade;

import java.util.List;
import javax.ejb.EJBException;
import wanderer.entity.Spot;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import wanderer.entity.Spot;

/**
 *
 * @author 23675
 */
@Stateless
public class SpotFacade extends AbstractFacade<Spot> {

    @PersistenceContext(unitName = "WdrTest5PU")
    private EntityManager em;
    private List<Spot> spots;
    private Spot currentSpot;//当前景点

    public Spot getCurrentSpot() {
        return currentSpot;
    }

    public void setCurrentSpot(Spot currentSpot) {
        this.currentSpot = currentSpot;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public List<Spot> getSpotListByCityID(int inputCityID) {
        System.out.println("wanderer.ejb.facade.SpotFacade.getSpotListByCityID()");
        spots = em.createNamedQuery("Spot.findByCityID").setParameter("inputCityID", inputCityID).getResultList();
        System.out.println("wanderer.ejb.facade.SpotFacade.getSpotListByCityID() spotlist size:" + spots.size());
        return spots;
    }

    //调用Spot类里的NamedQuery查询 return一个Spot的List
    public List<Spot> getSpotListByName(String inputName) throws EJBException {
        System.out.println("wanderer.ejb.facade.SpotFacade.getSpotListByName()");
        spots = (List<Spot>) em.createNamedQuery("Spot.findBySpotName").setParameter("spotName", inputName).getResultList();
        System.out.println("wanderer.ejb.facade.SpotFacade.getSpotListByName() spotlist size:" + spots.size());
        return spots;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SpotFacade() {
        super(Spot.class);
    }

}
