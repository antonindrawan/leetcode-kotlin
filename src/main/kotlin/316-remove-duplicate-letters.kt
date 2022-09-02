import java.util.Stack

class Solution316 {
    fun removeDuplicateLetters(s: String): String {
        // cbacdcbc ==> acdb
        // cbcdcbca ==> bdca
        // cbcdcba ==> bcda
        // cbdccba  ==> bcda
        /*
        Input: s = "bcabc"
        Output: "abc"

        [0] b. push b
        [1] c. push c => b c
        [2] a. push a, remove c and remove b => a
        [3] b. push b
        [4] c. push c


        // Input: cbdccba
        // Output: bcda

        [0] c. push c
        [1] b. push b, remove c (because it still exists in the backlog). => b
        [2] d. push d => b d
        [3] c. push c => b d c (because d doesn't exist in the pipeline)
        [4] c. ignore, because c is visited
        [5] b. ignore, because b is visited
        [6] a. push a  => bdca (because there is c in the pipeline)
         */

        val stack = Stack<Char>()

        val charVisited = BooleanArray(26) { false }

        val lastIndex = mutableMapOf<Char, Int>()
        s.forEachIndexed { index, c ->
            lastIndex[c] = index
        }

        s.forEachIndexed { index, c ->
            val charIndex = c - 'a'
            if (!charVisited[charIndex]) {
                // remove lower characters if they exist in the backlog

                var topItem = stack.lastOrNull()
                while (topItem != null) {
                    if (c < topItem && index < lastIndex[topItem]!!) {//exists in the backlog)

                        var removedChar = stack.pop()
                        println("Removing $removedChar from stack")
                        charVisited[topItem - 'a'] = false
                        topItem = stack.lastOrNull()
                    } else {
                        break
                    }
                }

                // push to stack

                stack.add(c)
                println("Adding $c to stack. Current stack: ${stack.toArray().toList()}")

                // mark visited
                charVisited[charIndex] = true
            }
        }
        return stack.toArray().joinToString("")
    }
}

fun main() {
    val sol = Solution316()
    assert(sol.removeDuplicateLetters("bcabc") == "abc")
    println()
    assert(sol.removeDuplicateLetters("cbacdcbc") == "acdb")
}