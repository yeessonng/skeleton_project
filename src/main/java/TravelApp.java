import java.util.Scanner;

public class TravelApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TravelService service = new TravelService();

        System.out.println("권역별 관광지 검색 프로그램");
        System.out.print("지역명을 입력하세요 (예: 수도권, 충청권, 전라권): ");
        String region = scanner.nextLine();

        try {
            service.showTourListByDistrict(region);
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
    }
}

