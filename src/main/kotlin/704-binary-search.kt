class BSearch {
    fun search(nums: IntArray, target: Int): Int {
        return bsearch(nums, target, 0, nums.size - 1)
    }

    private fun bsearch(nums: IntArray, target: Int, low: Int, high: Int): Int {
        if (low > high) return -1
        val mid = (low + high) / 2
        return if (nums[mid] < target) {
            // check on the right side
            bsearch(nums, target, mid + 1, high)
        } else if (nums[mid] > target) {
            // check on the left side
            bsearch(nums, target, low, mid - 1)
        } else {
            mid
        }
    }
}

fun main() {
    val nums = intArrayOf(-1,0,3,5,9,12,13)
    val target = 13
    println(BSearch().search(nums, target))
}