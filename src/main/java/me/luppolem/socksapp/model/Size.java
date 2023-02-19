package me.luppolem.socksapp.model;

public enum Size {
    XS(36.0),S(36.5),M(37.0),L(37.5),XL(38.0),XXL(38.5),XXXL(39.0);
    private final Double number;

    Size(Double number) {
        this.number=number;
    }

    public Double getNumber() {
        return number;
    }
}
