import java.util.ArrayList;
import java.util.List;

public class CraftableItem implements ItemInterface {
    private ItemDefinition definition;
    private List<ItemInterface> components = new ArrayList<>();

    public CraftableItem(ItemDefinition definition) {
        this.definition = definition;
    }

    public void addComponent(ItemInterface component) {
        components.add(component);
    }

    public void removeComponent(ItemInterface component) {
        components.remove(component);
    }

    public List<ItemInterface> getComponents() {
        return components;
    }
    

    @Override
    public String getName() {
        return definition.getName();
    }

    @Override
    public String getDescription() {
        return definition.getDescription();
    }

    @Override
    public double getWeight() {
        return components.stream().mapToDouble(ItemInterface::getWeight).sum();
    }

    @Override
    public ItemDefinition getDefinition() {
        return definition;
    }

    @Override
    public String getCompositionDescription() {
        StringBuilder description = new StringBuilder();
        for (ItemInterface component : components) {
            description.append(component.getName()).append("\n");
        }
        return description.toString();
    }

    @Override
    public boolean equals(ItemInterface other) {
        return this.definition.equals(other.getDefinition());
    }

    @Override
    public boolean isOf(ItemDefinition def) {
        return this.definition.equals(def);
    }
}
