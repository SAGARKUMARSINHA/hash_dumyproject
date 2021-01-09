package com.smlnskgmail.jaman.hashchecker;

import com.smlnskgmail.jaman.hashchecker.calculator.GenerateHashFromFileTest;
import com.smlnskgmail.jaman.hashchecker.calculator.GenerateHashFromTextTest;
import com.smlnskgmail.jaman.hashchecker.calculator.jdk.crc.CRC32JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.jdk.md.MD5JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.jdk.sha.SHA1JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.jdk.sha.SHA224JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.jdk.sha.SHA256JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.jdk.sha.SHA384JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.jdk.sha.SHA512JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.jdk.zeroleads.MessageDigestZeroLeadsJdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.database.DatabaseExporterTest;
import com.smlnskgmail.jaman.hashchecker.database.HistoryHelperTest;
import com.smlnskgmail.jaman.hashchecker.feedback.FeedbackTest;
import com.smlnskgmail.jaman.hashchecker.history.HistoryTest;
import com.smlnskgmail.jaman.hashchecker.rateapp.RateAppDialogTest;
import com.smlnskgmail.jaman.hashchecker.screenrunner.ScreenRunnerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MD5JdkHashCalculatorTest.class,
        SHA1JdkHashCalculatorTest.class,
        SHA224JdkHashCalculatorTest.class,
        SHA256JdkHashCalculatorTest.class,
        SHA384JdkHashCalculatorTest.class,
        SHA512JdkHashCalculatorTest.class,
        CRC32JdkHashCalculatorTest.class,
        MessageDigestZeroLeadsJdkHashCalculatorTest.class,
        GenerateHashFromFileTest.class,
        GenerateHashFromTextTest.class,
        HistoryTest.class,
        FeedbackTest.class,
        RateAppDialogTest.class,
        DatabaseExporterTest.class,
        ScreenRunnerTest.class,
        HistoryHelperTest.class,
})
public class AndroidTestSuite {

}
