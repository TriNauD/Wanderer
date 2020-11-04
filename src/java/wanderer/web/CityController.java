package wanderer.web;

import wanderer.ejb.util.JsfUtil;
import wanderer.ejb.util.PaginationHelper;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import wanderer.entity.City;
import wanderer.ejb.facade.CityFacade;
import wanderer.ejb.facade.SpotFacade;
import wanderer.entity.Spot;

@Named("cityController")
@SessionScoped
public class CityController implements Serializable {

    private City current;
    private List<Spot> citySpotList;
    private DataModel items = null;
    @EJB
    private CityFacade ejbFacade;
    @EJB
    private SpotFacade ejbSpotFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String inputName;//输入的城市名
    private String[] SpotNameList = new String[2];
    private String[] spotPicURLList = new String[2];
    private String coverStyleStr;//城市的cover图css的url

    public String getCoverStyleStr() {
        return coverStyleStr;
    }

    public void setCoverStyleStr(String coverStyleStr) {
        this.coverStyleStr = coverStyleStr;
    }

    public String[] getSpotPicURLList() {
        return spotPicURLList;
    }

    public void setSpotPicURLList(String[] spotPicURLList) {
        this.spotPicURLList = spotPicURLList;
    }
    
    
    public String[] getSpotNameList() {
        return SpotNameList;
    }

    public void setSpotNameList(String[] SpotNameList) {
        this.SpotNameList = SpotNameList;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getUrlWithPara(int index) {
        System.out.println("wanderer.web.CityController.getUrlWithPara()");
        System.out.println("wanderer.web.CityController.getUrlWithPara() index:" + index);
        System.out.println("wanderer.web.CityController.getUrlWithPara() SpotName:" + SpotNameList[index]);
        ejbSpotFacade.getSpotListByName(SpotNameList[index]);
        return "spot.xhtml";
    }

    //得到本页面应该用的city，找到ejb刚才查到的city list，取第一个城市存入current
    public void flushCurrentCity() {
        System.out.println("wanderer.web.CityController.flushCurrentCity()");
        System.out.println("wanderer.web.CityController.flushCurrentCity() cities size: " + ejbFacade.getCities().size());
        List<City> cities = ejbFacade.getCities();//拿到刚才找的list
        current = cities.get(0);//只取搜索到的第一结果
        coverStyleStr =  "background-image:url("+current.getCityPicMainUrl()+")";//将当前查到的city的main pic URL存到coverStyleStr
        System.out.println("wanderer.web.CityController.flushCurrentCity() this city is: " + current.getCityName());
        System.out.println("wanderer.web.CityController.flushCurrentCity() current.getCityId():" + current.getCityId());
        System.out.println("wanderer.web.CityController.flushCurrentCity() spots size:" + ejbSpotFacade.getSpotListByCityID(current.getCityId()).size());
        List<Spot> spots = ejbSpotFacade.getSpotListByCityID(current.getCityId());
        System.out.println("wanderer.web.CityController.flushCurrentCity() spot list size: " + spots.size());
        this.SpotNameList[0] = spots.get(0).getSpotName();
        this.SpotNameList[1] = spots.get(1).getSpotName();
        this.spotPicURLList[0] = spots.get(0).getSpotPicMainUrl();
        this.spotPicURLList[1] = spots.get(1).getSpotPicMainUrl();
        System.out.println("wanderer.web.CityController.flushCurrentCity() SpotNameList[0]: " + this.SpotNameList[0]);
        System.out.println("wanderer.web.CityController.flushCurrentCity() SpotNameList[1]: " + this.SpotNameList[1]);
    }

    public City getSelected() {
        if (current == null) {
            current = new City();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CityFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (City) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new City();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CityCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (City) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CityUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (City) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CityDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public City getCity(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = City.class)
    public static class CityControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CityController controller = (CityController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cityController");
            return controller.getCity(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof City) {
                City o = (City) object;
                return getStringKey(o.getCityId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + City.class.getName());
            }
        }

    }

}
