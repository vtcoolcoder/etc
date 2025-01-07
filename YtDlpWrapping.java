/*
* Данная программа должна выводить в stdout строку с нужным форматом, 
*     например: 139+397
* Однако при возникновении ошибок она должна вывести все их в stderr
* и вернуть командной оболочке код возврата -- 1
*/

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;


public class YtDlpWrapping {

    private enum RowIdxS {ID, EXT, RESOLUTION, PROTO, VCODEC, ACODEC}
    
    private record Row(String id,
                       String ext,
                       String resolution,
                       String proto,
                       String vcodec,
                       String acodec) {
                       
        @Override     
        public String toString() {
            return STR."""
                \{RowIdxS.ID}: \{id}
                \{RowIdxS.EXT}: \{ext}
                \{RowIdxS.RESOLUTION}: \{resolution}
                \{RowIdxS.PROTO}: \{proto}
                \{RowIdxS.VCODEC}: \{vcodec}
                \{RowIdxS.ACODEC}: \{acodec}
                """;
        }             
    }
    
    
    private static final String EXECUTABLE_COMMAND = "yt-dlp";    
    private static final String TEST_URL = "file:///storage/emulated/0/Бэкстейдж Java Bootcamp. Live-режим.mp4";                
    private static final int SUCCESS = 0;                 
   
                   
    public static void main(String[] args) throws Exception {  
        var url = 0 != args.length ? args[0] : TEST_URL;
        
        var builder = new ProcessBuilder(
                EXECUTABLE_COMMAND,
                "--enable-file-urls",
                "--list-formats",
                url   
        );
        
        var process = builder.start().onExit().get();
        
        if (SUCCESS != process.exitValue()) {
            printErrors(new Scanner(process.getErrorStream()));
        } else {
            getFormatTable(new Scanner(process.getInputStream())).forEach(System.out::println);
        }
    }
    
    
    private static List<Row> getFormatTable(Scanner scanner) {
        Objects.requireNonNull(scanner);
        
        //var result = new ArrayList<Row>();
        //var isStartProcessing = false;
        //final var splitRegexp = "[^\\d\\w]+";
           
        return scanner.useDelimiter("\n").tokens()
                .filter(line -> line.matches("^[^|\\p{Upper}]+\\|[^|]+\\|[^|\\p{Upper}]+$"))
                .map(line -> line.split("[^\\d\\w]+"))
                .map(splitted -> new Row(
                        splitted[RowIdxS.ID.ordinal()],
                        splitted[RowIdxS.EXT.ordinal()],
                        splitted[RowIdxS.RESOLUTION.ordinal()],
                        splitted[RowIdxS.PROTO.ordinal()],
                        splitted[RowIdxS.VCODEC.ordinal()],
                        splitted[RowIdxS.ACODEC.ordinal()]
                )).toList();
                
        /*
        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            var predicate = line.startsWith("-------");
            
            if (predicate) {
                isStartProcessing = true;
                continue;
            }
            
            if (isStartProcessing) {
                var data = line.split(regexp);
                     
                var currentRow = new Row(
                        data[RowIdxS.ID.ordinal()],
                        data[RowIdxS.EXT.ordinal()],
                        data[RowIdxS.RESOLUTION.ordinal()],
                        data[RowIdxS.PROTO.ordinal()],
                        data[RowIdxS.VCODEC.ordinal()],
                        data[RowIdxS.ACODEC.ordinal()]
                );
                             
                result.add(currentRow);
            }
        }
        
        return result;
        */
    }
    
    
    private static void printErrors(Scanner scanner) {
        Objects.requireNonNull(scanner);
        scanner.useDelimiter("\n").tokens().forEach(System.err::println);
    }
    
    
    private static String getContentFormat() {
        return null;
    }
}


/*
        var downloadYtDlpArgs = new String[] {
                EXECUTABLE_COMMAND,
                "--retries",
                "infinite",
                "--format",
                contentFormat,
                url
        };
*/
