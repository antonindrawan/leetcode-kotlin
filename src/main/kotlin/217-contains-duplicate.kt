
class Solution {
    fun containsDuplicate(nums: IntArray): Boolean {
        var duplicateDict = mutableSetOf<Int>()
        for (n in nums) {
            if (duplicateDict.contains(n)) {
                return true
            }
            else {
                duplicateDict.add(n)
            }
        }
        return false
    }
}

fun main() {
    val sol: Solution = Solution()
    val input: IntArray = intArrayOf(1, 2, 3)
    println("Hello world! " + sol.containsDuplicate(input))
}