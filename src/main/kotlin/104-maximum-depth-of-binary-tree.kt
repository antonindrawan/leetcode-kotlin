import com.sun.source.tree.Tree
import kotlin.math.max

class Solution226 {
    var _maxDepth: Int = 0
    fun maxDepth(root: TreeNode?): Int {

        /*
         * Approaches:
         * a) dfs from root and track the depth
         * b) compare max depth left & right, pick the highest one (also dfs)
         */

        // Approach b:
        if (root == null) return 0
        val (left, right) = Pair(maxDepth(root.left) + 1, maxDepth(root.right) + 1)
        return max(left, right)
    }

    fun dfsApproach(root: TreeNode?) : Int {
        if (root == null) return 0
        var depth = 1
        calcDepth(root!!, depth)
        return _maxDepth
    }

    fun calcDepth(node: TreeNode, depth: Int) {
        _maxDepth = max(_maxDepth, depth)
        if (node.left != null) {
            calcDepth(node.left!!, depth + 1)
        }

        if (node.right != null) {
            calcDepth(node.right!!, depth + 1)
        }
    }
}

fun main() {
    val input = arrayOf(3,9,20,null,null,15,7)
    val root = TreeNode(input[0] as Int)

    input.forEachIndexed { index, value ->
        if (index > 0) {
            if (index % 2 == 0) {
                // insert left
            }
        }
    }
    TreeNode.printNode(root)
    println(Solution226().maxDepth(root))
}
