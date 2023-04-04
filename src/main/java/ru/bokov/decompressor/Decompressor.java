package ru.bokov.decompressor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface Decompressor {
    InputStream decompress(File archive) throws IOException;
}
