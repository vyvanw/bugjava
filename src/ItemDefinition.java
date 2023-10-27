import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDefinition {
    private String name;
    private String description;
    private String[] componentNames;
    private boolean isBaseItem;
    private Optional<Double> weight;

    public ItemDefinition(String n, String desc, Optional<Double> weightIfBase, String[] components) {
        name = n;
        description = desc;
        componentNames = components;
        isBaseItem = weightIfBase.isPresent();
        weight = weightIfBase;

        // This may be helpful for the compsite pattern to find the appropriate item definitions
        ItemDictionary dict = ItemDictionary.get();

    }

    /**
     * Create an instance of the item described by this ItemDefinition.
     * If the Item is made up of other items, then each sub-item should also be created.
     * @return An Item instance described by the ItemDefinition
     */
    public Item create() {
        Item item = new Item(this);
        // An ItemDefinition for a craftable item might follow a similar pattern
        // to how a craftable/composite item looks.
        return item;
    }
    
    

    public ItemInterface create(List<ItemInterface> components) {
        if (isBaseItemDef()) { //if base item, simply return the item
            return new Item(this);
        } 
        CraftableItem craftedItem = new CraftableItem(this);
        for (ItemInterface component : components) {
            craftedItem.addComponent(component);
        }
        return craftedItem;
    }

    public ItemInterface craft(Inventory sourceInventory) throws Exception {
        String[] components = componentsString().split(",");
        List<ItemInterface> craftItem = new ArrayList<>();
        boolean rollback = false;
        
        // Check components from the source inventory
        for (String comp : components) {
            Optional<ItemDefinition> componentDef = ItemDictionary.get().defByName(comp.trim());
            if (componentDef.isPresent()) {
                Item componentItem = (Item) sourceInventory.removeOne(componentDef.get()); //remove components from player inventory
                if (componentItem == null) { //if no components are found
                    rollback = true;
                    break;  // Trigger crafting rollback
                }
                craftItem.add(componentItem); //otherwise, add to craft list
            }
        }
    
        // If rollback, return the components to the inventory
        if (rollback) {
            for (ItemInterface item : craftItem) {
                sourceInventory.addOne(item);
            }
            throw new Exception("Crafting failed due to missing components.");
        }
    
        // Craft and return the new item
        return create(craftItem);
    }

    public List<Item> uncraft(Inventory targetInventory) {
        List<Item> componentsList = new ArrayList<>();
        
        String[] components = componentsString().split(",");
        for (String comp : components) {
            Optional<ItemDefinition> componentDef = ItemDictionary.get().defByName(comp.trim());
            if (componentDef.isPresent()) {
                componentsList.add(componentDef.get().create());
            }
        }
        return componentsList;
    }
    

    // ItemDefinition might "craft" and return an item, using items from some source inventory.
    // You might use the Milestone 1 Basket transaction code as a guide
    
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Format: {ITEM 1}, {ITEM 2}, ...
     * @return a String of sub-item/component names in the above format
     */
    public String componentsString() {
        String output = "";
        for (String componentName : componentNames) {
            output += componentName + ", ";
        }
        return output;
    }

    public boolean isBaseItemDef() {
        return isBaseItem;
    }

    public Optional<Double> getWeight() {
        return weight;
    }

    public boolean equals(Item other) {
        return isOf(other.getDefinition());
    }

	public boolean isOf(ItemDefinition def) {
		return getName().equals(def.getName());
	}

}
