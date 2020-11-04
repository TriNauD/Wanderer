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
import javax.persistence.Lob;
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
@Table(name = "city")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c")
    , @NamedQuery(name = "City.findByCityId", query = "SELECT c FROM City c WHERE c.cityId = :cityId")
    , @NamedQuery(name = "City.findByCityName", query = "SELECT c FROM City c WHERE c.cityName = :cityName")
    , @NamedQuery(name = "City.findByCityPicMainUrl", query = "SELECT c FROM City c WHERE c.cityPicMainUrl = :cityPicMainUrl")
    , @NamedQuery(name = "City.findByCityPicIntroUrl", query = "SELECT c FROM City c WHERE c.cityPicIntroUrl = :cityPicIntroUrl")
    , @NamedQuery(name = "City.findByCityPicOtherUrl", query = "SELECT c FROM City c WHERE c.cityPicOtherUrl = :cityPicOtherUrl")
    , @NamedQuery(name = "City.findByCityIntroTitle", query = "SELECT c FROM City c WHERE c.cityIntroTitle = :cityIntroTitle")})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "city_id")
    private Integer cityId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "city_name")
    private String cityName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "city_pic_main_url")
    private String cityPicMainUrl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "city_pic_intro_url")
    private String cityPicIntroUrl;
    @Size(max = 512)
    @Column(name = "city_pic_other_url")
    private String cityPicOtherUrl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "city_intro_title")
    private String cityIntroTitle;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "city_intro_content")
    private String cityIntroContent;
    @OneToMany(mappedBy = "spotBelongToCity")
    private Collection<Spot> spotCollection;

    public City() {
    }

    public City(Integer cityId) {
        this.cityId = cityId;
    }

    public City(Integer cityId, String cityName, String cityPicMainUrl, String cityPicIntroUrl, String cityIntroTitle, String cityIntroContent) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityPicMainUrl = cityPicMainUrl;
        this.cityPicIntroUrl = cityPicIntroUrl;
        this.cityIntroTitle = cityIntroTitle;
        this.cityIntroContent = cityIntroContent;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityPicMainUrl() {
        return cityPicMainUrl;
    }

    public void setCityPicMainUrl(String cityPicMainUrl) {
        this.cityPicMainUrl = cityPicMainUrl;
    }

    public String getCityPicIntroUrl() {
        return cityPicIntroUrl;
    }

    public void setCityPicIntroUrl(String cityPicIntroUrl) {
        this.cityPicIntroUrl = cityPicIntroUrl;
    }

    public String getCityPicOtherUrl() {
        return cityPicOtherUrl;
    }

    public void setCityPicOtherUrl(String cityPicOtherUrl) {
        this.cityPicOtherUrl = cityPicOtherUrl;
    }

    public String getCityIntroTitle() {
        return cityIntroTitle;
    }

    public void setCityIntroTitle(String cityIntroTitle) {
        this.cityIntroTitle = cityIntroTitle;
    }

    public String getCityIntroContent() {
        return cityIntroContent;
    }

    public void setCityIntroContent(String cityIntroContent) {
        this.cityIntroContent = cityIntroContent;
    }

    @XmlTransient
    public Collection<Spot> getSpotCollection() {
        return spotCollection;
    }

    public void setSpotCollection(Collection<Spot> spotCollection) {
        this.spotCollection = spotCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cityId != null ? cityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.cityId == null && other.cityId != null) || (this.cityId != null && !this.cityId.equals(other.cityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wanderer.ejb.City[ cityId=" + cityId + " ]";
    }
    
}
