class Solution70 {
    fun climbStair(n: Int) : Int {
        val uniqueSteps : MutableList<Int> = MutableList(n + 1) {0}
        uniqueSteps[0] = 1
        uniqueSteps[1] = 1

        // fibbonaci
        for (i in 2..n) {
            uniqueSteps[i] = uniqueSteps[i - 1] + uniqueSteps[i - 2]
        }

        return uniqueSteps[n]
    }

    fun dp() {
        for (i in 1..2) {

        }
    }
}

fun main() {
    val sol = Solution70()
    assert(sol.climbStair(1) == 1)
    assert(sol.climbStair(2) == 2)
    assert(sol.climbStair(3) == 3)
    assert(sol.climbStair(4) == 5)
    assert(sol.climbStair(5) == 8)


}
