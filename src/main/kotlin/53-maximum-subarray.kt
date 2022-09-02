import kotlin.math.*

class Solution53 {
    // -2,1,-3,4,-1,2,1,-5,4

    // -2.
    //  Max = 0
    // 1.
    //  (-2) + 1 = -1. It is negative. Max = 1 because it is positive.
    // -3. 1 + (-3) = -2. It is negative, so update the Max to 0
    // 4. -2 + 4 = 2. It is positive, Max(2, 4). Pick the max
    // -1.
    fun maxSubArray(nums: IntArray): Int {
        return Kadane().maxSum(nums.toList())
        //return o_n(nums)
    }

    fun o_n(nums: IntArray): Int {
        //if (nums.size == 1) return nums[0]
        // Input: -2,1,-3,4,-1,2,1,-5,4

        // maxContiguousValue = nums[0]
        // currentMax = 0

        // -2, currentSum(0) + (-2) = -2 (negative value -> reset to 0)
        // 1, currentSum(0) + 1 = 1, maxContiguousValue = 1
        // -3, currentSum(1)+(-3) = -2 (negative value -> reset to 0), maxContiguousValue = 1
        // 4, currentSum(0) + 4 = 4, , maxContiguousValue = 4
        // -1, currentSum(4) + (-1) = 3 (still positive), maxContiguousValue = 4 (max between currentMax and maxContiguousValue)
        // 2, currentSum(3) + 2 = 5 (still positive), maxContigousValue = 5
        // 1, currentSum(5) + 1 = 6 (still positive), maxContigousValue = 6
        // -5, currentSum(6) + (-5) = 1 (still positive), maxContigousValue = 6
        // 4, currentSum(1) + (4) = 5 (still positive), maxContigousValue = 6

        var maxContiguousValue = nums[0]
        var indexFromTo = Pair(0, 0)
        var currentSum = 0
        nums.forEachIndexed { i, value ->
            // determine the start index (also update the end index to be equal)
            {
                // A new index happens when it is the current max becomes positive for the first time.
                if (currentSum == 0) {
                    indexFromTo = Pair(i, i)
                }
            }
            currentSum += value

            // determine the end index
            {
                if (maxContiguousValue <= currentSum) {
                    indexFromTo = Pair(indexFromTo.first, i)
                }
            }

            maxContiguousValue = max(maxContiguousValue, currentSum)
            if (currentSum < 0) currentSum = 0
        }

        println("SubArray ${indexFromTo.first} to ${indexFromTo.second} has the max contiguous value ${maxContiguousValue}")

        return maxContiguousValue
    }

    fun bruteForce(nums: IntArray): Int {
        // Input: -2,1,-3,4,-1,2,1,-5,4
        // Approach:
        // loop from index 0 to last index and calculate the sum
        // loop from index 1 to last index and calculate the sum
        // loop from index 2 to last index and calculate the sum
        // ...
        // loop from last index to last index and calculate the sum

        // For each operation, track the max value of the sum.

        var maxContiguousValue = Int.MIN_VALUE
        for (i in 0..nums.lastIndex) {
            var sum = 0
            for (j in i  ..nums.lastIndex) {
                sum += nums[j] // 0 + (-2) + 1 + ... + 4
                maxContiguousValue = max(maxContiguousValue, sum)
            }
        }
        return maxContiguousValue
    }
}

fun main() {
    val sol = Solution53()
    println(sol.maxSubArray(intArrayOf(-2,1,-3,4,-1,2,1,-5,4)))
    println(sol.maxSubArray(intArrayOf(-2,1)))
    println(sol.maxSubArray(intArrayOf(-1)))
    println(sol.maxSubArray(intArrayOf(-2,-1)))

}