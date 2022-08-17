import com.sun.source.tree.Tree

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

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

fun main() {
    var input = listOf (4,2,7,1,3,6,9,10)
    //input = listOf(0)

    val root = TreeNode(input[0])
    input.forEachIndexed { index, value ->
        if (index > 0) {
            insert(root, value)
        }
    }

    printNode(root)
    println()

    val btree = BinaryTree()
    val invertedTree = btree.invertTree(root)
    //printNode(invertedTree)
    printNode(root)
}