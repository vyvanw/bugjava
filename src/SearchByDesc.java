import java.util.ArrayList;

public class SearchByDesc  implements SearchStrategy {
    @Override
        public ArrayList<ItemInterface> search(ArrayList<ItemInterface> items, String searchTerm){
            String term = searchTerm.toLowerCase();
            ArrayList<ItemInterface> result = new ArrayList<>();  // ArrayList copy
            for(ItemInterface item: items){
                if (item.getDescription().contains(term)) {
                    result.add(item);
                }
            }
            return result;
        }

}
