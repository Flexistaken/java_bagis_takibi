package service;

import model.*;
import util.FileUtil;

public class BagisService {

    private static final String DOSYA = "data/bagislar.txt";

    public static void bagisYap(int bagisciId, int kurumId, BagisTuru tur, double miktar,String aciklama) {

        // Dosyadaki son bağış ID'si bulunur ve yeni bağış için ID üretilir.
        int id = FileUtil.sonIdyiBul(DOSYA) + 1;

        // Girilen bilgilere göre yeni bir bağış nesnesi oluşturulur.
        Bagis b = new Bagis(id, bagisciId, kurumId, tur, miktar,aciklama);

        FileUtil.bagisEkle(DOSYA, b);
        System.out.println("Bagis kaydedildi.");
    }
}
