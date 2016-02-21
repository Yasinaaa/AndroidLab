package modified.dobjanschi.a.pattern.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yasina on 13.02.15.
 */
public class Vacancy {

    @SerializedName("items")
    public Item[] items;

    public static class Item{

        public long id;
        @SerializedName("name")
        public String name;
        @SerializedName("employer")
        public Employer employer;
        @SerializedName("snippet")
        public Snippet snippet;

        public static class Employer {
            @SerializedName("name")
            public String name;
        }

        public static class Snippet{
            @SerializedName("requirement")
            public String requirement;
            @SerializedName("responsibility")
            public String responsibility;
        }
    }


}
