import com.sun.source.tree.Tree



class BinaryTree {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) {
            return null
        }

        val left = root.left
        val right = root.right

        root.left = right
        invertTree(root.left)

        root.right = left
        invertTree(root.right)

        return root
    }
}

fun main() {
    var input = listOf (4,2,7,1,3,6,9,10)
    //input = listOf(0)

    val root = TreeNode(input[0])
    input.forEachIndexed { index, value ->
        if (index > 0) {
            TreeNode.insert(root, value)
        }
    }

    TreeNode.printNode(root)
    println()

    val btree = BinaryTree()
    btree.invertTree(root)
    TreeNode.printNode(root)
}