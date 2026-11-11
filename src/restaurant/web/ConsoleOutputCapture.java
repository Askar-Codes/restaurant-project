package restaurant.web;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// The restaurant classes print their messages with System.out.println.
// This class collects those lines while one web request runs, so the web page can show them.
// The lines are still printed to the real console as well.
public class ConsoleOutputCapture {
    private final PrintStream realConsole = System.out;
    private final ByteArrayOutputStream capturedBytes = new ByteArrayOutputStream();

    public void startCapturing() {
        capturedBytes.reset();
        OutputStream consoleAndMemory = new OutputStream() {
            public void write(int oneByte) {
                realConsole.write(oneByte);
                capturedBytes.write(oneByte);
            }
        };
        System.setOut(new PrintStream(consoleAndMemory, true));
    }

    public List<String> stopCapturingAndGetPrintedLines() {
        System.out.flush();
        System.setOut(realConsole);
        String printedText = new String(capturedBytes.toByteArray(), Charset.defaultCharset());
        List<String> printedLines = new ArrayList<>();
        for (String oneLine : printedText.split("\\r?\\n")) {
            if (!oneLine.trim().isEmpty()) {
                printedLines.add(oneLine);
            }
        }
        return printedLines;
    }
}
