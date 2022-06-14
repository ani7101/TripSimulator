package utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)
public class IotDeserializerList {

    public static final String LINKS = "links";

    public static final String OFFSET = "offset";

    public static final String LIMIT = "limit";

    public static final String COUNT = "count";

    public static final String HAS_MORE = "hasMore";

    @JsonProperty(LINKS)
    protected ArrayList<IoTDeserializerLinks> links;

    @JsonProperty(OFFSET)
    protected int offset;

    @JsonProperty(LIMIT)
    protected int limit;

    @JsonProperty(COUNT)
    protected int count;

    @JsonProperty(HAS_MORE)
    protected boolean hasMore;

    public ArrayList<IoTDeserializerLinks> getLinks() { return links; }

    public int getOffset() { return offset; }

    public int getLimit() { return limit; }

    public int getCount() { return count; }

    public boolean isHasMore() { return hasMore; }
}
