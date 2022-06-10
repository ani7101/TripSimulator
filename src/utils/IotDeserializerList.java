package utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)
public class IotDeserializerList <T> {

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Links {
        public static final String HREF = "href";

        public static final String REL = "rel";

        @JsonProperty(HREF)
        private String href;

        @JsonProperty(REL)
        private String rel;

        public String getHref() { return href; }

        public String getRel() { return rel; }
    }

    public static final String ITEMS = "items";

    public static final String LINKS = "links";

    public static final String OFFSET = "offset";

    public static final String LIMIT = "limit";

    public static final String COUNT = "count";

    public static final String HAS_MORE = "hasMore";

    @JsonProperty(ITEMS)
    private ArrayList<T> items;

    @JsonProperty(LINKS)
    private ArrayList<Links> links;

    @JsonProperty(OFFSET)
    private int offset;

    @JsonProperty(LIMIT)
    private int limit;

    @JsonProperty(COUNT)
    private int count;

    @JsonProperty(HAS_MORE)
    private boolean hasMore;

    public ArrayList<T> getItems() { return items; }

    public ArrayList<Links> getLinks() { return links; }

    public int getOffset() { return offset; }

    public int getLimit() { return limit; }

    public int getCount() { return count; }

    public boolean isHasMore() { return hasMore; }
}
