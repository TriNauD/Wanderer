package wanderer.web;

import wanderer.ejb.util.JsfUtil;
import wanderer.ejb.util.PaginationHelper;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import wanderer.entity.User;
import wanderer.ejb.facade.UserFacade;
import wanderer.entity.Spot;
import wanderer.entity.UserCollectSpot;

@Named("userController")
@SessionScoped
public class UserController implements Serializable {

    private User current;
    private DataModel items = null;
    @EJB
    private wanderer.ejb.facade.UserFacade ejbFacade;
    @EJB
    private wanderer.ejb.facade.SpotFacade spotFacade;
    @EJB
    private wanderer.ejb.facade.UserCollectSpotFacade loveFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String[] spotUrl = new String[3];

    public String[] getSpotUrl() {
        return spotUrl;
    }

    public void setSpotUrl(String[] spotUrl) {
        this.spotUrl = spotUrl;
    }

//    private List<Spot> spotList;//收藏景点列表
//    public List<Spot> getSpotList() {
//        return spotList;
//    }
//
//    public void setSpotList(List<Spot> spotList) {
//        this.spotList = spotList;
//    }
    //判定登录输入信息是否匹配
    //不匹配会报“Username or password wrong.”    
    public void isLoginSuccess(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        System.out.println("wanderer.web.WdrIOBean.isLoginSuccess()");
        UIComponent c = null;
        for (UIComponent ui : component.getParent().getChildren()) {
            if ("userName".equals(ui.getId())) {    //这里的"userName"是输入用户名控件的id
                c = ui;
                break;
            }
        }
        HtmlInputText htmlInputText = (HtmlInputText) c;
        String name, pass;
        name = htmlInputText.getValue().toString().trim();
        pass = value.toString().trim();

        //判定变量
        boolean isFound = false;
        //去数据库找到这一个用户
        User userFound = null;
        List<User> userListByName = ejbFacade.getUserByName(name);//通过用户输入找到数据库中相同名字的用户        
        System.out.println("wanderer.web.WdrIOBean.isLoginSuccess() find user list size: " + userListByName.size());
        if (userListByName.isEmpty()) {
            isFound = false;//如果没有找到相同用户名的用户，就说用户名错误
        } else {
            userFound = userListByName.get(0);//因为数据库建立不能重名，所以应该只能找到一个同样用户名的用户
            System.out.println("wanderer.web.WdrIOBean.isLoginSuccess() find user: " + userFound.getUserName());
            if (userFound.getUserPassword().equals(pass)) {
                isFound = true;//如果密码也相同，就匹配成功了
            }
        }

        if (!isFound) {
            //如果没有匹配
            FacesMessage msg = new FacesMessage("Username or password wrong.");
            throw new ValidatorException(msg);
        } else {
            //如果匹配成功
            current = userFound;
            ejbFacade.setCurrentUser(current);//保存当前登录用户
            ejbFacade.setLoginStatus(true);//保存登录状态
            //找到用户收藏列表
            List<UserCollectSpot> loveList;
            loveList = loveFacade.getItByUserId(current);
            System.out.println("wanderer.web.UserController.isLoginSuccess() loveList size： " + loveList.size());
            for (int i = 0; i < 3; i++) {
                spotUrl[i] = loveList.get(i).getSpotId().getSpotPicMainUrl();
                System.out.println("wanderer.web.UserController.isLoginSuccess() spotUrl[i]:  " + spotUrl[i]);
            }
        }
    }

    //判定注册信息是否合适
    //用户名不能重复
    public void isRegisterSuccess(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        System.out.println("wanderer.web.UserController.isRegisterSuccess()");

        UIComponent c = null;
        for (UIComponent ui : component.getParent().getChildren()) {
            if ("userName".equals(ui.getId())) {    //这里的"userName"是输入用户名控件的id
                c = ui;
                break;
            }
        }
        HtmlInputText htmlInputText = (HtmlInputText) c;
        String name = htmlInputText.getValue().toString().trim();
        String pass = value.toString().trim();

        //判定变量
        boolean isSame = true;
        //找到库里同名的user
        List<User> insideUserList = ejbFacade.getUserByName(name);
        System.out.println("wanderer.web.UserController.isRegisterSuccess() inside user size: " + insideUserList.size());
        if (insideUserList.isEmpty()) {
            //同名元素为空，不重复
            isSame = false;
        }

        if (isSame) {
            //如果不可通过，输出错误信息
            FacesMessage msg = new FacesMessage("Username existed.        Please choose another one.");
            throw new ValidatorException(msg);
        } else {
            //如果可以，进行正式创建
            current.setUserName(name);
            System.out.println("wanderer.web.UserController.isRegisterSuccess() real create. Name: " + current.getUserName());
            current.setUserPassword(pass);
            System.out.println("wanderer.web.UserController.isRegisterSuccess() Password: " + current.getUserPassword());
            create();
        }

    }

//
//    //创建注册用户
//    //检验用户名不重复才能注册成功
//    public void processCreate() {
//        System.out.println("wanderer.web.UserController.processCreate()");
//        //找到库里同名的user
//        String inputUsername = current.getUserName();
//        List<User> insideUserList = ejbFacade.getUserByName(inputUsername);
//        System.out.println("wanderer.web.UserController.processCreate() inside user size: " + insideUserList.size());
//
//    }
    public void getUserByUsername(String inputUsername) {
        List<User> users = ejbFacade.getUserByName(inputUsername);
        current = users.get(0);
    }

    public UserController() {
        this.current = new User();
    }

    public User getSelected() {
        if (current == null) {
            current = new User();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserFacade getFacade() {
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
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new User();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (User) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
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

    public User getUser(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = User.class)
    public static class UserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.getUser(getKey(value));
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
            if (object instanceof User) {
                User o = (User) object;
                return getStringKey(o.getUserId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + User.class.getName());
            }
        }

    }

}
