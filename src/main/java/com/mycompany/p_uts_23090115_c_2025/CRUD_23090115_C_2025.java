/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p_uts_23090115_c_2025;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.Scanner;

/**
 *
 * @author Lenovo
 */
public class CRUD_23090115_C_2025 {

    public static void main(String[] args) {
        // URL MongoDB lokal
        String URL = "mongodb://localhost:27017";

        // Scanner untuk input dari user
        Scanner scanner = new Scanner(System.in);

        try (MongoClient mongoClient = MongoClients.create(URL)) {
            // Koneksi ke database dan koleksi
            MongoDatabase database = mongoClient.getDatabase("uts_23090115_C_2025");
            MongoCollection<Document> collection = database.getCollection("coll_23090115_C_2025");

            // Menu utama
            while (true) {
                System.out.println("\n=== MENU ===");
                System.out.println("1. Tambah Data");
                System.out.println("2. Lihat Semua Data");
                System.out.println("3. Update Data");
                System.out.println("4. Hapus Data");
                System.out.println("5. Cari Data");
                System.out.println("0. Keluar");
                System.out.print("Pilih menu: ");
                int pilihan = scanner.nextInt();
                scanner.nextLine(); // membersihkan enter

                if (pilihan == 1) {
                    // CREATE
                    System.out.print("Masukkan nama produk: ");
                    String nama = scanner.nextLine();

                    System.out.print("Masukkan harga produk: ");
                    int harga = scanner.nextInt();
                    scanner.nextLine(); // bersihkan buffer setelah nextInt

                    // doc 1 - Nama dan harga produk
                    // Data yang dimasukkan adalah "nama" dan "harga"
                    Document newDoc = new Document("nama", nama) // # doc 1: "nama": "Lenovo ThinkPad"
                            .append("harga", harga); // # doc 1: "harga": 8000000

                    // doc 2 - Spesifikasi produk (subdokumen)
                    // Menambahkan field spesifikasi dalam subdokumen
                    System.out.print("Masukkan processor: ");
                    String processor = scanner.nextLine();

                    System.out.print("Masukkan RAM: ");
                    String ram = scanner.nextLine();

                    System.out.print("Masukkan GPU: ");
                    String gpu = scanner.nextLine();

                    // doc 2: Menambahkan subdokumen spesifikasi
                    newDoc.append("spesifikasi", new Document("processor", processor) // # doc 2: "processor": "Intel i7"
                            .append("RAM", ram) // # doc 2: "RAM": "16GB"
                            .append("GPU", gpu)); // # doc 2: "GPU": "RTX 4060"

                    // doc 3 - Garansi produk (subdokumen)
                    System.out.print("Masukkan lama garansi: ");
                    String garansiLama = scanner.nextLine();

                    // doc 3: Menambahkan subdokumen garansi
                    newDoc.append("garansi", new Document("lama", garansiLama)); // # doc 3: "garansi": {"lama": "1 tahun"}

                    collection.insertOne(newDoc);
                    System.out.println("Data berhasil ditambahkan.");

                } else if (pilihan == 2) {
                    // READ
                    System.out.println("\nData yang tersimpan:");
                    FindIterable<Document> allDocs = collection.find();
                    for (Document doc : allDocs) {
                        System.out.println(doc.toJson());
                    }

                } else if (pilihan == 3) {
                    // UPDATE
                    System.out.print("Masukkan nama produk yang ingin diubah: ");
                    String namaCari = scanner.nextLine();

                    System.out.print("Masukkan harga baru: ");
                    int hargaBaru = scanner.nextInt();

                    UpdateResult result = collection.updateOne(
                            Filters.eq("nama", namaCari),
                            new Document("$set", new Document("harga", hargaBaru))
                    );

                    if (result.getModifiedCount() > 0) {
                        System.out.println("Data berhasil diupdate.");
                    } else {
                        System.out.println("Data tidak ditemukan.");
                    }

                } else if (pilihan == 4) {
                    // DELETE
                    System.out.print("Masukkan nama produk yang ingin dihapus: ");
                    String namaHapus = scanner.nextLine();

                    DeleteResult deleteResult = collection.deleteOne(Filters.eq("nama", namaHapus));
                    if (deleteResult.getDeletedCount() > 0) {
                        System.out.println("Data berhasil dihapus.");
                    } else {
                        System.out.println("Data tidak ditemukan.");
                    }

                } else if (pilihan == 5) {
                    // SEARCH
                    System.out.print("Masukkan keyword pencarian: ");
                    String keyword = scanner.nextLine();

                    FindIterable<Document> searchResults = collection.find(Filters.regex("nama", keyword));
                    System.out.println("Hasil pencarian:");
                    for (Document doc : searchResults) {
                        System.out.println(doc.toJson());
                    }

                } else if (pilihan == 0) {
                    // Keluar dari program
                    System.out.println("Terima kasih. Program selesai.");
                    break;

                } else {
                    System.out.println("Pilihan tidak valid.");
                }
            }

        } catch (Exception e) {
            // Menangani error koneksi atau error lainnya
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }

        scanner.close(); // Tutup scanner
    }
    
    
}

