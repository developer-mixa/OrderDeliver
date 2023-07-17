package com.example.orderdeliver

import org.junit.Assert

inline fun <reified T : Throwable> catch(block: () -> Unit): T {
    try {
        block()
    } catch (e: Throwable) {
        if (e is T) {
            return e
        } else {
            Assert.fail("Invalid exception type. " +
                    "Expected: ${T::class.java.simpleName}, " +
                    "Actual: ${e.javaClass.simpleName}")
        }
    }
    throw AssertionError("No expected exception")
}