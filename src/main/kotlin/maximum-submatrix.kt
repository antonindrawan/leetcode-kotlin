import kotlin.math.*

class MaxSubMatrix {

    fun kadane(intList: List<Int>): Int {
        if (intList.isEmpty()) return 0

        var indexFromTo = Pair(0, 0)
        var maxSum = intList[0]
        var currentSum = 0
        intList.forEachIndexed { index, it ->
            if (currentSum < 0) currentSum = 0

            currentSum += it

            maxSum = max(currentSum, maxSum)

        }
        return maxSum
    }

    fun maxSubMatrix(matrix: List<List<Int>>): Int {
        val dp = MutableList<MutableList<Int>>(matrix.size) { mutableListOf() }

        // Step 1 calculate the sum of each row of matrix
        matrix.forEachIndexed { row, rowMatrix ->
            dp[row].addAll(IntArray(rowMatrix.size) {0}.toList())
            rowMatrix.forEachIndexed { col, value ->
                dp[row][col] = if (col == 0) value else dp[row][col - 1] + value

                print("${dp[row][col]} ".padStart(4))
            }
            println()
        }
        // Output example:
        /*
          0  -2  -9  -9
          9  11   5   7
         -4  -3  -7  -6
         -1   7   7   5
         */
        //
        var maxSum = Int.MIN_VALUE

        val rowLastIndex = matrix.lastIndex
        val colLastIndex = matrix[0].lastIndex

        // Iterate from start col
        for (i in 0..colLastIndex) {
            // Iterate from the i to the last column
            for (j in i..colLastIndex) {

                val kadaneList = mutableListOf<Int>()
                // Iterate through every row for the sub matrix
                for (k in 0..rowLastIndex) {

                    // if the start col (i) == 0, use the value in the dp
                    if (i == 0) {
                        kadaneList.add(dp[k][j])
                    } else {
                        // if the start col (i) != 0, add the diff against the previous column
                        kadaneList.add(dp[k][j] - dp[k][i-1])
                    }
                }
                val maxSubSum = kadane(kadaneList)
                println("kadane List: ${kadaneList.toList()}. MaxSubSum: $maxSubSum")

                maxSum = max(maxSum, maxSubSum)
                println("MaxSum now: $maxSum")
            }
            println("move to the next col. Finding the diff against col ${i+1}")
        }


        //return bruteForce(matrix)
        return maxSum
    }

    fun bruteForce(matrix: List<List<Int>>): Int {

        var maxVal = Int.MIN_VALUE

        var leftCol = 0; var leftRow = 0; var rightCol = 0; var rightRow = 0

        matrix.forEachIndexed { i, row ->
            row.forEachIndexed { j, col ->
                var sum = 0
                for (k in i..matrix.lastIndex) {
                    for (l in 0 .. row.lastIndex) {
                        var subSumMatrix = 0
                        for (m in i..k) {
                            for (n in j..l) {
                                subSumMatrix += matrix[m][n]
                            }
                        }

                        if (maxVal < subSumMatrix) {
                            maxVal = subSumMatrix

                            leftRow = i
                            leftCol = j

                            rightRow = k
                            rightCol = l
                        }
                    }
                }
            }
        }

        println("${leftRow}, ${leftCol} - ${rightRow}, ${rightCol}")
        return maxVal
    }
}


fun main() {

    /*
    0 -2 -7  0
    9  2 -6  2
    -4  1 -4  1
    -1  8  0 -2
     */
    var inputString = """
        0 -2 -7  0
        9  2 -6  2
        -4  1 -4  1
        -1  8  0 -2
    """.trimIndent()

    /*
    inputString = """
         1  2  3  4  5
 6  7  8  9 10
11 12 13 14 15
16 17 18 19 20
21 22 23 24 25
    """.trimIndent()
*/

    var lines = inputString.split("\n")
    val input2dArray : MutableList<List<Int>> = mutableListOf()

    lines.forEachIndexed { index, line ->
        val intLines = line.trim().split("\\s+".toRegex()).map {
            it.toInt()
        }
        input2dArray.add(intLines)
    }

    input2dArray.forEach(::println)

    println(MaxSubMatrix().maxSubMatrix(input2dArray))

}