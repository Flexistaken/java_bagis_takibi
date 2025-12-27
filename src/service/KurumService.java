package service;

import model.Kurum;
import util.FileUtil;

import java.util.List;

public class KurumService {

    private static final String DOSYA = "data/kurumlar.txt";

    public static void kurumEkle(String ad) {

        // Dosyadaki son kurum ID'si bulunur ve yeni bir benzersiz ID üretilir.
        int id = FileUtil.kurumSonId(DOSYA) + 1;
        // Yeni kurum nesnesi oluşturulup dosyaya kaydedilir.
        FileUtil.kurumEkle(DOSYA, new Kurum(id, ad));

        System.out.println("Kurum eklendi.");
    }

    public static List<Kurum> kurumlariGetir() {
        return FileUtil.kurumlariOku(DOSYA);
    }
}
