package make;
//
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManageConsole {
    private List<Person> people;
    private String filePath;

    public ManageConsole() {
        people = new ArrayList<>();
        filePath = "C:\\\\\\\\Users\\\\\\\\u\\\\\\\\Desktop\\\\\\\\전화번호부 콘솔.txt"; // 데이터를 저장할 파일 경로
    }

    public static void main(String[] args) {
        ManageConsole console = new ManageConsole();
        console.start();
    }

    public void start() {
        loadPeopleFromFile(); // 파일에서 데이터 로드

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("=== 회원관리 시스템 ===");
            System.out.println("1.회원 추가");
            System.out.println("2.회원 정보");
            System.out.println("3.회원 검색");
            System.out.println("4.회원 수정");
            System.out.println("5.회원 삭제");
            System.out.println("6.저장 종료");
            System.out.println("==================");
            System.out.print("선택: ");

            int choice = 0;
            boolean validInput = false;
            
            //숫자만 입력하라는 예외처리
            while (!validInput) {
                try {
                    String input = scanner.nextLine();
                    choice = Integer.parseInt(input);
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("올바른 숫자를 입력하세요.");
                }
            }


            switch (choice) {
                case 1:
                    addPerson(scanner);
                    break;
                case 2:
                    printPeople();
                    break;
                case 3:
                    searchPerson(scanner);
                    break;
                case 4:
                    updatePerson(scanner);
                    break;
                case 5:
                    removePerson(scanner);
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 입력해주세요.");
            }
        }

        savePeopleToFile(); // 데이터를 파일에 저장
        scanner.close();
        System.out.println("프로그램을 종료합니다.");
    }

    private void addPerson(Scanner scanner) {
        String name = ""; // 이름 변수 초기화
        while (name.isEmpty()) { // 이름이 비어있는 동안 반복
            System.out.print("이름: ");
            name = scanner.nextLine().trim(); // 사용자로부터 이름 입력받아서 공백 제거 후 변수에 저장
            if (name.isEmpty()) { // 이름이 비어있으면 오류 메시지 출력
                System.out.println("이름을 입력해 주십시오.");
                }
        }
        String phone = "";
        while (phone.isEmpty()) {
            System.out.print("전화번호: ");
            phone = scanner.nextLine().trim();
            if (phone.isEmpty()) {
                System.out.println("전화번호를 입력해 주십시오.");
            } else if (!phone.matches("\\d+")) {
                System.out.println("전화번호는 숫자로만 입력해 주십시오.");
                phone = "";
            }
        }
        Person person = new Person(name, phone); // 이름과 전화번호를 사용하여 Person 객체 생성
        people.add(person); // Person 객체를 people 리스트에 추가
        System.out.println("정보가 추가되었습니다.");
    }


    private void printPeople() {
        System.out.println("=== 저장된 회원 목록 ===");
        if (people.isEmpty()) {
            System.out.println("저장된 정보가 없습니다. 정보를 추가해 주십시오.");
        } else {
            for (int i = 0; i < people.size(); i++) {
                Person person = people.get(i);
                System.out.println((i + 1) + ") " + person);
            }
        }
    }
    
    private void searchPerson(Scanner scanner) {
        System.out.print("이름을 입력해주십시오:");
        String keyword = scanner.nextLine().trim();

        System.out.println("=== 검색 결과 ===");
        boolean found = false;
        for (Person person : people) {
            if (person.getName().contains(keyword) || person.getPhone().contains(keyword)) {
                System.out.println(person);
                found = true;
            }
        }

        if (!found) {
            System.out.println("일치하는 회원이 없습니다.");
        }
    }
    

    private void updatePerson(Scanner scanner) {
    	 System.out.println("=== 저장된 회원 목록 ===");
         if (people.isEmpty()) {
             System.out.println("저장된 정보가 없습니다. 정보를 추가해 주십시오.");
         } else {
             for (int i = 0; i < people.size(); i++) {
                 Person person = people.get(i);
                 System.out.println((i + 1) + ") " + person);
             }
         }

        System.out.print("수정할 회원 번호: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        if (index >= 1 && index <= people.size()) {
            Person person = people.get(index - 1);

            System.out.print("수정할 이름: ");
            String name = scanner.nextLine();

            System.out.print("수정할 전화번호: ");
            String phone = scanner.nextLine();

            person.setName(name);
            person.setPhone(phone);

            System.out.println("정보가 수정되었습니다.");
        } else {
            System.out.println("잘못된 회원 번호입니다.");
        }
    }

    private void removePerson(Scanner scanner) {
    	 System.out.println("=== 저장된 회원 목록 ===");
         if (people.isEmpty()) {
             System.out.println("저장된 정보가 없습니다. 정보를 추가해 주십시오.");
         } else {
             for (int i = 0; i < people.size(); i++) {
                 Person person = people.get(i);
                 System.out.println((i + 1) + ") " + person);
             }
         }
        System.out.print("삭제할 회원 번호: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        if (index >= 1 && index <= people.size()) {
            Person person = people.remove(index - 1);
            System.out.println(person.getName() + "님의 정보가 삭제되었습니다.");
        } else {
            System.out.println("잘못된 회원 번호입니다.");
        }
    }
    
    

    private void loadPeopleFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    String phone = parts[1];
                    Person person = new Person(name, phone);
                    people.add(person);
                }
            }
            System.out.println("데이터를 로드하였습니다.");
        } catch (IOException e) {
            System.out.println("데이터를 로드하는 데 실패하였습니다.");
        }
    }

    private void savePeopleToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Person person : people) {
                String line = person.getName() + "," + person.getPhone();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("데이터를 저장하였습니다.");
        } catch (IOException e) {
            System.out.println("데이터를 저장하는 데 실패하였습니다.");
        }
    }
}

class Person {
    private String name;
    private String phone;

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "이름: " + name + ", 전화번호: " + phone;
    }
}

