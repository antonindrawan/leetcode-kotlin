import kotlin.math.max
import kotlin.math.min

class Solution11 {
    fun maxArea(height: IntArray) : Int {
        /*
        Approach:
        - start from the left most and right most
        - calculate the area and track the max area
        - if left's height < right's height, increment left
        - if right's height < left's height, decrement left
        - Stop when left == right
         */
        var maxAreaVal = 0
        var left = 0; var right = height.lastIndex

        while (left < right) {
            var w = right - left
            var h = min(height[left], height[right])

            maxAreaVal = max(maxAreaVal, w * h)

            if (height[left] < height[right]) {
                left += 1
            } else {
                right -= 1
            }
        }

        return maxAreaVal
    }
}

fun main() {
    val input = intArrayOf(1,8,6,2,5,4,8,3,7)
    println(Solution11().maxArea(input))
}