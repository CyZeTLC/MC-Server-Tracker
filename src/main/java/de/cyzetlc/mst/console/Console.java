package de.cyzetlc.mst.console;

import de.cyzetlc.mst.MSTracker;
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
                    //reader.printAbove("§7[INFO]§r Log #" + (++i));
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        logger.setDaemon(true);
        logger.start();

        while (true) {
            String line = reader.readLine("$ ");
            if (line.equalsIgnoreCase("stop")) {
                break;
            }

            if (line.startsWith("pull")) {
                if (line.split(" ").length == 2) {
                    try {
                        MSTracker.getInstance().trackServer(line.split(" ")[1]);
                    } catch (Exception e) {
                        reader.printAbove("Error");
                    }
                }
            }
        }

        terminal.close();
    }
}
