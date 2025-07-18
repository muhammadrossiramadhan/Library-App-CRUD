package CRUD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Operasi {
        

    public static void Tampilkan_data () throws IOException{

        FileReader Fileinput;
        BufferedReader bufferinput;

        try {
            Fileinput = new FileReader("src/CRUD/Database.txt");
            bufferinput = new BufferedReader(Fileinput);
        }catch (Exception e){
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            Tambah_data();
            Tampilkan_data();
            return;
        }

        String data = bufferinput.readLine();
        int noData = 0;
        String uny = "    ";
    
        System.out.print("\n| No |\tTahun |\t        Penulis\t         | Penerbit   |           Judul Buku              |\n");
        System.out.println("===========================================================================================");
        
        while(data != null){
        
            StringTokenizer stringtokenizer = new StringTokenizer(data,",");
            
            if (stringtokenizer.hasMoreTokens()) {
                stringtokenizer.nextToken();
                
            noData++;
            System.out.printf("|%2d  ",noData);
            System.out.printf("|\t%4s  ", stringtokenizer.nextToken());
            System.out.printf("|\t   %-20s",stringtokenizer.nextToken());
            System.out.printf("  | %-10s",stringtokenizer.nextToken());
            System.out.printf(" | %s",stringtokenizer.nextToken());
            System.out.printf("\t\t%10s",uny);
            System.out.printf("|%s ",uny);

            System.out.print("\n");
        }
        
        
        
       
        

        data = bufferinput.readLine();

        
        }

        Fileinput.close();
        bufferinput.close();


        System.out.println("===========================================================================================");
    }

    public static boolean Cari_data () throws IOException{
        
        // Membaca database ada atau tidak

        try {
            File file = new File ("src/CRUD/Database.txt");
        }catch (Exception e){
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            Tampilkan_data();
        }

        // Kita ambil keyword dari user

        Scanner TerminalInput = new Scanner(System.in);
        System.out.print("Masukkan kata kunci untuk mencari buku : ");
        String CariString = TerminalInput.nextLine();
        
        String[] Keywords = CariString.split("\\s+");
        
        // kita cek keyword di database ( Pakai fungsi atau method sendiri saja )

        boolean bukuDitemukan = Utility.Cekbuku(Keywords, true);

        return bukuDitemukan;

    } 

public static void Tambah_data() throws IOException {

    FileWriter FileOutput = new FileWriter("src/CRUD/Database.txt", true);
    BufferedWriter BufferOutput = new BufferedWriter(FileOutput);

    // mengambil input dari user
    Scanner terminalInput = new Scanner(System.in);
    String penulis, judul, penerbit, tahun;

    System.out.print("\n");

    // Input penulis
    System.out.print("Masukan nama penulis (atau ketik '0' untuk kembali ke menu utama): ");
    penulis = terminalInput.nextLine();
    if (penulis.equals("0")) {
        System.out.println("\nKembali ke menu utama...\n");
        return; // Exit the method and return to the main menu
    }
    System.out.println(" ");

    // Input judul
    System.out.print("Masukan judul buku (atau ketik '0' untuk kembali ke menu utama): ");
    judul = terminalInput.nextLine();
    if (judul.equals("0")) {
        System.out.println("\nKembali ke menu utama...\n");
        return;
    }
    System.out.println(" ");

    // Input penerbit
    System.out.print("Masukan nama penerbit (atau ketik '0' untuk kembali ke menu utama): ");
    penerbit = terminalInput.nextLine();
    if (penerbit.equals("0")) {
        System.out.println("\nKembali ke menu utama...\n");
        return;
    }
    System.out.println(" ");

    // Input tahun
    System.out.print("Masukan tahun terbit (atau ketik '0' untuk kembali ke menu utama): ");
    tahun = Utility.ambiltahun();
    if (tahun.equals("0")) {
        System.out.println("\nKembali ke menu utama...\n");
        return;
    }

    // Cek buku di database
    String[] keywords = {tahun + "," + penulis + "," + penerbit + "," + judul};

    // kita cek keyword di database (Pakai fungsi atau method sendiri saja)
    boolean IsExist = Utility.Cekbuku(keywords, false);

    // Menulis buku di database
    if (!IsExist) {
        System.out.print("\nJumlah entry untuk tahun dan nama penulis: ");
        System.out.println(Utility.ambilEntrypertahun(penulis, tahun));
        long nomorentry = Utility.ambilEntrypertahun(penulis, tahun) + 1;

        String Penulis_tanpa_spasi = penulis.replaceAll("\\s+", "");
        String primary_key = Penulis_tanpa_spasi + "_" + tahun + "_" + nomorentry;
        System.out.println("\nData yang akan anda masukan adalah");
        System.out.println("----------------------------------------");
        System.out.println("Primary key  : " + primary_key);
        System.out.println("Tahun terbit : " + tahun);
        System.out.println("Penulis      : " + penulis);
        System.out.println("Judul        : " + judul);
        System.out.println("Penerbit     : " + penerbit);

        boolean Istambah = Utility.getYESorNO("Apakah anda ingin menambahkan data ini? ");

        if (Istambah) {
            BufferOutput.write(primary_key + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
            BufferOutput.newLine();
            BufferOutput.flush();
        }

    } else {
        System.out.println("Buku yang anda masukkan sudah ada 😨");
        Utility.Cekbuku(keywords, true);
    }

    BufferOutput.close();
    FileOutput.close();
}
    
public static void Delete_data() throws IOException {
    File database = new File("src/CRUD/Database.txt");
    File tempDatabase = new File("src/CRUD/TempDatabase.txt");

    BufferedReader bufferInput = new BufferedReader(new FileReader(database));
    BufferedWriter bufferOutput = new BufferedWriter(new FileWriter(tempDatabase));

    // Display the list of books
    System.out.println("\nHapus data buku\n");
    System.out.println("List Buku\n");
    System.out.print("\n| No |\tTahun |\t        Penulis\t         | Penerbit   |           Judul Buku              |\n");
    System.out.println("===========================================================================================");

    String data;
    int nomor = 0;

    // Read and display all records
    while ((data = bufferInput.readLine()) != null) {
        nomor++;
        StringTokenizer stringTokenizer = new StringTokenizer(data, ",");

        if (stringTokenizer.hasMoreTokens()) {
            stringTokenizer.nextToken(); // Skip the primary key

            System.out.printf("|%2d  ", nomor);
            System.out.printf("|\t%4s  ", stringTokenizer.nextToken()); // Tahun
            System.out.printf("|\t   %-20s", stringTokenizer.nextToken()); // Penulis
            System.out.printf("  | %-10s", stringTokenizer.nextToken()); // Penerbit
            System.out.printf(" | %s", stringTokenizer.nextToken()); // Judul Buku
            System.out.print("\n");
        }
    }

    System.out.println("===========================================================================================");

    // Ask the user which record to delete
    Scanner terminalInput = new Scanner(System.in);
    System.out.print("\nMasukan nomor buku yang akan dihapus 😎: ");
    int hapusNomor = terminalInput.nextInt();

    // Reset the reader to start from the beginning
    bufferInput.close();
    bufferInput = new BufferedReader(new FileReader(database));

    // Write all records except the one to be deleted to the temporary file
    int currentNomor = 0;
    boolean isDeleted = false;

    while ((data = bufferInput.readLine()) != null) {
        currentNomor++;

        if (currentNomor == hapusNomor) {
            // Display the record to be deleted
            StringTokenizer stringTokenizer = new StringTokenizer(data, ",");
            System.out.println("\nData yang ingin anda hapus adalah:");
            System.out.println("-----------------------------------");
            System.out.println("Referensi           : " + stringTokenizer.nextToken());
            System.out.println("Tahun               : " + stringTokenizer.nextToken());
            System.out.println("Penulis             : " + stringTokenizer.nextToken());
            System.out.println("Penerbit            : " + stringTokenizer.nextToken());
            System.out.println("Judul Buku          : " + stringTokenizer.nextToken());

            System.out.print("\nApakah kamu ingin menghapus ini 🥰 (atau ketik 0 untuk kembali ke menu utama):  [y/n] ");
            String confirm = terminalInput.next();

            if (confirm.equalsIgnoreCase("y")) {
                isDeleted = true;
                System.out.println("\nData berhasil dihapus");
                continue; // Skip writing this record to the temp file
            } else {
                System.out.println("\nPenghapusan dibatalkan");
                bufferOutput.write(data);
                bufferOutput.newLine();
                bufferOutput.flush();
                continue;
            }
        }

        // Write the record to the temporary file
        bufferOutput.write(data);
        bufferOutput.newLine();
    }

    // Close all resources
    bufferOutput.close();
    bufferInput.close();

    // Replace the original database file with the temporary file
    if (isDeleted) {
        if (database.delete()) {
            if (tempDatabase.renameTo(database)) {
                System.out.println("\nDatabase berhasil diperbarui.");
            } else {
                System.err.println("\nGagal mengganti nama file sementara ke database.");
            }
        } else {
            System.err.println("\nGagal menghapus file database lama.");
        }
    } else {
        tempDatabase.delete();
        System.out.println("\nTidak ada data yang dihapus.");
    }
}

    public static void Update_data() throws IOException{
        
        // Kita ambil database original
        File database = new File("src/CRUD/Database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedinput = new BufferedReader(fileInput);

        // kita buat database sementara
        File tempDB = new File("src/CRUD/TempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // Tampilkan Data
        System.out.println("List Buku");
        Tampilkan_data();

        // Ambil user input / pilihan data
        Scanner terminalinput = new Scanner(System.in);
        System.out.print("Masukkan Data buku yang ingin kamu rubah 😍 (atau ketik 0 untuk kembali ke menu utama): ");
        int Updatedata = terminalinput.nextInt();

        if (Updatedata == 0) {
            System.out.println("\nKembali ke menu utama...\n");
        }

        // Tampilkan data yang ingin di update

        String data = bufferedinput.readLine();
        int entrycount = 0;

        while ( data != null ){

            entrycount++;
            
            StringTokenizer st = new StringTokenizer(data,",");

            // Tampilkan si data jika sama dengan input user

            if ( Updatedata == entrycount ){

            System.out.println("Data yang ingin dirubah adalah :");
            System.out.println("================================");
            System.out.println("Referensi       : " + st.nextToken());
            System.out.println("Tahun           : " + st.nextToken());
            System.out.println("Penulis         : " + st.nextToken());
            System.out.println("Penerbit        : " + st.nextToken());
            System.out.println("Judul           : " + st.nextToken());

            // update data

            // mengambil input dari user    
            
            String[] fielddata = {"Tahun","Penulis","Penerbit","Judul"};
            String[] TempData = new String[4];

            // Refresh token juga, jangan lupa ya
            st = new StringTokenizer(data,",");
            String originalData = st.nextToken();
            
            for( int i = 0; i < fielddata.length; i++ ){
                boolean isUpdate = Utility.getYESorNO("Apakah anda ingin merubah " + fielddata[i]);
                originalData = st.nextToken();

                if ( isUpdate ){
                    // User Input
                    
                    if(fielddata[i].equalsIgnoreCase("Tahun")){
                        System.out.print("masukan tahun terbit, format=(YYYY): ");
                        TempData[i] = Utility.ambiltahun();
                    }else{
                        terminalinput = new Scanner(System.in);
                        System.out.print("\nMasukan " + fielddata[i] + " baru: ");
                        TempData[i] = terminalinput.nextLine();
                    }
                                                           
                }else{
                    TempData[i] = originalData;
                }
            }

            // Tampilkan data baru ke layar
            // Refresh token lagi
            st = new StringTokenizer(data,",");
            st.nextToken();
            System.out.println("---------------------------------------");
            System.out.println("Tahun               : " + st.nextToken() + " --> " + TempData[0]);
            System.out.println("Penulis             : " + st.nextToken() + " --> " + TempData[1]);
            System.out.println("Penerbit            : " + st.nextToken() + " --> " + TempData[2]);
            System.out.println("Judul               : " + st.nextToken() + " --> " + TempData[3]);

            Boolean IsUpdate = Utility.getYESorNO("Apakah anda yakin ingin mengupdate ini 😘 ?");

            if (IsUpdate){

                // cek data baru di database
                boolean isExist = Utility.Cekbuku(TempData,false);

                if(isExist){
                    System.err.println("data buku sudah ada di database, proses update dibatalkan, \nsilahkan delete data yang bersangkutan");
                    // copy data
                    bufferedOutput.write(data);

                }else{
                    
                    // format data baru kedalam database
                    String tahun = TempData[0];
                    String penulis = TempData[1];
                    String penerbit = TempData[2];
                    String judul = TempData[3];
                    
                    // kita bikin primary key
                    long nomorentry = Utility.ambilEntrypertahun(penulis,tahun) + 1;
        
                    String Penulis_tanpa_spasi = penulis.replaceAll("\\s+", "");
                    String primary_key = Penulis_tanpa_spasi+"_"+tahun+"_"+nomorentry;
                    
                    // tulis data ke database
                    bufferedOutput.write(primary_key + "," + tahun + ","+ penulis +"," + penerbit + ","+judul);

                }

            }else{
                // copy data
                bufferedOutput.write(data);
            }
        
            
        }else{
            // copy data
            bufferedOutput.write(data);
        }
        
        bufferedOutput.newLine();
        
        data = bufferedinput.readLine();

    }
        
        // Menulis data ke file
        bufferedOutput.flush();
        fileOutput.close();
        bufferedinput.close();
        fileInput.close();

        System.gc();
        // Delete original file
        Files.delete(Paths.get("src/CRUD/Database.txt"));
        
        // rename file sementara ke database
        Files.move(Paths.get("src/CRUD/TempDB.txt"), Paths.get("src/CRUD/Database.txt"), StandardCopyOption.REPLACE_EXISTING);    

}


}