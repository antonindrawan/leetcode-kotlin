class LinkedListCycle {
    fun hasCycle(head: ListNode?): Boolean {
        if (head == null || head.next == null) return false
        var turtle = head
        var hare = head.next

        while (hare != null && hare.next != null) {
            if (turtle == hare) {
                return true
            }
            turtle = turtle!!.next
            hare = hare.next!!.next
        }
        return false
    }
}

fun main() {
    val head = ListNode(1)
    head.next = ListNode(2)
    head.next?.next = head
    println(LinkedListCycle().hasCycle(head))
}