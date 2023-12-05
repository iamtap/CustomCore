# CustomCraft API
CustomCraftAPI empowers developers to create server addon packs that match the immersive experience of mods, bringing lightweight and efficient features to Minecraft, such as custom blocks, items, and GUIs— all without requiring players to download any additional software. 
<br/>
<br/>
If you want to know more about the development process check out my channel where it was made! (https://www.youtube.com/@michaelfoulk)
<br/>
<br/>
<b>
Installing this plugin on your Minecraft server standalone will grant no additional. Either implement the code into your codebase or use install as a plugin to use as an external library.
If you find any problems you can contact me on Discord or report an issue here on GitHub.
<b/>
<br/>

<b>Documentation is still being written. Thank you for being patient.<b/>

### Quick Links
- Server Requirements
- Custom Items
- Custom Blocks
- Custom Inventories



### Min. Server Requirements ###
- Running on Java 17
- Spigot or PaperSpigot
  - Version 1.20.2
- Minimum of 2GB of RAM



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
        registerItems(new ShadowSword());
    }
```
<br/>

Retrieving your custom item is a little different. In order to retrieve your item you need to
access the item map using the items ID. In this case my class name is "ShadowSword" so the ID of the item is "shadowsword" all lower case.

```java
 ItemStack shadowSword = CustomCore.customItemMap.get("shadowsword").getItem();

// Your logic here. (ie: give to player or add to GUI)
```

<br/>

#### Creating Custom Blocks ####
Docs are to be written...

<br/>

#### Creating Custom Inventories ####
Docs are to be written.

<br/>
