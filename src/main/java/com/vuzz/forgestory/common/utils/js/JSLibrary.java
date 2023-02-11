package com.vuzz.forgestory.common.utils.js;

import java.io.File;

public class JSLibrary implements JSElement {

    public File scriptLib;

    public JSLibrary(File file) {
        scriptLib = file;
    }
}
