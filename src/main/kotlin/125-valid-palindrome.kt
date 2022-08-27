class Solution125 {
    val re: Regex = "[a-zA-Z0-9]".toRegex()
    private fun _isAlphanumeric(ch: Char) : Boolean {
        return re.matches(ch.toString())
    }

    fun isPalindrome(s: String): Boolean {
        var result = false
        // aa = true
        // ama = true
        // b a b = true
        // " "
        // "  "

        // Algorithm
            // left = 0, right = len-1.
            // char at left == char at right -> palindrome
            // increment left, decrement right
            // if left >= right, stop


        var left = 0
        var right = s.length - 1

        while (left < right) {
            // need to only process alphanumeric, so advance the left for non-alphanumerics
            while (left < right && !_isAlphanumeric(s[left])) {
                left += 1
            }

            while (left < right && !_isAlphanumeric(s[right])) {
                right -= 1
            }

            // leetcode uses an older version (1.3.10): use toLowerCase() i.s.o lowercaseChar() when submitting to leetcode
            if (s[left].lowercaseChar() != s[right].lowercaseChar()) {
                return false
            }
            left += 1
            right -= 1
        }
        return true
    }
}

fun main() {
    val sol = Solution125()
    println(sol.isPalindrome("A man, a plan, a canal: Panama"))
    println(sol.isPalindrome("race a car"))
    println(sol.isPalindrome(" "))
}