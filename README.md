# CustomCore
CustomCore is a powerful API used for creating custom items, blocks and (soon) worlds. Installing this as a standalone plugin will grant no additional features.

### Min. Server Requirements ###
- Running on Java 17
- Spigot or PaperSpigot (1.20+)
- 2GB of RAM

### Getting Started ### 

#### Creating Custom Items ####
The first step to creating a custom item is to extends the CustomItem class. Each item has it's own
ID based on the class name. 
<br/>
If my class name is FlightStick the ID will be "flightstick". You can not register anytwo items with the same ID or class name.

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
<br/>

After defining your custom item you need to register it in order to retrieve it later. <br/>
You can do this by calling the CustomCore.registerItems() method in your onEnable method.

```java
    @Override
    public void onEnable() {
        registerItems(new FlightStick());
    }
```
<br/>

Retrieving your custom item is a little different. In order to retrieve your item you need to
access the item map using the items ID. In this case my class name is "ShadowSword" so the ID of the item is "shadowsword" all lower case.

```java
 ItemStack shadowSword = CustomCore.customItemMap.get("shadowsword").getItem();

// Your logic here. (ie: give to player or add to GUI)
```

#### Creating Custom Blocks ####
Docs are to be written...
