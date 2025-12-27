package service;

import model.*;
import util.FileUtil;

import java.util.List;

public class AuthService {

    private static final String DOSYA = "data/kullanicilar.txt";

    public static void kayitOl(String ad, String email, String sifre) {

        // Dosyadaki tüm kullanıcılar okunur.
        List<Kullanici> liste = FileUtil.kullanicilariOku(DOSYA);

        // Aynı email ile kayıtlı kullanıcı var mı diye kontrol edilir.
        for (Kullanici k : liste) {
            if (k.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Bu email sistemde zaten kayitli.");
                return;// Kayıt işlemi iptal edilir.
            }
        }
        // Yeni kullanıcı için farklı bir ID oluşturulur.
        int id = FileUtil.sonIdyiBul(DOSYA) + 1;
        FileUtil.kullaniciEkle(DOSYA, new Bagisci(id, ad, email, sifre));

        System.out.println("Kayit basarili.");
    }

    public static Kullanici girisYap(String email, String sifre) {

        // Email ve şifre eşleşmesi kontrol edilir.
        for (Kullanici k : FileUtil.kullanicilariOku(DOSYA)) {
            if (k.getEmail().equalsIgnoreCase(email) && k.getSifre().equals(sifre)) {
                return k;
            }
        }

        System.out.println("Hata , Giris basarisiz!");
        return null;
    }
}
