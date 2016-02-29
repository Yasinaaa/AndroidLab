package modified.dobjanschi.a.pattern.service.model;

/**
 * @author Rustem
 */
public class RequestItem {

    private int id;
    private final String request;
    private String response;
    private String status;

    public RequestItem(String request, String status) {
        this.status = status;
        this.request = request;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String json) {
        this.response = json;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}