/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wanderer.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 23675
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId")
    , @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName")
    , @NamedQuery(name = "User.findByUserPassword", query = "SELECT u FROM User u WHERE u.userPassword = :userPassword")
    , @NamedQuery(name = "User.findByUserAvatarPicUrl", query = "SELECT u FROM User u WHERE u.userAvatarPicUrl = :userAvatarPicUrl")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_password")
    private String userPassword;
    @OneToMany(mappedBy = "userId")
    private Collection<UserCollectSpot> userCollectSpotCollection;
    @Basic(optional = false)
    @Size(min = 1, max = 512)
    @Column(name = "user_avatar_pic_url")
    private String userAvatarPicUrl;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String userName, String userPassword,String userAvatarPicUrl) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userAvatarPicUrl = userAvatarPicUrl;
    }

    public String getUserAvatarPicUrl() {
        return userAvatarPicUrl;
    }

    public void setUserAvatarPicUrl(String userAvatarPicUrl) {
        this.userAvatarPicUrl = userAvatarPicUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @XmlTransient
    public Collection<UserCollectSpot> getUserCollectSpotCollection() {
        return userCollectSpotCollection;
    }

    public void setUserCollectSpotCollection(Collection<UserCollectSpot> userCollectSpotCollection) {
        this.userCollectSpotCollection = userCollectSpotCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wanderer.ejb.User[ userId=" + userId + " ]";
    }

}
