package ru.bokov.decompressor;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.*;

public class BZip2Decompressor implements Decompressor{
    @Override
    public InputStream decompress(File archive) throws IOException {
        return new BZip2CompressorInputStream(new FileInputStream(archive));
    }
}
