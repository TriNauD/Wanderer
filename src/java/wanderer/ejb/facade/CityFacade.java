/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wanderer.ejb.facade;

import java.util.List;
import javax.ejb.EJBException;
import wanderer.entity.City;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 23675
 */
@Stateless
public class CityFacade extends AbstractFacade<City> {

    @PersistenceContext(unitName = "WdrTest5PU")
    private EntityManager em;
    private List<City> cities;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
    

 
    //通过名字查找城市实体
    //调用City类里的NamedQuery查询 
    //return一个City的List
    public List<City> getCityListByName(String inputName) throws EJBException{
        System.out.println("wanderer.ejb.facade.CityFacade.getCityListByName()");
        cities = (List<City>) em.createNamedQuery("City.findByCityName").setParameter("cityName", inputName).getResultList();
        System.out.println("wanderer.ejb.facade.CityFacade.getCityListByName() citylist size: " + cities.size());
        return cities;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CityFacade() {
        super(City.class);
    }

}
