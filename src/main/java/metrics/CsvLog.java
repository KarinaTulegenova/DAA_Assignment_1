package metrics;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CsvLog implements Closeable, Flushable {
    private final PrintWriter out;

    public CsvLog(String path, String header) {
        try {
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path, false),
                    StandardCharsets.UTF_8), true);
            out.println(header);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    public void row(Object... cells){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cells.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(cells[i]);
        }
        out.println(sb.toString());
    }
    @Override public void flush(){ out.flush(); }
    @Override public void close(){ out.close(); }
}
