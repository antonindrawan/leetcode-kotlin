
// https://leetcode.com/problems/lfu-cache/

class LFUCache(val capacity: Int) {

    class Node (val key :Int = 0, var value: Int = 0, var accessedCount: Int = 0) {
        var next: Node? = null
        var prev: Node? = null

        override fun toString(): String {
            return "$key: $value. Accessed[$accessedCount]"
        }
    }

    // key -> Node
    var dataStore = hashMapOf<Int, Node>()
    

    // A doubly linked list
    // (head) <-> (most frequently used item) <-> (...) <-> (least frequently used item) <-> (tail)

    // capacity: 3
    // Given: (head) <-> (A, 3) <-> (B, 2) <-> (C, 1) <-> (tail)
    // When: get(C). C is now accessed twice, as well as (B), but it is recently used, so swap the position.
    // Then: (head) <-> (A, 3) <-> (C, 2) <-> (B, 2) <-> (tail)

    var head: Node = Node()
    var tail: Node = Node()

    init {
        // (h) <-> (t)
        head.next = tail
        tail.prev = head
    }

    /**
     * Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
     */
    fun get(key: Int): Int {
        val node = dataStore.get(key)
        return if (node != null) {
            // promote the node
            _promoteNode(node)

            node.value
        } else {
            -1
        }
    }

    fun put(key: Int, value: Int) {
        if (capacity == 0) {
            return
        }

        val node = dataStore.get(key)
        if (node != null) {
            // update, increment the frequency and promote the node
            node.value = value

            _promoteNode(node)
        } else {
            if (dataStore.size == capacity) {
                val lfuNode = tail.prev
                if (lfuNode != null) {
                    _removeNode(lfuNode)
                    dataStore.remove(lfuNode.key)
                }
            }

            // create a new node and push it to the dataStore with accessedCount 1
            val newNode = Node(key, value, 0)
            dataStore.put(key, newNode)

            // Register the node before the tail, because it is least frequently used
            _insertNodeAfterTarget(newNode, tail.prev!!)

            // Shift to the more frequently used if the frequency is the same as the adjacent
            _promoteNode(newNode)
        }
    }

    private fun _promoteNode(node: Node) {
        // Given a tie condition: (A, 1), (B, 1), (C, 1)
        // When: (C, 1) is promoted
        // Then: (C, 1), (A, 1), (B, 1)

        // Given: (head), (D, 2), (E, 2), (A, 1), (B, 1), (C, 1)
        // When: (B, 1) just gets accessed, it becomes (B, 2)
        // Then: (head), (B, 2), (D, 2), (E, 2), (A, 1), (C, 1)

        node.accessedCount += 1

        // find the target node which is accessed higher than current (TODO: This is not efficient)
        var prev = node.prev
        while (prev != head && prev != null && node.accessedCount >= prev.accessedCount) {
            prev = prev.prev
        }

        _removeNode(node)

        // prev is either the head or a higher frequently accessed node
        // prev is guaranteed to be non-null
        _insertNodeAfterTarget(node, prev!!)
    }

    //
    private fun _insertNodeAfterTarget(node: Node, targetNode: Node) {
        // Given: (target) <-> (tail) (or (A) node)
        // When: Insert (n)
        // Then: (target) <-> (n) <-> (tail) (or (A) node)
        val targetNodeNext = targetNode.next

        node.prev = targetNode
        node.next = targetNodeNext

        targetNode.next = node
        targetNodeNext?.prev = node
    }

    private fun _removeNode(node: Node) {
        // Given: (1) <-> (node) <-> (2)
        // When: (node) is removed)
        // Then: (1) <-> (2)
        val next = node.next
        val prev = node.prev
        next?.prev = prev
        prev?.next = next

        // unset (optionally)
        node.prev = null
        node.next = null
    }
}

fun main() {

    var command = listOf("LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get")
    var params = listOf(2, Pair(1, 1), Pair(2, 2), 1, Pair(3, 3), 2, 3, Pair(4, 4), 1, 3, 4)

    command = listOf("LFUCache","put","get")
    params = listOf(0, Pair(0 , 0),0)

    val lfu = LFUCache(params[0] as Int);
    var output = ""
    command.forEachIndexed lit@ { i, s ->
        if (s == "LFUCache") {
            // skip
            output += "null, "
            return@lit
        }
        else if (s == "put") {
            val param: Pair<Int, Int> = params[i] as Pair<Int, Int>
            lfu.put(param.first, param.second)
            output += "null, "
        } else if (s == "get") {
            val result = lfu.get(params[i] as Int)
            output += "$result, "
        }
    }
    println("${output.substring(0, output.length - 2)}")
}