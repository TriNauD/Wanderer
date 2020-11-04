/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wanderer.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 23675
 */
@Entity
@Table(name = "user_collect_spot")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserCollectSpot.findAll", query = "SELECT u FROM UserCollectSpot u")
    , @NamedQuery(name = "UserCollectSpot.findByUserCollectSpotId", query = "SELECT u FROM UserCollectSpot u WHERE u.userCollectSpotId = :userCollectSpotId")
    , @NamedQuery(name = "UserCollectSpot.findByUserId", query = "SELECT u FROM UserCollectSpot u WHERE u.userId = :userId")})
public class UserCollectSpot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_collect_spot_id")
    private Integer userCollectSpotId;
    @JoinColumn(name = "spot_id", referencedColumnName = "spot_id")
    @ManyToOne
    private Spot spotId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;

    public UserCollectSpot() {
    }

    public UserCollectSpot(Integer userCollectSpotId) {
        this.userCollectSpotId = userCollectSpotId;
    }

    public Integer getUserCollectSpotId() {
        return userCollectSpotId;
    }

    public void setUserCollectSpotId(Integer userCollectSpotId) {
        this.userCollectSpotId = userCollectSpotId;
    }

    public Spot getSpotId() {
        return spotId;
    }

    public void setSpotId(Spot spotId) {
        this.spotId = spotId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userCollectSpotId != null ? userCollectSpotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserCollectSpot)) {
            return false;
        }
        UserCollectSpot other = (UserCollectSpot) object;
        if ((this.userCollectSpotId == null && other.userCollectSpotId != null) || (this.userCollectSpotId != null && !this.userCollectSpotId.equals(other.userCollectSpotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wanderer.ejb.UserCollectSpot[ userCollectSpotId=" + userCollectSpotId + " ]";
    }

}
