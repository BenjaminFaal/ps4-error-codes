# ps4-error-codes
PS4 Error Codes

Sources:
- https://github.com/xXxTheDarkprogramerxXx/PS4_Error_Code_Viewer
- https://www.psdevwiki.com/ps4/Error_Codes
- https://www.psdevwiki.com/ps4/Talk:Error_Codes

[![Release](https://jitpack.io/v/BenjaminFaal/ps4-error-codes.svg)](https://jitpack.io/#BenjaminFaal/ps4-error-codes)
## Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
	<groupId>com.github.BenjaminFaal</groupId>
	<artifactId>ps4-error-codes</artifactId>
	<version>0.0.1</version>
</dependency>
```

## Usage
```java
import com.benjaminfaal.ps4errorcodes.PS4ErrorCodes;

public class PS4ErrorCodesTest {

    public static void main(String[] args) {
        PS4ErrorCodes.findByName("SCE_KERNEL_ERROR_E2BIG");
        PS4ErrorCodes.findByCode("CE-30007-0");
        PS4ErrorCodes.findByReturnCode("0x80020007");
        PS4ErrorCodes.findBySigned(-2147352569);
        PS4ErrorCodes.findByUnsigned(2147614727L);
    }

}
```