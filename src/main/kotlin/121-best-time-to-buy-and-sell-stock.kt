import kotlin.math.*

class Solution121 {
    fun maxProfit(prices: IntArray): Int {
        // starting from left most item
        // calculate the diff IF left <> right and check if max can be updated
        // - track the minimum value (left)
        // - track the max profit (diff with the right)

        // stop condition:
        // - when the right is at the end (out of bounds)

        //7 - 3 - 15
        //l   r
        // if l > r. then left = right
        // if not -> then increment right

        // no transaction anyway
        if (prices.size <= 1) return 0

        // starts with 0 profit (no transaction)
        var highestProfit = 0

        var leftIdx = 0
        var rightIdx = 1
        while (rightIdx < prices.size) {
            val diff = prices[rightIdx] - prices[leftIdx]
            if (diff <= 0) {
                leftIdx = rightIdx
            } else {
                highestProfit = max(highestProfit, diff)
            }
            rightIdx += 1 // check the next item
        }
        return highestProfit
    }
 }

fun main() {
    val sol = Solution121()
    println(sol.maxProfit(intArrayOf(7,1,5,3,6,4))) // 5
    println(sol.maxProfit(intArrayOf(7,3,15,1,5,16,4))) // 5
    println(sol.maxProfit(intArrayOf(7,6,4,3,1))) // 0
}
