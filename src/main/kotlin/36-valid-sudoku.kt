class ValidSudoku {
    var _board: Array<CharArray>? = null

    // row[0..8] valid/not valid
    // col[0..8] valid/not valid
    // sub-box 3x3 [0..8] valid /not valid
    var rowValidity = BooleanArray(9) {false}
    var colValidity = BooleanArray(9) {false}
    var subBoxValidity = BooleanArray(9) {false}

    fun isValidSudoku(board: Array<CharArray>): Boolean {

        _board = board
        board.forEachIndexed { rowIndex, row ->
            //println("$rowIndex, ${row.toList()}")
            row.forEachIndexed { colIndex, col ->
                // rowIndex, colIndex
                if (col != '.') {
                    if (!_validRow(rowIndex) ||  !_validColumn(colIndex) || !_validSubBoxes(rowIndex, colIndex)) {
                        return false
                    }
                }
            }
        }

        return true
    }

    /**
     * Assume that it is a valid number (not a '.')
     */
    private fun _validRow(rowIndex: Int) : Boolean {
        // if not exist, calculate
        if (!rowValidity[rowIndex]) {

            val uniqueNum = hashSetOf<Char>()
            for (col in 0..8) {
                val number = _board?.get(rowIndex)?.get(col)
                assert(number != null)
                if (uniqueNum.contains(number)) {
                    return false
                } else {
                    if (number != '.') {
                        uniqueNum.add(number!!)
                    }
                }
            }
            rowValidity[rowIndex] = true
        }
        return true
    }

    private fun _validColumn(colIndex: Int) : Boolean {
        // if not exist, calculate
        if (!colValidity[colIndex]) {

            val uniqueNum = hashSetOf<Char>()
            for (row in 0..8) {
                val number = _board?.get(row)?.get(colIndex)
                assert(number != null)
                if (uniqueNum.contains(number)) {
                    return false
                } else {
                    if (number != '.') {
                        uniqueNum.add(number!!)
                    }
                }
            }
            colValidity[colIndex] = true
        }
        return true
    }

    private fun _validSubBoxes(rowIndex: Int, colIndex: Int) : Boolean {
        // sub-box[0] (col 0-2, row 0-2)
        // sub-box[1] (col 3-5, row 0-2)
        // sub-box[2] (col 6-9, row 0-2)
        // sub-box[3] (col 0-2, row 3-5)
        // ..
        // Here is the formula to get the index from [row, col]
        var index = 3 * (rowIndex / 3) + (colIndex / 3)

        if (!subBoxValidity[index]) {

            val uniqueNum = hashSetOf<Char>()
            val rowStartIndex = (rowIndex / 3) * 3 // if rowIndex = (3, 4, 5), the start index is 3
            val colStartIndex = (colIndex / 3) * 3
            for (row in rowStartIndex..rowStartIndex+2) {
                for (col in colStartIndex .. colStartIndex+2) {
                    val number = _board?.get(row)?.get(col)
                    assert(number != null)
                    if (uniqueNum.contains(number)) {
                        return false
                    } else {
                        if (number != '.') {
                            uniqueNum.add(number!!)
                        }
                    }
                }
            }

            subBoxValidity[index] = true
        }
        return true
    }
}

fun main() {
    var input = arrayOf(
        charArrayOf('5','3','.','.','7','.','.','.','.'),
        charArrayOf('6','.','.','1','9','5','.','.','.'),
        charArrayOf('.','9','8','.','.','.','.','6','.'),
        charArrayOf('8','.','.','.','6','.','.','.','3'),
        charArrayOf('4','.','.','8','.','3','.','.','1'),
        charArrayOf('7','.','.','.','2','.','.','.','6'),
        charArrayOf('.','6','.','.','.','.','2','8','.'),
        charArrayOf('.','.','.','4','1','9','.','.','5'),
        charArrayOf('.','.','.','.','8','.','.','7','9')
    )

    val sol = ValidSudoku()
    println(sol.isValidSudoku(input))
}