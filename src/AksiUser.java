import java.util.Map;
import java.util.Scanner;

public class AksiUser extends Aksi {
    @Override
    public void tampilanAksi() {
        System.out.println("Aksi User:");
        System.out.println("1. Pesan Film");
        System.out.println("2. Lihat Saldo");
        System.out.println("3. Lihat List Film");
        System.out.println("4. Lihat Pesanan");
        System.out.println("5. Logout");
        System.out.println("6. Tutup Aplikasi");
    }

    @Override
    public void keluar() {
        Akun.logout();
        System.out.println("Anda telah logout.");
    }

    @Override
    public void tutupAplikasi() {
        System.out.println("Aplikasi ditutup.");
        System.exit(0);
    }

    @Override
    public void lihatListFilm() {
        Film.getFilms().forEach((name, film) ->
                System.out.println("Film: " + film.getName() + " - Deskripsi: " + film.getDescription() +
                        " - Harga: " + film.getPrice() + " - Stok: " + film.getStock()));
    }

    public void lihatSaldo() {
        System.out.println("Saldo anda: " + Akun.getCurrentUser().getSaldo());
    }

    public void pesanFilm() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nama Film yang ingin dipesan: ");
        String filmName = scanner.nextLine();
        Film film = Film.getFilms().get(filmName);
        if (film == null) {
            System.out.println("Film yang dicari tidak ditemukan.");
            return;
        }

        System.out.print("Jumlah tiket yang ingin dipesan: ");
        int jumlahTiket = scanner.nextInt();
        if (jumlahTiket > film.getStock()) {
            System.out.println("Stok tiket tidak mencukupi.");
            return;
        }

        double totalHarga = jumlahTiket * film.getPrice();
        if (Akun.getCurrentUser().getSaldo() < totalHarga) {
            System.out.println("Saldo tidak mencukupi, saldo yang dimiliki " + Akun.getCurrentUser().getSaldo());
            return;
        }

        film.setStock(film.getStock() - jumlahTiket);
        Akun.getCurrentUser().setSaldo(Akun.getCurrentUser().getSaldo() - totalHarga);
        Akun.getCurrentUser().addPesanan(film, jumlahTiket);
        System.out.println("Tiket berhasil dipesan.");
    }

    public void lihatPesanan() {
        Map<String, Pesanan> pesanan = Akun.getCurrentUser().getPesanan();
        if (pesanan.isEmpty()) {
            System.out.println("Kamu belum pernah melakukan pemesanan.");
        } else {
            pesanan.forEach((name, order) ->
                    System.out.println("Film: " + order.getFilm().getName() + " - Jumlah: " + order.getKuantitas() +
                            " - Total Harga: " + (order.getKuantitas() * order.getFilm().getPrice())));
        }
    }
}
