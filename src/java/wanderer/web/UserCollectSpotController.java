package wanderer.web;

import wanderer.ejb.util.JsfUtil;
import wanderer.ejb.util.PaginationHelper;

import java.io.Serializable;
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
import wanderer.entity.UserCollectSpot;
import wanderer.ejb.facade.UserCollectSpotFacade;
import wanderer.entity.User;

@Named("userCollectSpotController")
@SessionScoped
public class UserCollectSpotController implements Serializable {

    private UserCollectSpot current;
    private DataModel items = null;
    @EJB
    private wanderer.ejb.facade.UserCollectSpotFacade ejbFacade;
    @EJB
    private wanderer.ejb.facade.UserFacade usersEjbFacade;
    @EJB
    private wanderer.ejb.facade.SpotFacade spotEjbFacade;

    private PaginationHelper pagination;
    private int selectedItemIndex;

    //收藏函数 会新建一个用户与景点的联系 
    public String processCollect() {
        System.out.println("wanderer.web.UserCollectSpotController.processCollect()");

        if (!usersEjbFacade.isLoginStatus()) {
            //如果用户未登录
            return "login.xhtml";
        } else {
            //如果已经登录            
            User inputUser = usersEjbFacade.getCurrentUser();
            System.out.println("wanderer.web.UserCollectSpotController.processCollect() input user: " + inputUser.getUserName());
            System.out.println("wanderer.web.UserCollectSpotController.processCollect() input spot: " + spotEjbFacade.getCurrentSpot().getSpotName());
            current.setUserId(inputUser);            
            current.setSpotId(spotEjbFacade.getCurrentSpot());
            System.out.println("wanderer.web.UserCollectSpotController.processCollect() set user: " + current.getUserId().getUserName());
            
            create();
            System.out.println("wanderer.web.UserCollectSpotController.processCollect() Real create one.");

            return "spot.xhtml";//刷新页面
        }
    }

    public UserCollectSpotController() {
        this.current = new UserCollectSpot();
    }

    public UserCollectSpot getSelected() {
        if (current == null) {
            current = new UserCollectSpot();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserCollectSpotFacade getFacade() {
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
        current = (UserCollectSpot) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new UserCollectSpot();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCollectSpotCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (UserCollectSpot) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCollectSpotUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (UserCollectSpot) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCollectSpotDeleted"));
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

    public UserCollectSpot getUserCollectSpot(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = UserCollectSpot.class)
    public static class UserCollectSpotControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserCollectSpotController controller = (UserCollectSpotController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userCollectSpotController");
            return controller.getUserCollectSpot(getKey(value));
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
            if (object instanceof UserCollectSpot) {
                UserCollectSpot o = (UserCollectSpot) object;
                return getStringKey(o.getUserCollectSpotId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UserCollectSpot.class.getName());
            }
        }

    }

}
