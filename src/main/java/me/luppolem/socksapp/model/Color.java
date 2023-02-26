package me.luppolem.socksapp.model;

public enum Color {
    BLACK("черный"), WHITE("белый"), RED("красный"), BLUE("синий"), GREY("серый");
    private final String text;

    Color(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
