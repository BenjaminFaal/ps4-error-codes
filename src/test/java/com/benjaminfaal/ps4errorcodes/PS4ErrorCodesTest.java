package com.benjaminfaal.ps4errorcodes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PS4ErrorCodesTest {

    @Test
    public void testFindMethods() {
        PS4ErrorCode errorCode = PS4ErrorCodes.findByName("SCE_KERNEL_ERROR_E2BIG");
        assertEquals(PS4ErrorCodes.findByCode("CE-30007-0"), errorCode);
        assertEquals(PS4ErrorCodes.findByReturnCode("0x80020007"), errorCode);
        assertEquals(PS4ErrorCodes.findBySigned(-2147352569), errorCode);
        assertEquals(PS4ErrorCodes.findByUnsigned(2147614727L), errorCode);
    }

}