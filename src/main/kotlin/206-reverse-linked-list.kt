
class Solution206 {
    var reversedNodeHead: ListNode? = null
    fun reverseList(head: ListNode?): ListNode? {
        //if (head == null) return null
        //reverse(head!!)
        //return reversedNodeHead
        return reverseOptimum(head)
    }

    fun reverse(node: ListNode): ListNode {
        if (node.next != null) {
            val head = reverse(node.next!!)
            val newNode = ListNode(node.`val`)
            head.next = newNode
            return head.next!!
        } else {
            reversedNodeHead = ListNode(node.`val`)
            return reversedNodeHead!!
        }
    }

    fun reverseOptimum(head: ListNode?) : ListNode? {
        if (head == null) return null
        var reversedHead: ListNode? = null
        var prev: ListNode? = null

        var current = head
        while (current != null) {
            reversedHead = current
            current = current.next

            reversedHead.next = prev
            prev = reversedHead
        }
        return reversedHead!!
    }
}

fun main() {
    val sol = Solution206()
    val input = listOf(1, 2, 3, 4, 5)
    val head = ListNode(1)
    var previousNode = head
    input.forEachIndexed { index, value ->
        if (index > 0) {
            val node = ListNode(value)
            previousNode.next = node
            previousNode = node
        }
    }
    val result = sol.reverseList(head)
    result?.printNextNodes()

}