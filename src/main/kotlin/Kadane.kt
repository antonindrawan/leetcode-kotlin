import kotlin.math.max

class Kadane {
    fun maxSum(intList: List<Int>): Int {
        if (intList.isEmpty()) return 0

        var indexFromTo = Pair(0, 0)
        var maxSum = intList[0]
        var currentSum = 0
        intList.forEachIndexed { index, it ->
            if (currentSum < 0) currentSum = 0

            // determine the start index (add 'run' to execute it)
            {
                if (currentSum == 0) {
                    indexFromTo = Pair(index, index)
                }
            }
            currentSum += it

            // determine the end index (add 'run' to execute it)
            {
                if (maxSum <= currentSum) {
                    indexFromTo = Pair(indexFromTo.first, index)
                }
            }
            maxSum = max(currentSum, maxSum)

        }
        // println("From ${indexFromTo.first} to ${indexFromTo.second} has the biggest sum: ${maxSum}")
        return maxSum
    }
}