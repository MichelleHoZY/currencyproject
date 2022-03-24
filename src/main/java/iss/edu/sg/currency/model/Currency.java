package iss.edu.sg.currency.model;

import jakarta.json.JsonObject;

public class Currency {
    private String currencyId;
    private String currencyName;
    private String currencySymbol;
    private String id;

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Currency create(JsonObject object) {
        Currency currency = new Currency();
        currency.setCurrencyId(object.getString("currencyId"));
        currency.setCurrencyName(object.getString("currencyName"));
        currency.setCurrencySymbol(object.getString("currencySymbol"));
        currency.setId(object.getString("id"));

        return currency;
        }
    }

    
    
    
    






    // private String currencyName;

    // public String getCurrencyName() {
    //     return currencyName;
    // }

    // public void setCurrencyName(String currencyName) {
    //     this.currencyName = currencyName;
    // }

    // public static Currency create(JsonObject o){

    //     Currency currency = new Currency();
    //     currency.currencyName = o.getString("currencyName");

    //     return currency;

    // }
    
// }
