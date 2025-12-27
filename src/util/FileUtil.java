package util;
import model.*;
import java.io.*;

import java.util.ArrayList;
import java.util.List;

/*
 * FileUtil sınıfı:
 * Dosya üzerinden veri okuma ve yazma işlemlerini yapan yardımcı sınıftır.
 * Kullanici, kurum ve bagis verileri bu sınıf aracılığıyla dosyadan alınır
 * ve bu sınıf ile dosyaya kaydedilir.
 */
public class FileUtil {

    /*
     * Dosyadaki her satır bir kullanıcıyı temsil eder.
     * Okunan her satırdan bir Kullanici nesnesi üretilir ve listeye eklenir.
     */
    public static List<Kullanici> kullanicilariOku(String dosya) {

        // Dosyadan okunan kullanıcı nesnelerinin tutulacağı liste
        List<Kullanici> liste = new ArrayList<>();

        /*
         * BufferedReader ve FileReader:
         * Dosyayı satır satır okumak için kullanılır.
         */
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {

            String satir;

            // Dosya sonuna gelene kadar satır satır okuma yapılır.
            while ((satir = br.readLine()) != null) {

                // Boş veya sadece boşluk içeren satırlar varsa atlanır.
                if (satir.trim().isEmpty()) continue;

                // Satır virgüllerden parcalanir.
                String[] d = satir.split(",");

                // Dosyadan gelen tüm veriler String olduğu için
                // sayısal olanlar int'e çevrilir.
                int id = Integer.parseInt(d[0]);
                String ad = d[1];
                String email = d[2];
                String sifre = d[3];
                String rol = d[4];

                /*
                 * Rol bilgisine göre hangi alt sınıfın oluşturulacağına karar verilir.
                 */
                if (rol.equals("ADMIN")) {
                    liste.add(new Admin(id, ad, email, sifre));
                } else {
                    liste.add(new Bagisci(id, ad, email, sifre));
                }
            }

        } catch (IOException e) {}

        return liste;
    }

    public static void kullaniciEkle(String dosya, Kullanici k) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dosya, true))) {

            // Kullanici nesnesi dosyaya yazılabilmesi için tekrar metin formatına çevrilir.
            bw.write(k.getId() + "," + k.getAd() + "," + k.getEmail() + "," + k.getSifre() + "," + k.getRol());

            // Her kullanıcı kaydı yeni bir satıra yazılır.
            bw.newLine();

        } catch (IOException e) {}
    }

    public static int sonIdyiBul(String dosya) {

        int max = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {

            String satir;
            while ((satir = br.readLine()) != null) {

                // Boş satırlar dikkate alınmaz.
                if (satir.trim().isEmpty()) continue;

                // Satırdaki ilk değer ID olduğu için sadece ilk parça alınır.
                int id = Integer.parseInt(satir.split(",")[0]);

                // Okunan ID, mevcut maksimumdan büyükse güncellenir.
                if (id > max) max = id;
            }

        } catch (IOException e) {}

        return max;
    }

    /*
     * Kurum bilgilerini dosyadan okur.
     * Her satır bir kurumu temsil eder.
     */
    public static List<Kurum> kurumlariOku(String dosya) {

        // Okunan kurum nesnelerinin tutulacağı liste
        List<Kurum> liste = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {

            String satir;
            while ((satir = br.readLine()) != null) {

                // Boş satırlar atlanır.
                if (satir.trim().isEmpty()) continue;

                // Satır id ve kurum adı olacak şekilde ayrılır.
                String[] d = satir.split(",");

                // Kurum nesnesi oluşturulup listeye eklenir.
                liste.add(new Kurum(Integer.parseInt(d[0]), d[1]));
            }

        } catch (IOException e) {}

        return liste;
    }

    public static void kurumEkle(String dosya, Kurum k) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dosya, true))) {

            // Kurum nesnesi dosya formatına çevrilerek yazılır.
            bw.write(k.getId() + "," + k.getAd());
            bw.newLine();

        } catch (IOException e) {}
    }

    public static int kurumSonId(String dosya) {
        return sonIdyiBul(dosya);
    }

    /*
     * Bağış bilgilerini dosyadan okur.
     * Her satır bir bağışı temsil eder.
     */
    public static List<Bagis> bagislariOku(String dosya) {

        // Okunan bağışların tutulacağı liste
        List<Bagis> liste = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {

            String satir;
            while ((satir = br.readLine()) != null) {

                if (satir.trim().isEmpty()) continue;
                String[] d = satir.split(",");

                /*
                 * Bağış nesnesi oluşturulurken:
                 * - ID, bağışçı ID ve kurum ID sayıya çevrilir.
                 * - Bağış türü enum olduğu için valueOf kullanılır.
                 */
                liste.add(new Bagis(
                        Integer.parseInt(d[0]),
                        Integer.parseInt(d[1]),
                        Integer.parseInt(d[2]),
                        BagisTuru.valueOf(d[3]),
                        Double.parseDouble(d[4]),
                        d[5]
                ));
            }

        } catch (IOException e) {}

        return liste;
    }

    public static void bagisEkle(String dosya, Bagis b) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dosya, true))) {

            // Bağış nesnesi dosya formatına dönüştürülerek yazılır.
            bw.write(
                    b.getId() + "," +
                            b.getBagisciId() + "," +
                            b.getKurumId() + "," +
                            b.getTur() + "," +
                            b.getMiktar() + "," +
                            b.getAciklama()
            );
            bw.newLine();

        } catch (IOException e) {}
    }
}
