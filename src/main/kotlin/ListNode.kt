class ListNode(var `val`: Int) {
    var next: ListNode? = null

    fun printNextNodes() {
        print(`val`)
        var node: ListNode? = next
        while (node != null) {
            print(" ${node!!.`val`}")
            node = node!!.next
        }
        println()
    }
}