import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;

public class App {
    private Player player;
    private Storage storage;
    private JFrame frame;
    private PageManager manager;

    public App(Player p, Storage s) {
        player = p;
        storage = s;
        player.setStorageView(storage.getInventory());

        manager = new PageManager(player, storage);

        // Setup of sorting
        setupSearching((InventoryPage) manager.findPage("Player Inventory"));
        setupSearching((InventoryPage) manager.findPage("Storage"));

        // Setup of craftng
        setupCrafting((ItemCraftPage) manager.findPage("Item Crafting"), player);
        setupUncrafting((ProductPage) manager.findPage("Product Page"), player);

        // Window creation
        manager.refresh();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(manager.getJPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Task 1: Defining what each button in the UI will do.
    void setupSearching(InventoryPage page) {
        page.addSearchByButton(new SearchByButton("All", () -> {
            player.getInventory().setSearch(new SearchByAll());
            player.getStorageView().setSearch(new SearchByAll());
        }));

        page.addSearchByButton(new SearchByButton("Name", () -> {
            player.getInventory().setSearch(new SearchByName());
            player.getStorageView().setSearch(new SearchByName());
        }));

        page.addSearchByButton(new SearchByButton("Description", () -> {
            player.getInventory().setSearch(new SearchByDesc());
            player.getStorageView().setSearch(new SearchByDesc());
        }));
    }

    void setupCrafting(ItemCraftPage page, Player player) {
        page.setCraftAction((def) -> {
            ItemInterface craftedItem = def.craft(player.getInventory());
            if (craftedItem != null) {
                player.getInventory().addOne(craftedItem);
                System.out.println("Crafted item: " + craftedItem.getDefinition().getName());
            } else {
                System.out.println("Failed to craft item.");
            }
        });
    }

    void setupUncrafting(ProductPage page, Player player) {
        page.setUncraftAction((item) -> {
            if (item instanceof CraftableItem) {
                List<Item> components = item.getDefinition().uncraft(player.getInventory());
                for (Item comp : components) {
                    player.getInventory().addOne(comp);
                }
                player.getInventory().removeOne(item.getDefinition());
            } else {
                System.out.println("Item is not a craftable item.");
            }
        });
    }
    
}
