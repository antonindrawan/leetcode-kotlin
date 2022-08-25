import kotlin.math.*
class Solution7 {
    fun reverse(x: Int): Int {
        if (x == 0) return 0

        var isNegative = (x < 0)
        var value = abs(x)
        var reversedValue = 0

        while (value > 0) {
            val reminder = value % 10

            // edge cases: check overflow / underflow
            if (reversedValue > Int.MAX_VALUE / 10) return 0
            reversedValue = reversedValue * 10 + reminder

            value /= 10
        }
        return if (isNegative) {
            reversedValue * -1
        } else {
            reversedValue
        }
    }
}

fun main() {
    val sol = Solution7()
    assert(sol.reverse(1463847412) == 2147483641)
    assert(sol.reverse(2147483647) == 0) // int32 overflow
    assert(sol.reverse(-2147483647) == 0) // int32 overflow
    assert(sol.reverse(0) == 0)
}