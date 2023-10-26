import java.util.ArrayList;

public class SearchByAll  implements SearchStrategy {
    @Override
        public ArrayList<ItemInterface> search(ArrayList<ItemInterface> items, String searchTerm){
            String term = searchTerm.toLowerCase();
            ArrayList<ItemInterface> result = new ArrayList<>();  // ArrayList copy
            for(ItemInterface item: items){
                if (item.getName().contains(term) || item.getDescription().contains(term)) {
                    result.add(item);
                }
            }
            return result;
        }

}
