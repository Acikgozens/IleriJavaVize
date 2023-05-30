package tr.edu.medipol.java.vize;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@RestController
public class VizeController {

    @GetMapping("/")
    public String ad(){
        return "HR210027 Enes Efe Açıkgöz";
    }
    @GetMapping("/Ortak-Kelimeleri-Bul")
    public Set<String> ortakKelimeleriBul(String metin1, String metin2) {

        // 2 metinin kelimelerini küçük harfle yazdırıyoruz
        String[] kelimeler1 = metin1.toLowerCase().split("\\s+");
        String[] kelimeler2 = metin2.toLowerCase().split("\\s+");

        // Ortak kelimeleri bulmak için iki küme kullanıyoruz
        Set<String> kume1 = new HashSet<>(Arrays.asList(kelimeler1));
        Set<String> kume2 = new HashSet<>(Arrays.asList(kelimeler2));
        //ortak olanları küme1 de birleştiriyoruz
        kume1.retainAll(kume2);

        return kume1;
    }
    @GetMapping("/metin-islemi")
    public String metinIslemi(String metin) {
        //trim ile metnin başındaki ve sonundaki boşlukları yok ediyoruz sonra metini büyük harfle yazdırıp aralarındaki boşlukları siliyoruz
        String sonucMetni = metin.trim().toUpperCase().replaceAll("\\s","");
        return "{ \"sonuc\": \"" + sonucMetni + "\" }";
    }

    @GetMapping("/HesaplamaIslemi")
    public String hesaplamaIslemi(int a, int b) {

        // Alanı hesaplıyoruz
        double alan = Math.PI * a * b;

        // Çevreleri verilmiş olan formulle hesaplıyoruz
        double cevre1 = Math.PI * (a + b);
        double cevre2 = Math.PI * Math.sqrt(2 * (a*a + b*b));
        double cevre3 = Math.PI * ((3.0/2.0)*(a+b) - Math.sqrt(a*b));

        // Sonuçları JSON formatında döndürüyoruz
        return "{ \"alan\": \"" + alan + "\", \"cevre1\": \"" + cevre1 + "\", \"cevre2\": \"" + cevre2 + "\", \"cevre3\": \"" + cevre3 + "\" }";
    }

    @GetMapping("/karekok")
    public String karekok(int i) {//Bunda biraz yardım aldım!!

        try {
            // Dosya ismi ve path'i oluşturuluyor
            String dosyaAdi = "karekokler_" + i + ".txt";
            String dosyaYolu = System.getProperty("user.dir") + "/" + dosyaAdi;

            // Dosya oluşturuyoruz
            File dosya = new File(dosyaYolu);
            dosya.createNewFile();

            // Dosyaya yazma işlemi yapıyoruz
            BufferedWriter writer = new BufferedWriter(new FileWriter(dosya));
            for (int sayi = 1; sayi <= i; sayi++) {
                double karekok = Math.sqrt(sayi);
                writer.write(String.format("%.2f", karekok));
                writer.newLine();//i den başlayıp sayıya kadar olan karekökleri yazdırıyoruz
            }
            writer.close();

            return "Başarılı, dosya adresi: " + dosyaYolu;

        } catch (IOException e) {//işlem başarısız olursa başarısız mesajı verilecek
            e.printStackTrace();
            return "Başarısız";
        }
    }
}
