package com.target.targetcasestudy

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    DealListFragmentTest::class,
    DealItemFragmentTest::class
)
class InstrumentedTestSuite