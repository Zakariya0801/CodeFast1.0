package application.Admin;
import java.util.ArrayList;
import java.util.List;

public abstract class Request<T> {
    protected List<T> requestData; // To store data fetched from the database.

    // Constructor initializes the list and loads data.
    public Request() {
        this.requestData = new ArrayList<>();
        loadData();
    }
    protected abstract void loadData();

    public List<T> getRequestData() {
        return requestData;
    }
}
