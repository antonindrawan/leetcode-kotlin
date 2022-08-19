class SudokuSolver {
    fun solveSudoku(board: Array<CharArray>): Unit {
        // Iterate through each cell.
        // GIVEN the cell is empty (.)
        // ASSIGN values 1..9
        // CHECK if there is no duplicate
        // No -> assign the value
        //    -> no longer empty cells? stops!
        // go to next cell (recursive)

        val emptyCell = getNextEmptyCell(board, 0, -1)
        if (emptyCell != null) {
            val result = solve(board, emptyCell.first, emptyCell.second)
            assert(result)

            // print the solution
            // board.forEach {
            //     println(it)
            // }
        }
    }

    fun getNextEmptyCell(board: Array<CharArray>, row: Int, col: Int): Pair<Int, Int>? {

        var nextCell = Pair<Int, Int>(row, col)
        do {
            if (nextCell.second < 8) {
                // go to the next column
                nextCell = Pair(nextCell.first, nextCell.second + 1)
            } else {
                // go to the next row
                nextCell = Pair(nextCell.first + 1, 0)
            }

            if (nextCell.first < 9 && board[nextCell.first][nextCell.second] == '.') {
                return nextCell
            }
        } while (nextCell.first < 9 && nextCell.second < 9)

        return null
    }

    fun solve(board: Array<CharArray>, row: Int, col: Int): Boolean {

        for (number in '1'..'9') {
            if (!numberUsedInRow(board, number, row) && !numberUsedInCol(board, number, col) && !numberUsedInSubBox(board, number, row, col)) {
                board[row][col] = number

                val nextCell = getNextEmptyCell(board, row, col)
                if (nextCell == null) {
                    return true
                }
                if (solve(board, nextCell.first, nextCell.second)) {
                    return true
                }
                board[row][col] = '.'
            }
        }
        return false // true
    }

    fun numberUsedInRow(board: Array<CharArray>, number: Char, row: Int): Boolean {
        for (i in 0..8) {
            val cell = board[row][i]
            if (cell != '.' && cell == number) {
                return true
            }
        }
        return false
    }

    fun numberUsedInCol(board: Array<CharArray>, number: Char, col: Int): Boolean {
        for (i in 0..8) {
            val cell = board[i][col]
            if (cell != '.' && cell == number) {
                return true
            }
        }
        return false
    }

    fun numberUsedInSubBox(board: Array<CharArray>, number: Char, row: Int, col: Int): Boolean {
        val beginRow = (row / 3) * 3
        val beginCol = (col / 3) * 3

        for (i in beginRow..beginRow + 2) {
            for (j in beginCol..beginCol + 2) {
                val cell = board[i][j]
                if (cell != '.' && cell == number) {
                    return true
                }
            }
        }
        return false
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

    val sol = SudokuSolver()
    println(sol.solveSudoku(input))
}