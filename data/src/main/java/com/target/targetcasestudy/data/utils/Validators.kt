package com.target.targetcasestudy.data.utils

/**
 * For an explanation of how to validate credit card numbers read:
 *
 * https://www.freeformatter.com/credit-card-number-generator-validator.html#fakeNumbers
 *
 * This contains a breakdown of how this algorithm should work as
 * well as a way to generate fake credit card numbers for testing
 *
 * The structure and signature of this is open to modification, however
 * it *must* include a method, field, etc that returns a [Boolean]
 * indicating if the input is valid or not
 *
 * Additional notes:
 *  * This method does not need to validate the credit card issuer
 *  * This method must validate card number length (13 - 19 digits), but does not
 *    need to validate the length based on the issuer.
 *
 * @param creditCardNumber - credit card number of (13, 19) digits
 * @return true if a credit card number is believed to be valid,
 * otherwise false
 */

fun validateCreditCard(creditCardNumber: String): Boolean {
    if (creditCardNumber.isNullOrEmpty() || creditCardNumber.length < 13 || creditCardNumber.length > 19) {
        return false
    }
    var cardNumber = creditCardNumber
    var lastDigit = cardNumber.substring(cardNumber.length - 1).toInt() // get the last digit
    cardNumber =
        cardNumber.substring(0, cardNumber.length - 1); // drop the last digit from given number
    cardNumber = reverseString(cardNumber) // reverse the number
    var sum = 0
    for (index in cardNumber.indices) {
        var num = cardNumber[index].toInt()
        if (index % 2 == 0) { // check if num position is odd
            num *= 2 // multiply by 2 if i is in odd position
            if (num > 9) { // check if num is greater than 9
                num -= 9 // substract 9 from num
            }
        }
        sum += num // add num value to sum
    }
    val isValidCard =
        (sum + lastDigit) % 10 == 0; // if digit (the last number of the card) is the amount that you would need to add to get a multiple of 10 (Modulo 10)
    return isValidCard;
}

fun reverseString(input: String): String {
    val stringBuilder = StringBuilder(input)
    return stringBuilder.reverse().toString()
}