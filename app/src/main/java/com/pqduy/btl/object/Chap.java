package com.pqduy.btl.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Chap implements Serializable {
    private Integer numberChapter, idNovel;
    private String nameChap, timeUpload;

    public Chap() {
    }

    public Chap(JSONObject object, int idNovel) throws JSONException {
        this.numberChapter  = object.getInt("numberChapter");
        this.nameChap       = object.getString("name");
        this.timeUpload     = object.getString("timeUpload");
        this.idNovel        = idNovel;
    }

    public Integer getNumberChapter() {
        return numberChapter;
    }

    public void setNumberChapter(Integer numberChapter) {
        this.numberChapter = numberChapter;
    }

    public Integer getIdNovel() {
        return idNovel;
    }

    public void setIdNovel(Integer idNovel) {
        this.idNovel = idNovel;
    }

    public String getNameChap() {
        return nameChap;
    }

    public void setNameChap(String nameChap) {
        this.nameChap = nameChap;
    }

    public String getTimeUpload() {
        return timeUpload;
    }

    public void setTimeUpload(String timeUpload) {
        this.timeUpload = timeUpload;
    }
}
