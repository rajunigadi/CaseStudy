package com.target.targetcasestudy

import com.target.targetcasestudy.data.utils.validateCreditCard
import org.junit.Assert
import org.junit.Test

/**
 * Feel free to make modifications to these unit tests! Remember, you have full technical control
 * over the project, so you can use any libraries and testing strategies that see fit.
 */
class CreditCardValidatorTest {

    @Test
    fun isCreditCardNumberValid() {
        Assert.assertTrue(
            "valid credit card number should yield true",
            validateCreditCard("4716763950111815")
        )
    }

    @Test
    fun isCreditCardNumberInValid() {
        Assert.assertFalse(
            "invalid credit card number should yield false",
            validateCreditCard("45399767415120431587")
        )
    }
}
