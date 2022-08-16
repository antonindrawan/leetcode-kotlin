
// https://leetcode.com/problems/lfu-cache/

class LFUCache(val capacity: Int) {

    class Node (val key :Int = 0, var value: Int = 0, var accessedCount: Int = 0) {
        var next: Node? = null
        var prev: Node? = null

        override fun toString(): String {
            return "$key: $value. Accessed[$accessedCount]"
        }
    }

    class FrequentNodes () {
        // list of nodes in a least recently order
        //  [most recently used node, ..., least recently used node]
        var nodeList = listOf<Node>() // TODO: do we need this?

        // define the order in the list
        // (head) <-> (most recently used node) <->  ... <-> (least recently used node) <-> (tail)
        var head: Node = Node() // most recently used
        var tail: Node = Node() // least recently used

        init {
            head.next = tail
            tail.prev = head
        }

        /**
         * Adds a node after the head sentinel
         *
         * Given: (head) <-> (tail)
         * When: (A) is added
         * Then: (head) <-> (A) <-> (tail)
         */
        fun addNodeToFront(node: Node) {
            var headNext = head.next
            node.prev = head
            node.next = headNext

            headNext?.prev = node
            head.next = node
        }

        /**
         * Removes the given node from the list
         *
         * Given: (head) <-> (A) <-> (tail)
         * When: (A) is removed
         * Then: (head) <-> (tail)
         */
        fun removeNode(node: Node) {
            var prev = node.prev
            var next = node.next

            prev?.next = next
            next?.prev = prev

            node.prev = null
            node.next = null
        }

        /**
         * Gets the last node from the tail
         *
         * Given: (head) <-> (A) <-> (tail)
         * Returns (A)
         */
        fun getLruNode() : Node {
            assert(tail.prev != head)
            assert(tail.prev != null)
            return tail.prev!!
        }

        fun empty() : Boolean {
            return (head.next == tail && tail.prev == head)
        }
    }

    // Keep track the minimum frequency for an eviction if the cache is full.
    var minFrequency = 0

    // Keep track of the frequency to achieve O(1)
    // Map: frequency -> nodes (key, val, freq)
    // 1 -> (A, 10, 1), (B, 11, 1)
    // 2 -> (C, 12, 2)
    // When it is a tie (nodes having the same frequency), the order will be MRU -> LRU.
    var frequencyToNodeMap = hashMapOf<Int, FrequentNodes>()

    // key -> Node
    // (A) -> (A, 10, 1)
    var dataStore = hashMapOf<Int, Node>()


    /**
     * Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
     */
    fun get(key: Int): Int {
        val node = dataStore.get(key)
        return if (node != null) {
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
            // update, and promote the node
            node.value = value

            _promoteNode(node)
        } else {
            if (dataStore.size == capacity) {

                assert (minFrequency > 0)
                val frequentNodes =  frequencyToNodeMap[minFrequency]
                if (frequentNodes != null) {
                    val lruNode = frequentNodes.getLruNode()
                    frequentNodes.removeNode(lruNode)
                    if (frequentNodes.empty()) {
                        frequencyToNodeMap.remove(minFrequency)
                    }

                    dataStore.remove(lruNode.key)
                }
            }

            val newNode = Node(key, value, 0)
            dataStore.put(key, newNode)
            minFrequency = 1
            _promoteNode(newNode)
        }
    }

    private fun _promoteNode(node: Node) {
        // Given a tie condition: [1] -> (A, 1), (B, 1), (C, 1)
        //                        [2] -> (D, 2)
        // When: (C, 1) is promoted
        // Then: Remove (C, 1) from frequentNodes [1] and add it to [2]
        //        [1] -> (A, 1), (B, 1)
        //        [2] -> (C, 2), (D, 2)

        // removal the node from existing frequencyNodes
        if (node.accessedCount > 0) {
            val frequentNodes = frequencyToNodeMap[node.accessedCount]

            if (frequentNodes != null) {
                frequentNodes.removeNode(node)
                if (frequentNodes.empty()) {
                    frequencyToNodeMap.remove(node.accessedCount)

                    // update the minimum
                    if (minFrequency == node.accessedCount) {
                        minFrequency += 1
                    }
                }
            }
        }

        // adding part
        node.accessedCount += 1
        var frequentNodes = frequencyToNodeMap[node.accessedCount]
        if (frequentNodes != null) {
            frequentNodes.addNodeToFront(node)
        } else {
            frequentNodes = FrequentNodes()
            frequentNodes.addNodeToFront(node)
            frequencyToNodeMap.put(node.accessedCount, frequentNodes)
        }
    }
}

fun main() {

    var command = listOf("LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get")
    var params = listOf(2, Pair(1, 1), Pair(2, 2), 1, Pair(3, 3), 2, 3, Pair(4, 4), 1, 3, 4)

    //command = listOf("LFUCache","put","get")
    //params = listOf(0, Pair(0 , 0),0)

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