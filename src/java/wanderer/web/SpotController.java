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
import wanderer.entity.Spot;
import wanderer.ejb.facade.SpotFacade;

@Named("spotController")
@SessionScoped
public class SpotController implements Serializable {

    private Spot current;
    private DataModel items = null;
    @EJB
    private wanderer.ejb.facade.SpotFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String[] spotPicOtherUrls = new String[2];
    private String coverStyleStr;

    public String getCoverStyleStr() {
        return coverStyleStr;
    }

    public void setCoverStyleStr(String coverStyleStr) {
        this.coverStyleStr = coverStyleStr;
    }

    public String[] getSpotPicOtherUrls() {
        return spotPicOtherUrls;
    }

    public void setSpotPicOtherUrls(String[] spotPicOtherUrls) {
        this.spotPicOtherUrls = spotPicOtherUrls;
    }

    private String inputName;

    public void flushCurrentSpot() {
        System.out.println("wanderer.web.SpotController.flushCurrentSpot()");
        System.out.println("wanderer.web.SpotController.flushCurrentSpot() size: " + ejbFacade.getSpots().size());
        List<Spot> spots = ejbFacade.getSpots();//拿到刚才找的list
        current = spots.get(0);//取搜索到的第一结果.
        System.out.println("wanderer.web.SpotController.flushCurrentSpot() this spot is: " + current.getSpotName());
        ejbFacade.setCurrentSpot(current);//保存当前景点
        System.out.println("wanderer.web.SpotController.flushCurrentSpot() save spot: " + ejbFacade.getCurrentSpot().getSpotName());
        coverStyleStr = "background-image:url(" + current.getSpotPicMainUrl() + ")";//将当前查到的spot的main pic URL存到coverStyleStr
        //初始化 "other pic url"
        this.spotPicOtherUrls[0] = current.getSpotPicOtherUrl() + "/1.jpg";
        this.spotPicOtherUrls[1] = current.getSpotPicOtherUrl() + "/2.jpg";
        System.out.println("wanderer.web.SpotController.flushCurrentSpot() spotPicOtherUrls[0]:" + spotPicOtherUrls[0]);
        System.out.println("wanderer.web.SpotController.flushCurrentSpot() spotPicOtherUrls[1]:" + spotPicOtherUrls[1]);

    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public SpotController() {
    }

    public Spot getSelected() {
        if (current == null) {
            current = new Spot();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SpotFacade getFacade() {
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
        current = (Spot) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Spot();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SpotCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Spot) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SpotUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Spot) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SpotDeleted"));
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

    public Spot getSpot(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Spot.class)
    public static class SpotControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SpotController controller = (SpotController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "spotController");
            return controller.getSpot(getKey(value));
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
            if (object instanceof Spot) {
                Spot o = (Spot) object;
                return getStringKey(o.getSpotId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Spot.class.getName());
            }
        }

    }

}
