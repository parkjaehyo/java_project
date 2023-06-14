package make;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.BufferedReader;
import java.io.BufferedWriter; // 수정된 부분
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MemberManagementSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel, addPanel;
    private JButton addButton, showButton, removeButton, saveButton,editButton,searchButton,add_saveButton;
    private JTextField nameField, phoneField;
    private DefaultTableModel model;
    private JTable table;
    private String filePath = "C:\\Users\\u\\Desktop\\전화번호부 스윙.txt"; // 저장할 텍스트 파일 경로
    
    
    private List<Person> people; // 사람 정보 리스트
 
   
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
    }
   
   
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MemberManagementSystem window = new MemberManagementSystem();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    
    
    public MemberManagementSystem() {
        initialize();
        showWelcomeMessage(); // 첫 화면에 문구를 띄우는 메서드 호출
        people = new ArrayList<>(); // people 리스트 초기화
        loadPeopleFromFile();
        
    }
    
    private void loadPeopleFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            
         // 파일에서 한 줄씩 읽어오기
            while ((line = reader.readLine()) != null) {
            	// 쉼표(,)를 기준으로 이름,전화번호를 나눔
                String[] parts = line.split(",");
                

                
              //문자열이2개인 경우= 문자열(이름),문자열(전화번호)형태 일때만 실행
                if (parts.length == 2) {
                	
                    // 이름과 전화번호를 각각 변수에 저장
                	String name = parts[0];
                    String phone = parts[1];

                    // Person 객체를 생성하여 people 리스트에 추가
                    // Person은 이름과 전화번호를 저장하는 클래스입니다.
                    people.add(new Person(name, phone));
                 
                    
                    // Swing의 JTable에도 데이터를 추가
                    // model은 JTable의 데이터 모델을 관리하는 객체입니다.
                    model.addRow(new Object[]{name, phone});
                 
                }
            }
            System.out.println("데이터를 불러왔습니다.");
        } catch (IOException e) {
            System.out.println("데이터를 불러오는 데 실패하였습니다.");
        }
    }

   

    private void savePeopleToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        	
        	// people 리스트에 있는 각 Person 객체에 대해 반복문 실행
        	for (Person person : people) {
        		// Person 객체에서 이름과 전화번호를 가져와서 쉼표(,)로 구분하여 문자열 생성
                String line = person.getName() + "," + person.getPhone();
                // 문자열을 파일에 쓰기
                writer.write(line);
                //새로운 줄로 이동하여 다음 데이터를 준비
                writer.newLine();
                
            }
        } catch (IOException e) {
            System.out.println("데이터를 저장하는 데 실패하였습니다.");
        }
    }

    
    
    private void showWelcomeMessage() {
        JLabel stringLabel = new JLabel("회원 관리 프로그램");
        stringLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font koreanFont = new Font("맑은 고딕", Font.BOLD, 18);
        stringLabel.setFont(koreanFont); // 폰트 적용
        mainPanel.add(stringLabel, "welcome"); // 메인 패널에 레이블 추가
        cardLayout.show(mainPanel, "welcome"); // 첫 화면으로 welcome 레이블 보여주기
    }
    
    
 // 새로운 검색 결과를 담을 DefaultTableModel 객체를 생성합니다.
    private void searchPerson(String keyword) {
        
        DefaultTableModel searchModel = new DefaultTableModel();
        searchModel.addColumn("이름");
        searchModel.addColumn("전화번호");

        // people 리스트에서 각 Person 객체를 반복하여 검색을 수행합니다.
        for (Person person : people) {
            // person.getName()은 해당 Person 객체의 이름을 반환합니다.
            // person.getPhone()은 해당 Person 객체의 전화번호를 반환합니다.
            // contains() 메서드를 사용하여 keyword가 이름 또는 전화번호에 포함되는지 확인합니다.
        	
        	 // 검색 결과에 해당하는 데이터를 새로운 행으로 추가합니다.
            if (person.getName().contains(keyword) || person.getPhone().contains(keyword)) {
              
                searchModel.addRow(new Object[]{person.getName(), person.getPhone()});
            }
        }

        // JTable의 데이터 모델을 검색 결과로 설정합니다.
        table.setModel(searchModel);
    }



   

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 581, 360);
        setTitle("회원관리 시스템");

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        addPanel = new JPanel();

        // 회원 추가 패널 구성
        
        JLabel nameLabel = new JLabel("이름:");
        nameField = new JTextField();
        JLabel phoneLabel = new JLabel("전화번호:");
        phoneField = new JTextField();
        add_saveButton = new JButton("추가");
       
        
        addPanel.setLayout(null);
        addPanel.add(nameLabel);
        addPanel.add(nameField);
        addPanel.add(phoneLabel);
        addPanel.add(phoneField);
        addPanel.add(add_saveButton);
     
        
        nameLabel.setBounds(153, 104, 57, 15);
        nameField.setBounds(222, 101, 150, 21);
        phoneLabel.setBounds(149, 135, 57, 15);
        phoneField.setBounds(222, 132, 150, 21);
        add_saveButton.setBounds(275, 163, 97, 23);
       
       
     // 회원 관리 테이블
        String[] columnNames = {"이름", "전화번호"};
        String[][] data = {};
        model = new DefaultTableModel(data, 0) {  
        	@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames); // 헤더 이름 설정
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.getTableHeader().setVisible(false);
   
        mainPanel.add(scrollPane, "list");
        mainPanel.add(addPanel, "add");
       
        
        // 메인 프레임에 컴포넌트 추가
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // 메뉴 버튼
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("회원 추가");
        showButton = new JButton("회원 정보");
        removeButton = new JButton("회원 삭제");
        saveButton = new JButton("저장 종료");
        editButton = new JButton("회원 수정");
        searchButton = new JButton("회원 검색");
        
        //버튼 패널
        buttonPanel.add(addButton);
        buttonPanel.add(showButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(saveButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        	       	
          
        
         
        //'회원 추가' 버튼 이벤트
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//addPanel로 화면 전환
            	cardLayout.show(mainPanel, "add");             }
        });
        //'추가'버튼 이벤트
        add_saveButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { 
        		//이름,전화 번호를 입력하기 위한 Textfield 
        		String name = nameField.getText();
                String phone = phoneField.getText();
                //입력창에 아무것도 입력하지 않고 추가 버튼을 누를경우
                if (name.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "이름과 전화번호를 모두 입력해주세요.");
                    return;}
                // 전화번호 유효성 검사, 문자열을 입력하는 경우 숫자를 다시 입력받음
                if (!isValidPhoneNumber(phone)) {
                    JOptionPane.showMessageDialog(null, "유효한 전화번호를 입력해주세요.");
                    return;}
                //데이터 추가 및 '회원 정보'의테이블 업데이트
                model.addRow(new Object[]{name, phone});
    		    people.add(new Person(name, phone));
    		    // 이름,전화번호 입력창에 넣은 정보 지우기 
                nameField.setText("");
                phoneField.setText("");
                // 저장 완료 메시지 표시
                JOptionPane.showMessageDialog(null, "저장되었습니다.");
                savePeopleToFile(); // 데이터를 txt에 저장하는 메소드 실행   
                // 추가된 부분: 입력된 값 저장
                Person person = new Person(name, phone);
                people.add(person);
        	}
        	  // 전화번호 유효성 검사 메서드
            private boolean isValidPhoneNumber(String phoneNumber) {
                //숫자만 입력가능 
            	 String regex = "^\\d+$";
                return Pattern.matches(regex, phoneNumber);
            }		
        });
        
       
      //회원 출력
      showButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    	 table.setModel(model); // 기존의 모델로 설정 
                    	 table.clearSelection();// 선택되어있는 행 풀기
                    	 table.setEnabled(false); //테이블 수정불가하도록 기능 비활성화
                        cardLayout.show(mainPanel, "list");
                        table.getTableHeader().setVisible(true);
                    }
                });   
        

   // 검색 버튼 이벤트
      searchButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // 이름과 전화번호를 입력하는 패널 생성
              JPanel panel = new JPanel(new GridLayout(2, 2));
              JLabel nameLabel = new JLabel("찾고자하는 이름을 입력하십시오:");
              JTextField nameField = new JTextField(); 
              panel.add(nameLabel);
              panel.add(nameField);
              // 대화상자 표시
              int result = JOptionPane.showConfirmDialog(null, panel, "회원 검색", JOptionPane.OK_CANCEL_OPTION);
              if (result == JOptionPane.OK_OPTION) {
                  String name = nameField.getText();
                  if (name.isEmpty()) {
                      JOptionPane.showMessageDialog(null, "이름을 입력하세요.");
                      return;
                  }
                  // 숫자 입력 예외 처리
                  try {
                      Integer.parseInt(name);
                      JOptionPane.showMessageDialog(null, "숫자가 아닌 이름을 입력해주세요.");
                      return;
                  } catch (NumberFormatException ex) {
                      // 숫자가 아닌 경우 계속 진행
                  }
                  // 이름 검색
                  String keyword = nameField.getText();
                  searchPerson(keyword);
              }
          }
      });

        
       
   // 회원 수정 버튼 이벤트 처리
      editButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              table.setEnabled(true); // 테이블 선택 활성화
              cardLayout.show(mainPanel, "list");//화면 전환
              table.getTableHeader().setVisible(true);//테이블 보이기
              int selectedRow = table.getSelectedRow();
              // 수정할 회원을 선택하지 않은 경우 오류 메시지 출력 후 종료
              if (selectedRow == -1) {
                  JOptionPane.showMessageDialog(null, "수정할 회원을 선택하고 '회원 수정'을 다시 눌러주세요.");
                  return;
              }
              String name = (String) model.getValueAt(selectedRow, 0);
              String phone = (String) model.getValueAt(selectedRow, 1);
              // 이름과 전화번호 입력을 위한 패널 생성
              JPanel panel = new JPanel(new GridLayout(2, 2));
              JLabel nameLabel = new JLabel("이름:");
              JTextField nameField = new JTextField(name);
              JLabel phoneLabel = new JLabel("전화번호:");
              JTextField phoneField = new JTextField(phone);
              panel.add(nameLabel);
              panel.add(nameField);
              panel.add(phoneLabel);
              panel.add(phoneField);
              // 대화상자 표시
              int result = JOptionPane.showConfirmDialog(null, panel, "회원 정보 수정", JOptionPane.OK_CANCEL_OPTION);
              if (result == JOptionPane.OK_OPTION) {
                  String newName = nameField.getText();
                  String newPhone = phoneField.getText();
                  // 입력값 유효성 검사
                  if (newName.isEmpty() || newPhone.isEmpty()) {
                      // 이름과 전화번호가 빈 값인 경우 오류 메시지 출력 후 종료
                      JOptionPane.showMessageDialog(null, "이름과 전화번호를 모두 입력해주세요.");
                      return;
                  }
                  // 전화번호에 숫자 이외의 문자가 입력되었는지 검사
                  if (!newPhone.matches("\\d+")) {
                      // 전화번호가 숫자가 아닌 경우 오류 메시지 출력 후 종료
                      JOptionPane.showMessageDialog(null, "전화번호는 숫자만 입력해주세요.");
                      return;
                  }
                  // 회원 정보 수정
                  model.setValueAt(newName, selectedRow, 0);
                  model.setValueAt(newPhone, selectedRow, 1);
                  JOptionPane.showMessageDialog(null, "회원 정보가 수정되었습니다.");
                  savePeopleToFile(); // 데이터 저장
              }
              table.clearSelection();
          }
      });

        
        
   // 삭제 이벤트
      removeButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              table.setModel(model); // 기존의 모델로 설정, 회원 검색 이벤트 이후 누를때
              table.setEnabled(true); // 테이블 선택 활성화
              cardLayout.show(mainPanel, "list");//화면전환
              table.getTableHeader().setVisible(true);//table 보여주기

              int selectedRow = table.getSelectedRow();
              
              // 삭제할 회원을 선택하지 않은 경우 오류 메시지 출력 후 종료
              if (selectedRow == -1) {
                  JOptionPane.showMessageDialog(null, "삭제할 회원을 선택하고 '회원 삭제'를 다시 눌러주세요.");
                  return;
              }
             // 선택한 회원 삭제
              int confirm = JOptionPane.showConfirmDialog(null, "선택한 회원을 삭제하시겠습니까?", "회원 삭제", JOptionPane.YES_NO_OPTION);
              if (confirm == JOptionPane.YES_OPTION) {  
                  model.removeRow(selectedRow);
                  JOptionPane.showMessageDialog(null, "삭제되었습니다.");
              }
              savePeopleToFile(); // 데이터 저장 메소드 작동
              table.clearSelection();
          }
      });


        
        
        //저장 종료 버튼 이벤트 처리
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//filePath에 지정되있는 text파일에 데이터 저장
            	try {
                    FileWriter writer = new FileWriter(filePath);
                    for (int i = 0; i < model.getRowCount(); i++) {
                        String name = (String) model.getValueAt(i, 0);
                        String phone = (String) model.getValueAt(i, 1);
                        writer.write(name + "," + phone + "\n");
                    }
                    writer.close();
                    JOptionPane.showMessageDialog(null, "회원 정보가 저장되었습니다.");
                    System.out.println("데이터를 저장하였습니다.");
                    System.exit(0); // 파일 저장 후 프로그램 종료
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}