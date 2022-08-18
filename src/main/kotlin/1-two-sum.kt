class TwoSum {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        // 7, 2, 4
        // target = 9

        // 1st:
        // index = 0, value = 7
        // diff = 9 - 7 = 2
        // add [7 -> 0] to the map

        // 2nd:
        // index = 1, value = 2
        // diff = 9 - 2 = 7
        // 7 exists in the map, so return [1, 0]

        val numToIndex = hashMapOf<Int, Int>()
        nums.forEachIndexed { index, value ->
            val diff = target - value
            val diffIndex = numToIndex[diff]
            if (diffIndex != null) {
                return intArrayOf(numToIndex[diff]!!, index)
            } else {
                numToIndex[value] = index
            }
        }

        return intArrayOf()
    }
}

fun main() {
    var input = intArrayOf(3,3)
    val target = 6
    println(TwoSum().twoSum(input, target).toList())
}