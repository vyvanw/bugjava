public class ItemNotCraftableException extends Exception {
    private ItemInterface item;

    public ItemNotCraftableException(ItemInterface item) {
        super("The given item is not craftable: " + item.getName());
        this.item = item;
    }

    public ItemInterface getItem() {
        return item;
    }
}
