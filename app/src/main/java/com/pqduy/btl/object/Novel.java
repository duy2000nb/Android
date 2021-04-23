package com.pqduy.btl.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Novel implements Serializable {
    private Integer id, idUser, totalChap;
    private String name, linkImage;

    public Novel(){

    }

    public Novel(JSONObject object) throws JSONException {
        this.id         = object.getInt("id");
        this.idUser     = object.getInt("idUser");
        this.totalChap  = object.getInt("totalChap");
        this.name       = object.getString("name");
        this.linkImage  = object.getString("linkImage");
    }

    public Novel(Integer id, Integer idUser, String name, Integer totalChap, String linkImage) {
        this.id         = id;
        this.idUser     = idUser;
        this.totalChap  = totalChap;
        this.name       = name;
        this.linkImage  = linkImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getTotalChap() {
        return totalChap;
    }

    public void setTotalChap(Integer totalChap) {
        this.totalChap = totalChap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
