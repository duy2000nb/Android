package com.pqduy.btl.object;

import org.json.JSONException;
import org.json.JSONObject;

public class ContentChap {
    private Integer numberChapter, idNovel;
    private String name, timeUpload, content;

    public ContentChap(JSONObject object) throws JSONException {
        this.numberChapter  = object.getInt("numberChapter");
        this.name           = object.getString("name");
        this.timeUpload     = object.getString("timeUpload");
        this.idNovel        = object.getInt("idNovel");
        this.content        = object.getString("content");
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeUpload() {
        return timeUpload;
    }

    public void setTimeUpload(String timeUpload) {
        this.timeUpload = timeUpload;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
