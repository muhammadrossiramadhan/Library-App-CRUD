package CRUD;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import CRUD.Operasi;
import CRUD.Utility;


public class Program_Utama {
    
    public static void main(String[] args) throws IOException{
        
        Scanner terminalInput = new Scanner(System.in);
        String PilihanUser;
        boolean Islanjutkan = true;
        boolean KaloNo = true;

        while (Islanjutkan) {
        
        Utility.ClearScreen();
        System.out.println("Database Perpustakaan\n");
        System.out.println("1.\tLihat seluruh buku");
        System.out.println("2.\tCari data buku");
        System.out.println("3.\tTambah data buku");
        System.out.println("4.\tUbah data buku");
        System.out.println("5.\tHapus data buku");

        System.out.print("\n\nPilihan anda: ");
        PilihanUser = terminalInput.next();

        switch (PilihanUser){

            case "1":
                System.out.println("\n===== Lihat Seluruh buku ======\n");
                Operasi.Tampilkan_data();
                break;
            case "2":
                System.out.println("\nCari data buku\n");
                boolean bukuDitemukan = Operasi.Cari_data();
                if (!bukuDitemukan) {
                    System.out.println("\nBuku tidak ditemukan 😅\n");
                    boolean tambahBuku= Utility.getYESorNO("Apakah anda ingin menambahkan ini ? ");
                    if (tambahBuku) {
                        Operasi.Tambah_data();
                    }else {
                        System.out.println("Baiklah, kita tidak jadi menambahkannya");
                    }
                }else {
                 System.out.println("\nBuku ditemukan\n");
                 boolean ubahBuku = Utility.getYESorNO("Apakah anda ingin mengubah data buku ini? ");
                 if (ubahBuku) {
                    Operasi.Update_data();
                    }
                }
                break;
            case "3":
                System.out.println("\nTambah data buku\n");
                Operasi.Tambah_data();
                Operasi.Tampilkan_data();
                break;

            case "4":
                System.out.println("\nUbah data buku\n");
                Operasi.Update_data();
                break;

            case "5":
                System.out.println("\nHapus data buku\n");
                Operasi.Delete_data();
                break;

            default:
                System.err.println("Waduh kawan, pilihan anda tidak ada disini");

            }   

            System.out.println("\nKONFIRMASI\n");

            Islanjutkan = Utility.getYESorNO("Apakah anda ingin melanjutkan aplikasi ini ? ");
            

            
        if (KaloNo) {
            PilihanUser.equalsIgnoreCase("n");
            System.out.println("\nBye-bye, Terima kasih udah dateng yee 😘\n");
        }



    }


}        

        
}
