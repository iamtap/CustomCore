# CustomCore
CustomCore is a powerful API used for creating custom items, blocks and (soon) worlds. Installing this as a standalone plugin will grant no additional features.

## License
It's my code I just want to be credited when necessary.

## API Changelog
### Version 1.0.0
 - Initial Commit
 - Implemented Custom Items
 - Implemented Custom Storage

### Version 1.0.1
 - Removed Lombok Dependency
 - Updated to Spigot-API 1.20.2
 - Implemented Custom Blocks
 - Implemented Custom Inventories

### Min. Server Requirements ###
- Running on Java 17
- Spigot or PaperSpigot (1.20+)
- 2GB of RAM

### Getting Started ###

#### Custom Items ####
#### 1. How to create a custom item ####
The first step to creating a custom item is to extends the CustomItem class.

```java
  public class ShadowSword extends CustomItem {
    @Override
    public String getName() {
        return "&7Shadow Sword";
    }

    @Override
    public Material getMaterial() {
        return Material.AMETHYST_SHARD;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                colorize("")
        );
    }

    @Override
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }
}
```

After defining your custom item you need to register it in order to retrieve it later.

```java
    @Override
    public void onEnable() {
        registerItems(new FlightStick());
    }
```
