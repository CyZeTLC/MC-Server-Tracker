package de.cyzetlc.mst.console;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class Console {
    public Console(String[] args) throws IOException {
        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build();

        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

        Thread logger = new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    Thread.sleep(2000);
                    reader.printAbove("§7[INFO]§r Log #" + (++i));
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        logger.setDaemon(true);
        logger.start();

        while (true) {
            String line = reader.readLine("> ");
            if (line.equalsIgnoreCase("stop")) {
                break;
            }
            reader.printAbove("Du hast eingegeben: " + line);
        }

        terminal.close();
    }
}
