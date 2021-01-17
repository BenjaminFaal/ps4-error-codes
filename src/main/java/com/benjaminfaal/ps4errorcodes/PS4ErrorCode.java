package com.benjaminfaal.ps4errorcodes;

import lombok.Data;
import lombok.NonNull;

@Data
public class PS4ErrorCode {

    private final String name;

    @NonNull
    private final String code;

    @NonNull
    private final String returnCode;

    private final String description;

}
