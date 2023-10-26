import java.util.Optional;

public class BaseItem implements ItemInterface {
    private String name;
    private String description;
    private double weight;
    private ItemDefinition definition; // Assume an appropriate constructor or method in ItemDefinition

    // Constructor
    public BaseItem(String name, String description, double weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.definition = new ItemDefinition(name, description, Optional.of(weight), null); // Assuming ItemDefinition has this constructor
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public ItemDefinition getDefinition() {
        return definition;
    }

    @Override
    public String getCompositionDescription() {
        return name; // Since it's a basic item, it's only composed of itself.
    }

    @Override
    public boolean equals(ItemInterface other) {
        return this.definition.equals(other.getDefinition()); // Assuming ItemDefinition has a proper equals method
    }

    @Override
    public boolean isOf(ItemDefinition def) {
        return this.definition.equals(def); // Again, assuming ItemDefinition has a proper equals method
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Description: " + description + ", Weight: " + weight;
    }
}
