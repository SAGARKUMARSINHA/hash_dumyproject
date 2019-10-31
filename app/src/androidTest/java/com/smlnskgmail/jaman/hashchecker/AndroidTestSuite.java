package com.smlnskgmail.jaman.hashchecker;

import com.smlnskgmail.jaman.hashchecker.calculator.ui.GenerateHashFromTextTest;
import com.smlnskgmail.jaman.hashchecker.calculator.values.crc.CRC32HashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.values.md.MD5HashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.values.sha.SHA1HashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.values.sha.SHA224HashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.values.sha.SHA256HashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.values.sha.SHA384HashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.values.sha.SHA512HashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.calculator.values.zeroleads.MessageDigestZeroLeadsHashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.screenrunner.ScreenRunnerTest;
import com.smlnskgmail.jaman.hashchecker.tools.TextToolsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MD5HashCalculatorTest.class,
        SHA1HashCalculatorTest.class,
        SHA224HashCalculatorTest.class,
        SHA256HashCalculatorTest.class,
        SHA384HashCalculatorTest.class,
        SHA512HashCalculatorTest.class,
        CRC32HashCalculatorTest.class,
        MessageDigestZeroLeadsHashCalculatorTest.class,
        ScreenRunnerTest.class,
        TextToolsTest.class,
        GenerateHashFromTextTest.class
})
public class AndroidTestSuite {}
