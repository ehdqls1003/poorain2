package com.kplo.myapplication;

public class Makeparty_Item {


    private String iconDrawable;
    private String product_name;
    private String product_price;
    private String product_text;
    private int product_count;

    public void setImg(String icon) {
        iconDrawable = icon;
    }

    public void setName(String name) {
        product_name = name;
    }

    public void setPrice(String price) {
        product_price = price;
    }
    public void setText(String text) {
        product_text = text;
    }
    public void setCount(int count) {
        product_count = count;
    }

    public String getImg() {
        return this.iconDrawable;
    }

    public String getName() {
        return this.product_name;
    }

    public String getPrice() {
        return this.product_price;
    }
    public String getText() {
        return this.product_text;
    }
    public int getCount() {
        return this.product_count;
    }
}
