import java.util.ArrayList;

public interface SearchStrategy {
    // search method that all concrete strategies implement
    ArrayList<ItemInterface> search(ArrayList<ItemInterface> items, String searchTerm);
}
