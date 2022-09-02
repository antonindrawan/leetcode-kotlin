import java.util.*
import kotlin.collections.HashMap

// This is a shortened version, but it is far too slow

class LRUCache2(val capacity: Int) {

    // Approach:
    // Create LRU cache (capacity: 2)
    // Put (1, 1)
    //      LRU = (1, 1)
    // Put (2, 2)
    //      LRU = (2, 2) (1, 1)
    // Get (1)
    //      LRU = (1, 1) (2, 2)
    // Put (3, 3)
    //      LRU is full, thus evict the LRU node (2, 2)
    //      LRU = (3, 3) (1, 1)


    // For fast lookup
    // key -> node
    var keyNodeMap: MutableMap<Int, Node> = mutableMapOf()

    // Model the key & value as a node
    data class Node (val key: Int, val value: Int);

    // Store the order of nodes
    var nodeList: LinkedList<Node> = LinkedList()

    fun get(key: Int): Int {
        val node = keyNodeMap[key]
        return if (node == null) {
            -1
        } else {
            // advance the node
            nodeList.remove(node)
            nodeList.addFirst(node)

            node.value
        }
    }

    fun put(key: Int, value: Int) {
        if (capacity == 0) return

        val node = keyNodeMap[key]
        node?.let {
            nodeList.remove(node)
        }

        // add the new node
        val newNode = Node(key, value)

        // put it on the map & list
        keyNodeMap[key] = newNode
        nodeList.addFirst(newNode)

        // check the capacity, evict one if it is full
        if (nodeList.size > capacity) {
            val lruNode = nodeList.removeLast()
            keyNodeMap.remove(lruNode.key)
        }
    }
}

fun main() {
    var command = listOf("LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get")
    var params = listOf(2, Pair(1, 1), Pair(2, 2), 1, Pair(3, 3), 2, Pair(4, 4), 1, 3, 4)

    //command = listOf("LRUCache","put","get")
    //params = listOf(0, Pair(0, 0), 0)

    val lru = LRUCache2(params[0] as Int);
    var output = ""
    command.forEachIndexed lit@ { i, s ->
        if (s == "LRUCache") {
            // skip
            output += "null, "
            return@lit
        }
        else if (s == "put") {
            val param: Pair<Int, Int> = params[i] as Pair<Int, Int>
            lru.put(param.first, param.second)
            output += "null, "
        } else if (s == "get") {
            val result = lru.get(params[i] as Int)
            output += "$result, "
        }
    }
    println("${output.substring(0, output.length - 2)}")
}