package org.digitalcrafting.nio;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileReaderExample {
    public static void main(String[] args) throws IOException {
        URL resource = NIOFileReaderExample.class.getClassLoader().getResource("org/digitalcrafting/nio/nio-data.txt");
        if (resource == null) throw new FileNotFoundException("Resource not found!");

        // Copy to a temp file
        File tempFile = File.createTempFile("nio-data", ".txt");
        tempFile.deleteOnExit();
        try (InputStream in = resource.openStream();
             OutputStream out = new FileOutputStream(tempFile)) {
            in.transferTo(out);
        }

        try (RandomAccessFile file = new RandomAccessFile(tempFile, "rw")) {
            FileChannel inChannel = file.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {
                System.out.println("Read " + bytesRead);
                buf.flip();

                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }
                System.out.println();

                buf.clear();
                bytesRead = inChannel.read(buf);
            }
        }
    }
}
