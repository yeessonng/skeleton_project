import java.sql.SQLException;
import java.util.Scanner;

public class TravelApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TravelService service = new TravelService();
        TravelService.pageCount = 1;
        while (true) {
            try {
                service.showPaging();
            } catch (SQLException e) {
                System.out.println("DB 오류: " + e.getMessage());
            }

            System.out.println("이전 페이지는 <, 다음 페이지는 >, 종료하려면 q:");
            String input = scanner.nextLine();

            if (input.equals("q")) {
                System.out.println("종료합니다!");
                break;
            } else if (input.equals("<")) {
                TravelService.pageCount--;
            } else if (input.equals(">")) {
                TravelService.pageCount++;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
