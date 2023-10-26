import java.util.ArrayList;

public class SearchByName  implements SearchStrategy {
    @Override
        public ArrayList<ItemInterface> search(ArrayList<ItemInterface> items, String searchTerm){
            String term = searchTerm.toLowerCase();
            ArrayList<ItemInterface> result = new ArrayList<>();  // ArrayList copy
            for(ItemInterface item: items){
                if (item.getName().contains(term)) {
                    result.add(item);
                }
            }
            return result;
        }

}
