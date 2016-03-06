package itis.ru.homework4.model;

/**
 * Created by yasina on 06.03.16.
 */
public class Item {

    private String title;
    private String text;

    public Item(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
