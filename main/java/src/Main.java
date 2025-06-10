package main.java.src;

public class Main {
    // API key yang digunakan untuk autentikasi (harus dicocokkan oleh request)
    public static final String API_KEY = "123456"; // kamu bisa ubah sesuai kebutuhan

    public static void main(String[] args) {
        try {
            System.out.println("Memulai server...");
            Server server = new Server(8080); // port 8080
            server.start();
            System.out.println("Server berjalan di http://localhost:8080");
        } catch (Exception e) {
            System.err.println("Gagal menjalankan server: " + e.getMessage());
        }
    }
}
