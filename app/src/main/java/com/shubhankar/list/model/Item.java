package com.shubhankar.list.model;

public class Item {
    private int id;
    private String itemName;
    private Double itemPrice;
    private String itemQuantity;
    private String itemNote; // 3, 4 months...12 months...
    private String dateItemAdded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemNote() {
        return itemNote;
    }

    public void setItemNote(String itemNote) {
        this.itemNote = itemNote;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }

    public Item(String itemName, Double itemPrice, String itemQuantity, String itemNote) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemNote = itemNote;
    }

    public Item(String itemName, Double itemPrice, String itemQuantity, String itemNote, String dateItemAdded) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemNote = itemNote;
        this.dateItemAdded = dateItemAdded;
    }

    public Item(int id, String itemName, Double itemPrice, String itemQuantity, String itemNote, String dateItemAdded) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemNote = itemNote;
        this.dateItemAdded = dateItemAdded;
    }

    public Item() {
    }
}
