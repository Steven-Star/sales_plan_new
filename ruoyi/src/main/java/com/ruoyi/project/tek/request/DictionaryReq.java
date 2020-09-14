package com.ruoyi.project.tek.request;


/**
 * 字典类型请求
 *
 * @author steven.guo
 * @date 2020/5/29 10:16
 */
public class DictionaryReq {

    /**
     * 编码
     */
    private String code;

    /**
     * 类型code
     */
    private String typeCode;

    /**
     * 组别
     */
    private String group;

    @Override
    public String toString() {
        return "DictionaryReq{" +
                "code='" + code + '\'' +
                ", typeCode='" + typeCode + '\'' +
                ", group='" + group + '\'' +
                "} " + super.toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
