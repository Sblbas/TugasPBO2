import Tugas2.Server;

public class Main {
    // API key yang digunakan untuk autentikasi (harus dicocokkan oleh request)
    public static final String API_KEY = "123456"; // kamu bisa ubah sesuai kebutuhan

    public class Tugas2 {

        public static void main(String[] args) throws Exception {
            int port = 8080;
            if (args.length == 1) {
                port = Integer.parseInt(args[0]);
            }
            System.out.printf("Listening on port: %s...\n", port);
            new Server(port);
        }
    }
}
