package iss.edu.sg.currency.model;

public class Conversion {
    private String from;
    private String fromSymbol;
    private String to;
    private String toSymbol;
    private double inputValue;
    private double outputValue;

    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getFromSymbol() {
        return fromSymbol;
    }
    public void setFromSymbol(String fromSymbol) {
        this.fromSymbol = fromSymbol;
    }
    public String getToSymbol() {
        return toSymbol;
    }
    public void setToSymbol(String toSymbol) {
        this.toSymbol = toSymbol;
    }
    public double getInputValue() {
        return inputValue;
    }
    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }
    public double getOutputValue() {
        return outputValue;
    }
    public void setOutputValue(double outputValue) {
        this.outputValue = outputValue;
    }

    public static Conversion create(Currency from, Currency to, Double inputValue, Double conversionRate) {
        Conversion conv = new Conversion();
        conv.setFrom(from.getCurrencyName());
        conv.setFromSymbol(from.getCurrencySymbol());
        conv.setInputValue(inputValue);
        conv.setOutputValue(inputValue * conversionRate);
        conv.setToSymbol(to.getCurrencySymbol());
        conv.setTo(to.getCurrencyName());

        return conv;
    }
    
    
}
