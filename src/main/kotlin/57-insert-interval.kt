import kotlin.math.*

class Solution57 {
    fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {

        // possible cases
        // [1,2] [ 3, 4] [5, 7]   ==> [1,2] [ 3, 4] [5, 10]
        //               [6, 10]

        // [1,2] [ 3, 4] [10, 11]   ==> [1,2] [ 3, 4] [6, 9] [10, 11]
        //               [6, 9]

        // Non existing use case (the lowest new interval > the highest intervals)
        // [1,2] [ 3, 4] [5, 7]         ==> [1,2] [ 3, 4] [5, 7] [8, 9]
        //                      [8, 9]

        // Non existing use case (the highest new interval < the lowest intervals)
        // [3, 4] [5, 7] [8, 9]      ==> [1,2] [ 3, 4] [5, 7] [8, 9]
        //                      [1, 2]

        var result :MutableList<IntArray> = mutableListOf()

        if (intervals.size == 0) {
            result.add(newInterval)
        } else if (newInterval[0] > intervals.last()[1]) {
            result.addAll(intervals)
            result.add(newInterval)
        } else if (newInterval[1] < intervals.first()[0]) {
            result.add(newInterval)
            result.addAll(intervals)
        } else {
            // Algorithm
            // 1. Find the lowest index overlapped by the new interval
            // 2. Find the highest index overlapped by the new interval
            // Copy from 0 -> prior to the lowest index
            // Append the new interval
            // Copy from the highest overlapped index to the end of the list

            // The brute force way is to iterate one by one -> time complexity is O(n)
            // More efficient way is to use a binary search -> time complexity is O(log n)
            val lowestIndex = binarySearchLower(intervals, newInterval[0], 0, intervals.lastIndex)
            val highestIndex = binarySearchUpper(intervals, newInterval[1], 0, intervals.lastIndex)
            println(lowestIndex)
            println(highestIndex)
            //assert(lowestIndex <= highestIndex)

            var addItem = { from: Int, to: Int ->
                for (i in from..to) {
                    result.add(intervals[i])
                }
            }
            addItem(0, lowestIndex - 1)

            // insert the new interval here
            val lowestNode = intervals[lowestIndex]
            val highestNode = intervals[highestIndex]

            var newNode = intArrayOf(min(newInterval[0], lowestNode[0]), max(newInterval[1], highestNode[1]))
            result.add(newNode)

            addItem(highestIndex + 1, intervals.lastIndex)
        }

        return result.toTypedArray()
    }

    fun binarySearchUpper(intervals : Array<IntArray>, target: Int, from: Int, to: Int) : Int {
        if (from >= to) return from
        val mid = (from + to) / 2

        // GIVEN [1, 3][6,9][14,14][15,18]
        // WHEN target is 15
        // THEN return the index of [11, 13]
        if (target >= intervals[mid][0]) {
            // compare with the upper bound of the previous node
            if (target < intervals[mid + 1][0]) {
                return mid
            } else {
                return binarySearchUpper(intervals, target, mid + 1, to)
            }
        } else  {
            return binarySearchUpper(intervals, target, from, mid - 1)
        }
        // list [1,6][7,9][10,11][15,20]. target is [12,13]. Lowest: 3 & highest 2 (no overlap)
    }
    fun binarySearchLower(intervals : Array<IntArray>, target: Int, from: Int, to: Int) : Int {
        if (from > to) return from

        val mid = (from + to) / 2

        // bigger than the current node's upper bound but smaller than the next node's upper bound
        // GIVEN [1,3] [5,7]..
        // WHEN lower bound of the target is 1
        // THEN return the index of [5,7]

        if (target > intervals[mid][1]) {
            // compare with the upper bound of the previous node
            if (target < intervals[mid + 1][1]) {
                return mid + 1
            } else {
                return binarySearchLower(intervals, target, mid + 1, to)
            }
        } else  {
            return binarySearchLower(intervals, target, from, mid - 1)
        }
        // list [1,5][7,9][10,11][15,20]. target is [12,13]. Lowest: 3 & highest 2 (no overlap)
    }
}

fun main() {
    val sol = Solution57()
    var result = sol.insert(arrayOf(intArrayOf(1, 3), intArrayOf(6,9)), intArrayOf(2,5))
    result.forEach {
        println(it.toList())
    }

    result = sol.insert(arrayOf(intArrayOf(2, 9), intArrayOf(10, 10),intArrayOf(37, 40), intArrayOf(41,43), intArrayOf(44,44), intArrayOf(45,48), intArrayOf(49,49), intArrayOf(50,53)), intArrayOf(44,45))
    result.forEach {
        println(it.toList())
    }
}