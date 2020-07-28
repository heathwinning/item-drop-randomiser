import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ItemSpawnEvent
import org.bukkit.inventory.ItemFactory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import kotlin.math.*
import kotlin.random.Random
import kotlin.reflect.KFunction1

class ItemDropRandomiser : JavaPlugin(), Listener {
    override fun onEnable() {
        server.pluginManager.registerEvents(ItemDropListener(logger), this)
    }

    override fun onDisable() {
    }

}

class ItemDropListener(
    private val logger: Logger
) : Listener {
    val itemList = Material.values().filter { it.isItem() }
    val randomItemList = itemList.toMutableList().shuffled()
    val randomItemMap: Map<Material, Material> = itemList.zip(randomItemList).toMap()

    @EventHandler
    fun onItemSpawnEvent(event: ItemSpawnEvent) {
        randomiseItem(event.entity.itemStack)
    }

    private fun randomiseItem(item: ItemStack) {
        item.type = randomItemMap.getOrDefault(item.type, item.type)
    }

    private fun randomAmount(): Int {
            val lambda = 1
            return min(64*64, (-log(1- Random.Default.nextDouble(), E)/lambda).toInt() + 1)
    }
}
