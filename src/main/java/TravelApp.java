import java.sql.SQLException;
import java.util.*;

public class TravelApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TravelService service = new TravelService();

        Map<Integer, String> regionMap = new LinkedHashMap<>() {{
            put(1, "수도권");
            put(2, "강원권");
            put(3, "충청권");
            put(4, "전라권");
            put(5, "경상권");
            put(6, "제주권");
        }};

        System.out.println("╔═════════════════════════════════════════════════════╗");
        System.out.println("║           Welcome to the Travel Search App          ║");
        System.out.println("║      관광지 검색 프로그램에 오신 걸 환영합니다!     ║");
        System.out.println("╚═════════════════════════════════════════════════════╝");
        String input = "";

        mainLoop: while (true) {
            System.out.println("\n=== 관광지 검색 프로그램 ===");
            System.out.println("1. 전체 관광지 목록 조회");
            System.out.println("2. 권역별 관광지 목록 조회");
            System.out.println("3. 관광지명 or 키워드로 검색");
            System.out.println("0. 종료");
            System.out.print("메뉴를 선택하세요: ");

            String choice = scanner.hasNextLine() ? scanner.nextLine() : "";

            switch (choice) {
                case "1":
                    while (true) {
                        try {
                            service.showPaging();
                        } catch (SQLException e) {
                            System.out.println("DB 오류: " + e.getMessage());
                        }

                        input = scanner.nextLine();

                        if (input.equals("exit")) {
                            System.out.println("종료합니다!\n");
                            continue mainLoop;
                        } else if (input.equals("<")) {
                            TravelService.pageCount--;
                            if (TravelService.pageCount == 0) {
                                TravelService.pageCount = 12;
                            }
                        } else if (input.equals(">")) {
                            TravelService.pageCount++;
                            if (TravelService.pageCount == 13) {
                                TravelService.pageCount = 1;
                            }
                        } else {
                            System.out.println("잘못된 입력입니다.\n");
                        }
                    }

                case "2":
                    while (true) {
                        System.out.println("\n지역을 선택하세요:");
                        regionMap.forEach((key, value) -> System.out.println(key + ". " + value));
                        System.out.print("번호를 입력해주세요. (9: 이전 화면): ");
                        String regionChoice = scanner.hasNextLine() ? scanner.nextLine() : "";

                        int regionNum;
                        try {
                            regionNum = Integer.parseInt(regionChoice);
                            if (regionNum == 9) break;
                            if (!regionMap.containsKey(regionNum)) {
                                System.out.println("잘못된 번호입니다. 다시 입력해주세요.\n");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("숫자를 입력해주세요.");
                            continue;
                        }

                        String region = regionMap.get(regionNum);

                        while (true) {
                            try {
                                List<TravelVO> tourList = service.getTourListByDistrict(region);
                                if (tourList.isEmpty()) {
                                    System.out.println("해당 지역의 관광지를 찾을 수 없습니다.\n");
                                    break;
                                }

                                System.out.println("\n[ " + region + " 지역 관광지 목록 ]");
                                for (int i = 0; i < tourList.size(); i++) {
                                    System.out.println((i + 1) + ". " + tourList.get(i).getTitle());
                                }

                                System.out.print("상세정보를 확인하려면 관광지 번호를 입력하세요 (9: 이전, 0: 초기화면): ");
                                input = scanner.hasNextLine() ? scanner.nextLine() : "";

                                int index;
                                try {
                                    index = Integer.parseInt(input);
                                } catch (NumberFormatException e) {
                                    System.out.println("숫자를 입력해주세요.");
                                    continue;
                                }

                                if (index == 0) continue mainLoop;
                                if (index == 9) break;
                                if (index < 1 || index > tourList.size()) {
                                    System.out.println("잘못된 번호입니다.");
                                    continue;
                                }

                                TravelVO selected = tourList.get(index - 1);
                                System.out.println("\n===== 관광지 상세 정보 =====");
                                System.out.println(" 이름: " + selected.getTitle());
                                System.out.println(" 지역: " + selected.getDistrict());
                                System.out.println(" 주소: " + selected.getAddress());

                                String description = selected.getDescription();
                                int lineLength = 80;
                                System.out.print(" 소개: ");
                                for (int i = 0; i < description.length(); i += lineLength) {
                                    int end = Math.min(i + lineLength, description.length());
                                    String part = description.substring(i, end);
                                    if (i == 0) {
                                        System.out.println(part);
                                    } else {
                                        System.out.println("       " + part);
                                    }
                                }

                                System.out.println(" 전화: " + selected.getPhone());
                                System.out.println("===========================\n");

                                System.out.print("Enter를 누르면 목록으로 돌아갑니다...");
                                scanner.nextLine();

                            } catch (Exception e) {
                                System.out.println("오류 발생: " + e.getMessage());
                            }
                        }
                    }
                    break;

                case "3":
                    while (true) {
                        System.out.print("\n관광지명이나 키워드를 입력하세요 (예: 남이섬, 케이블카... 9: 이전 화면): ");
                        String title = scanner.hasNextLine() ? scanner.nextLine() : "";
                        if (title.equals("9")) break;

                        try {
                            boolean found = service.showTourListByTitle(title);
                            if (!found) {
                                System.out.println("해당 관광지를 찾을 수 없습니다. 다시 입력해주세요.");
                            } else {
                                System.out.println();
                                continue; // 검색 성공 후 다시 검색
                            }
                        } catch (Exception e) {
                            System.out.println("오류 발생: " + e.getMessage());
                        }
                    }
                    break;

                case "0":
                    System.out.println("\n╔═══════════════════════════════════════════════════╗");
                    System.out.println("║   이용해 주셔서 감사합니다. 즐거운 여행 되세요!   ║");
                    System.out.println("╚═══════════════════════════════════════════════════╝");
                    return;

                default:
                    System.out.println("올바른 메뉴 번호를 선택해주세요.");
            }
        }
    }
}
