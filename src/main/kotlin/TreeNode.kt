class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null

    companion object {
        fun insert(node: TreeNode, num: Int) {
            if (num > node.`val`) {

                if (node.right == null) {
                    node.right = TreeNode(num)
                } else {
                    insert(node.right!!, num)
                }
            } else {
                if (node.left == null) {
                    node.left = TreeNode(num)
                } else {
                    insert(node.left!!, num)
                }
            }
        }

        fun printNode(node: TreeNode) {
            println(node.`val`)
            if (node.left != null) {
                printNode(node.left!!)
            }
            if (node.right != null) {
                printNode(node.right!!)
            }
        }
    }
}