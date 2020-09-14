package com.ruoyi.project.tek.vo;


import java.io.Serializable;
import java.util.List;

/**
 * @author steven.guo
 * @date 2020/5/29 14:07
 */
public class SalesPlatformWithSiteVO implements Serializable {

    private String name;

    private String value;

    private List<SiteAccess> roleSites;

    public static class SiteAccess {
        private Long id;

        private String name;

        private String value;

        private String offset;

        private Integer offsetMinute;

        private String country;

        private String chineseName;

        public Long getId() {
            return id;
        }

        public SiteAccess setId(Long id) {
            this.id = id;
            return this;
        }

        public String getCountry() {
            return country;
        }

        public SiteAccess setCountry(String country) {
            this.country = country;
            return this;
        }

        public String getChineseName() {
            return chineseName;
        }

        public SiteAccess setChineseName(String chineseName) {
            this.chineseName = chineseName;
            return this;
        }

        public String getName() {
            return name;
        }

        public SiteAccess setName(String name) {
            this.name = name;
            return this;
        }

        public String getValue() {
            return value;
        }

        public SiteAccess setValue(String value) {
            this.value = value;
            return this;
        }

        public String getOffset() {
            return offset;
        }

        public SiteAccess setOffset(String offset) {
            this.offset = offset;
            return this;
        }

        public Integer getOffsetMinute() {
            return offsetMinute;
        }

        public SiteAccess setOffsetMinute(Integer offsetMinute) {
            this.offsetMinute = offsetMinute;
            return this;
        }

        @Override
        public String toString() {
            return "SiteAccess{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    ", offset='" + offset + '\'' +
                    ", offsetMinute='" + offsetMinute + '\'' +
                    '}';
        }
    }

    public String getName() {
        return name;
    }

    public SalesPlatformWithSiteVO setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public SalesPlatformWithSiteVO setValue(String value) {
        this.value = value;
        return this;
    }

    public List<SiteAccess> getRoleSites() {
        return roleSites;
    }

    public SalesPlatformWithSiteVO setRoleSites(List<SiteAccess> roleSites) {
        this.roleSites = roleSites;
        return this;
    }

    @Override
    public String toString() {
        return "SalesPlatformWithSiteVO{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", roleSites=" + roleSites +
                '}';
    }
}
