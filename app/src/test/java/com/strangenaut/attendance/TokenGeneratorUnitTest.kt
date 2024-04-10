package com.strangenaut.attendance

import com.strangenaut.attendance.home.domain.util.TokenGenerator
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TokenGeneratorUnitTest {

    private lateinit var token1: String
    private lateinit var token2: String

    @Before
    fun init() {
        token1 = TokenGenerator.generateToken()
        token2 = TokenGenerator.generateToken()
    }

    @Test
    fun testTokensAreNotSame() {
        assertNotEquals(token1, token2)
    }

    @Test
    fun testTokenHasCapitalAndLowercaseLetters() {
        var containsLowercase = false
        var containsCapitals = false

        for(char in token1) {
            if (char.isUpperCase()) {
                containsCapitals = true
            } else {
                containsLowercase = true
            }

            if (containsCapitals && containsLowercase) {
                break
            }
        }

        assertTrue(containsCapitals)
        assertTrue(containsLowercase)
    }

    @Test
    fun testTokenHasNumbers() {
        var containsNumbers = false

        for(char in token1) {
            if (char.isDigit()) {
                containsNumbers = true
                break
            }
        }

        assertTrue(containsNumbers)
    }

    @Test
    fun testTokenHasSpecialCharacters() {
        var containsSpecialCharacters = false
        val specialCharacters = setOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')')

        for(char in token1) {
            if (specialCharacters.contains(char)) {
                containsSpecialCharacters = true
                break
            }
        }

        assertTrue(containsSpecialCharacters)
    }
}