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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "spot")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spot.findAll", query = "SELECT s FROM Spot s")
    , @NamedQuery(name = "Spot.findBySpotId", query = "SELECT s FROM Spot s WHERE s.spotId = :spotId")
    , @NamedQuery(name = "Spot.findBySpotName", query = "SELECT s FROM Spot s WHERE s.spotName = :spotName")
    , @NamedQuery(name = "Spot.findBySpotPicMainUrl", query = "SELECT s FROM Spot s WHERE s.spotPicMainUrl = :spotPicMainUrl")
    , @NamedQuery(name = "Spot.findBySpotPicIntroUrl", query = "SELECT s FROM Spot s WHERE s.spotPicIntroUrl = :spotPicIntroUrl")
    , @NamedQuery(name = "Spot.findBySpotPicOtherUrl", query = "SELECT s FROM Spot s WHERE s.spotPicOtherUrl = :spotPicOtherUrl")
    , @NamedQuery(name = "Spot.findBySpotIntroTitle", query = "SELECT s FROM Spot s WHERE s.spotIntroTitle = :spotIntroTitle")
    , @NamedQuery(name = "Spot.findBySpotEmail", query = "SELECT s FROM Spot s WHERE s.spotEmail = :spotEmail")
    , @NamedQuery(name = "Spot.findBySpotTel", query = "SELECT s FROM Spot s WHERE s.spotTel = :spotTel")
    , @NamedQuery(name = "Spot.findBySpotOpentime", query = "SELECT s FROM Spot s WHERE s.spotOpentime = :spotOpentime")
    , @NamedQuery(name = "Spot.findBySpotTransport", query = "SELECT s FROM Spot s WHERE s.spotTransport = :spotTransport")
    , @NamedQuery(name = "Spot.findBySpotAddr", query = "SELECT s FROM Spot s WHERE s.spotAddr = :spotAddr")
    , @NamedQuery(name = "Spot.findBySpotWebsite", query = "SELECT s FROM Spot s WHERE s.spotWebsite = :spotWebsite")
    , @NamedQuery(name = "Spot.findBySpotMapUrl", query = "SELECT s FROM Spot s WHERE s.spotMapUrl = :spotMapUrl")
    , @NamedQuery(name = "Spot.findByCityID", query = "SELECT s FROM Spot s WHERE s.spotBelongToCity.cityId = :inputCityID")})
public class Spot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "spot_id")
    private Integer spotId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "spot_name")
    private String spotName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "spot_pic_main_url")
    private String spotPicMainUrl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "spot_pic_intro_url")
    private String spotPicIntroUrl;
    @Size(max = 512)
    @Column(name = "spot_pic_other_url")
    private String spotPicOtherUrl;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "spot_intro_content")
    private String spotIntroContent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "spot_intro_title")
    private String spotIntroTitle;
    @Size(max = 45)
    @Column(name = "spot_email")
    private String spotEmail;
    @Size(max = 45)
    @Column(name = "spot_tel")
    private String spotTel;
    @Size(max = 45)
    @Column(name = "spot_opentime")
    private String spotOpentime;
    @Size(max = 100)
    @Column(name = "spot_transport")
    private String spotTransport;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "spot_addr")
    private String spotAddr;
    @Size(max = 45)
    @Column(name = "spot_website")
    private String spotWebsite;
    @Size(max = 45)
    @Column(name = "spot_map_url")
    private String spotMapUrl;
    @JoinColumn(name = "spot_belong_to_city", referencedColumnName = "city_id")
    @ManyToOne
    private City spotBelongToCity;
    @OneToMany(mappedBy = "spotId")
    private Collection<UserCollectSpot> userCollectSpotCollection;

    public Spot() {
    }

    public Spot(Integer spotId) {
        this.spotId = spotId;
    }

    public Spot(Integer spotId, String spotName, String spotPicMainUrl, String spotPicIntroUrl, String spotIntroContent, String spotIntroTitle, String spotAddr) {
        this.spotId = spotId;
        this.spotName = spotName;
        this.spotPicMainUrl = spotPicMainUrl;
        this.spotPicIntroUrl = spotPicIntroUrl;
        this.spotIntroContent = spotIntroContent;
        this.spotIntroTitle = spotIntroTitle;
        this.spotAddr = spotAddr;
    }

    public Integer getSpotId() {
        return spotId;
    }

    public void setSpotId(Integer spotId) {
        this.spotId = spotId;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotPicMainUrl() {
        return spotPicMainUrl;
    }

    public void setSpotPicMainUrl(String spotPicMainUrl) {
        this.spotPicMainUrl = spotPicMainUrl;
    }

    public String getSpotPicIntroUrl() {
        return spotPicIntroUrl;
    }

    public void setSpotPicIntroUrl(String spotPicIntroUrl) {
        this.spotPicIntroUrl = spotPicIntroUrl;
    }

    public String getSpotPicOtherUrl() {
        return spotPicOtherUrl;
    }

    public void setSpotPicOtherUrl(String spotPicOtherUrl) {
        this.spotPicOtherUrl = spotPicOtherUrl;
    }

    public String getSpotIntroContent() {
        return spotIntroContent;
    }

    public void setSpotIntroContent(String spotIntroContent) {
        this.spotIntroContent = spotIntroContent;
    }

    public String getSpotIntroTitle() {
        return spotIntroTitle;
    }

    public void setSpotIntroTitle(String spotIntroTitle) {
        this.spotIntroTitle = spotIntroTitle;
    }

    public String getSpotEmail() {
        return spotEmail;
    }

    public void setSpotEmail(String spotEmail) {
        this.spotEmail = spotEmail;
    }

    public String getSpotTel() {
        return spotTel;
    }

    public void setSpotTel(String spotTel) {
        this.spotTel = spotTel;
    }

    public String getSpotOpentime() {
        return spotOpentime;
    }

    public void setSpotOpentime(String spotOpentime) {
        this.spotOpentime = spotOpentime;
    }

    public String getSpotTransport() {
        return spotTransport;
    }

    public void setSpotTransport(String spotTransport) {
        this.spotTransport = spotTransport;
    }

    public String getSpotAddr() {
        return spotAddr;
    }

    public void setSpotAddr(String spotAddr) {
        this.spotAddr = spotAddr;
    }

    public String getSpotWebsite() {
        return spotWebsite;
    }

    public void setSpotWebsite(String spotWebsite) {
        this.spotWebsite = spotWebsite;
    }

    public String getSpotMapUrl() {
        return spotMapUrl;
    }

    public void setSpotMapUrl(String spotMapUrl) {
        this.spotMapUrl = spotMapUrl;
    }

    public City getSpotBelongToCity() {
        return spotBelongToCity;
    }

    public void setSpotBelongToCity(City spotBelongToCity) {
        this.spotBelongToCity = spotBelongToCity;
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
        hash += (spotId != null ? spotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spot)) {
            return false;
        }
        Spot other = (Spot) object;
        if ((this.spotId == null && other.spotId != null) || (this.spotId != null && !this.spotId.equals(other.spotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wanderer.ejb.Spot[ spotId=" + spotId + " ]";
    }

}
