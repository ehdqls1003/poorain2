package com.kplo.myapplication;

public class Recipejson {
    private String RECIPE_ID;
    private String IMG_URL;
    private String RECIPE_NM_KO;
    private String SUMRY;

    public String getRECIPE_ID() {
        return RECIPE_ID;
    }

    public String getIMG_URL() {
        return IMG_URL;
    }

    public String getRECIPE_NM_KO() {
        return RECIPE_NM_KO;
    }
    public String getSUMRY() {
        return SUMRY;
    }
    public void setRECIPE_ID(String recipe_id) {
        this.RECIPE_ID = recipe_id;
    }

    public void setIMG_URL(String img_url) {
        this.IMG_URL = img_url;
    }

    public void setRECIPE_NM_KO(String recipe_nm_ko) {
        this.RECIPE_NM_KO = recipe_nm_ko;
    }
    public void setSUMRY(String sumry) {
        this.SUMRY = sumry;
    }

    @Override

    public String toString() {

        return "Recipejson [name=" + RECIPE_NM_KO + ", age=" + IMG_URL + "]";

    }



}
