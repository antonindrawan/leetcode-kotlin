class LRUCache(capacity: Int) {

    // need a double link list & hashmap.
    // - the hash map is used to store the data
    // - the double link list is used to define the order

    // [1, pointer to the node1]
    // [2, pointer to the node2]
    // double link list: head <-> node2 <-> node1 <-> tail

    class Node (val key: Int = 0, var data: Int = 0) {
        var prev: Node? = null
        var next: Node? = null
    }

    var dataStore = hashMapOf<Int, Node>()
    var head: Node = Node()
    var tail: Node = Node()
    var maxItem: Int

    init {
        maxItem = capacity

        // (head) <-> (tail)
        head.next = tail
        tail.prev = head
    }

    /**
     * Return the value of the key if the key exists, otherwise return -1
     */
    fun get(key: Int): Int {
        // cache:
        // [1, 10], [2, 20], [3, 30]
        // get(2)
        //  - returns 20
        //  - change the order [2, 20], [1, 10], [3, 30]

        // don't forget to change the access order
        val valueNode = dataStore.get(key)
        return if (valueNode != null) {
            moveToFront(valueNode)
            valueNode.data
        } else {
            -1
        }
    }

    private fun moveToFront(node: Node) {
        removeNode(node)
        addNodeToFront(node)
    }

    private fun removeNode(node: Node) {
        // assume node (2) is to be removed
        //
        // (1) -> (2) -> (3)
        //         ^
        //       remove
        //
        // result: (1) -> (3)

        val prev: Node? = node.prev
        val next: Node? = node.next
        prev?.next = next
        next?.prev = prev

        // unset adjacent (optionally)
        node.prev = null
        node.next = null
    }

    private fun addNodeToFront(node: Node) {
        // assume head (sentinel) is valid and always has a next
        // Given: (head) <-> (tail)
        // When: add (1) to front
        // Then: (head) <-> (1) <-> (tail)

        var prevNext = head.next
        prevNext?.prev = node
        node.prev = head
        node.next = prevNext
        head.next = node
    }

    fun put(key: Int, value: Int) {
        var node = dataStore.get(key)

        if (node != null) {
            // update the value and advance the node
            node.data = value
            moveToFront(node)
        } else {
            if (dataStore.size >= maxItem) {
                // remove the last item
                var lastNode = tail.prev
                if (lastNode != null) {
                    removeNode(lastNode)
                    dataStore.remove(lastNode.key)
                }
            }

            // add the node
            val node = Node(key, value)
            addNodeToFront(node)
            dataStore.put(key, node)
        }
    }
}

fun main() {

}