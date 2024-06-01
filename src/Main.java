import java.util.Scanner;

public class Main {
    static AksiAdmin aksiAdmin = new AksiAdmin();
    static AksiUser aksiUser = new AksiUser();
    static Scanner scanner = new Scanner(System.in);
    static boolean run = true;
    static boolean isLogin = false;

    public static void main(String[] args) {
        User.addUser("user", "123", false, 100000);
        User.addUser("admin", "123", true, 100000);

        while (run) {
            Aksi.welcome();
            int aksi = scanner.nextInt();

            if (aksi == 1) {
                while (!isLogin) {
                    System.out.println("Silakan login >_<");
                    System.out.print("Username: ");
                    String username = scanner.next();
                    System.out.print("Password: ");
                    String password = scanner.next();

                    User user = authenticate(username, password);

                    if (user != null) {
                        isLogin = true;
                        Akun.login(user);
                        System.out.println("Selamat datang " + Akun.getCurrentUser().getUsername());
                        if (Akun.getCurrentUser().isAdmin()) {
                            handleAksiAdmin();
                        } else {
                            handleAksiUser();
                        }
                    } else {
                        System.out.println("Username atau password salah. Silakan coba lagi.");
                    }
                }
                isLogin = false;
            } else {
                run = false;
                System.out.println("Aplikasi ditutup.");
            }
        }
    }

    private static User authenticate(String username, String password) {
        User user = User.getUsers().get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private static void handleAksiUser() {
        while (true) {
            aksiUser.tampilanAksi();
            int pilihan = scanner.nextInt();
            switch (pilihan) {
                case 1 -> aksiUser.pesanFilm();
                case 2 -> aksiUser.lihatSaldo();
                case 3 -> aksiUser.lihatListFilm();
                case 4 -> aksiUser.lihatPesanan();
                case 5 -> {
                    aksiUser.keluar();
                    return;
                }
                case 6 -> aksiUser.tutupAplikasi();
                default -> System.out.println("Pilihan tidak valid");
            }
        }
    }

    private static void handleAksiAdmin() {
        while (true) {
            aksiAdmin.tampilanAksi();
            int pilihan = scanner.nextInt();
            switch (pilihan) {
                case 1 -> aksiAdmin.tambahFilm();
                case 2 -> aksiAdmin.lihatListFilm();
                case 3 -> {
                    aksiAdmin.keluar();
                    return;
                }
                case 4 -> aksiAdmin.tutupAplikasi();
                default -> System.out.println("Pilihan tidak valid");
            }
        }
    }
}
