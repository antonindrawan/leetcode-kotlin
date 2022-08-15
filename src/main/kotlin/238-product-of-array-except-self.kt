import java.util.*

class `238-product-of-array-except-self` {

    fun productExceptSelf(nums: IntArray): IntArray {

        var answer = MutableList<Int>(nums.size, { 1 })
        for (i in 1..nums.size - 1) {
            answer[i] = answer[i - 1] * nums[i - 1]
        }
        println(answer.toString())
        var right = 1
        for (i in nums.size - 1 downTo 0) {
            answer[i] = answer[i] * right
            right *= nums[i]
        }
        return answer.toIntArray()
    }
}

fun main() {
    val sol = `238-product-of-array-except-self`()
    println(Arrays.toString(sol.productExceptSelf(intArrayOf(1, 2 , 3, 4))))
}