package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utility {
    

    static long ambilEntrypertahun(String penulis, String tahun) throws IOException{
        
        FileReader fileInput = new FileReader("src/CRUD/Database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        long entry = 0;
        String data = bufferInput.readLine();

        while (data != null) {
            
            Scanner dataScanner = new Scanner(data);
            dataScanner.useDelimiter(",");

                if (dataScanner.hasNext()) {  
                    String primaryKey = dataScanner.next();
                    Scanner primaryKeyScanner = new Scanner(primaryKey);
                    primaryKeyScanner.useDelimiter("_");
        
                    penulis = penulis.replaceAll("\\s+", "");
        
                    if (penulis.equalsIgnoreCase(primaryKeyScanner.next()) && tahun.equalsIgnoreCase(primaryKeyScanner.next())) {
                        
                        entry = primaryKeyScanner.nextInt();
                    }
            }
    
            data = bufferInput.readLine();
        }

        fileInput.close();
        bufferInput.close();
    
        return entry;
    }
    
protected static boolean Cekbuku(String[] Keywords, boolean IsDisplay) throws IOException {
    FileReader Fileinput = new FileReader("src/CRUD/Database.txt");
    BufferedReader bufferinput = new BufferedReader(Fileinput);

    String data = bufferinput.readLine();
    boolean IsExist = false;
    int jumlahdata = 0;

    if (IsDisplay) {
        System.out.printf("\n| %-3s | %-20s | %-20s | %-15s | %-30s |\n", "No", "Primary Key", "Tahun", "Penulis", "Penerbit", "Judul Buku");
        System.out.println("===========================================================================================");
    }

    while (data != null) {
        boolean match = true;

        for (String Keyword : Keywords) {
            String normalizedKeyword = Keyword.replaceAll("\\s+", "").toLowerCase();
            match = match && data.toLowerCase().contains(normalizedKeyword);
        }

        if (match) {
            IsExist = true;
            if (IsDisplay) {
                jumlahdata++;
                StringTokenizer stringtokenizer = new StringTokenizer(data, ",");

                System.out.printf("| %-3d | %-20s | %-20s | %-15s | %-30s |\n",
                        jumlahdata,
                        stringtokenizer.nextToken(), // Primary Key
                        stringtokenizer.nextToken(), // Tahun
                        stringtokenizer.nextToken(), // Penulis
                        stringtokenizer.nextToken(), // Penerbit
                        stringtokenizer.nextToken()  // Judul Buku
                );
            }
        }

        data = bufferinput.readLine();
    }

    bufferinput.close();
    return IsExist;
}

    static String ambiltahun () throws IOException{
    
    boolean TahunValid = false;
    Scanner terminalInput = new Scanner(System.in);
    String TahunInput = terminalInput.nextLine();

    while ( !TahunValid ){
            try{

                Year.parse(TahunInput);
                TahunValid = true;

            }catch ( Exception e ){
                
                System.out.println(" ");
                System.out.println("Format tahunnya tidak valid kawan, harusnya (YYYY)\n");
                System.err.print("masukan tahun terbit lagi yakk 🥰 : ");
                TahunValid = false;
                TahunInput = terminalInput.nextLine();

            }

        }
    
        return TahunInput;
    }

    public static boolean getYESorNO ( String Pesan ){

        Scanner TerminalInput = new Scanner (System.in);
        System.out.print("\n" + Pesan + " [y/n] " );
        String PilihanUser = TerminalInput.next();


        while (!PilihanUser.equalsIgnoreCase("y") && !PilihanUser.equalsIgnoreCase("n")){
            System.err.println("Pilhan anda sangat tidak manuk akal, pakai y atau n koh");
            System.out.print("\n" + Pesan + " [y/n] : " );
            PilihanUser = TerminalInput.next();
        }

        return PilihanUser.equalsIgnoreCase("y");
    }

    public static void ClearScreen () throws IOException{
        try {
            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception ex){
            System.err.println("tidak bisa clear screen");
        }

    }

}
