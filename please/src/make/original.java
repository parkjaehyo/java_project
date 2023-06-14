//제거 이벤트에서 제거하면 제거한 내용이 반영이 안됌
//시작할때 txt 불러오기

package make;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableModel;
import java.io.FileWriter;
import java.io.IOException;


public class original extends JFrame {
    JFrame frame;
    JPanel panel;
    JButton button;
    JLabel nameLabel, phoneLabel;
    JButton saveButton;
    private JTextField nameField, phoneField;
    private DefaultTableModel model;
    private JTable table;

	
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    original window = new original();
                    //window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

	/**
	 * Create the application.
	 */
 // txt 연동 기능
    private void saveDataToTextFile() {
        String filePath = "C:\\Users\\u\\Desktop\\test.txt"; // 저장할 텍스트 파일 경로
        try (FileWriter writer = new FileWriter(filePath)) {
            int rowCount = model.getRowCount();
            int columnCount = model.getColumnCount();
            // txt에 데이터 쓰기
            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < columnCount; column++) {
                    writer.write(model.getValueAt(row, column).toString() + "\n");
                }
                writer.write(System.lineSeparator());
                writer.write("\n");
            }

            System.out.println("텍스트 파일에 저장되었습니다.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

	public original() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

	    String[] columnNames = {"이름","전화번호"};
	
	    String[][] data = {}; // JTable의 데이터

	    // JTable 모델 생성
	    model = new DefaultTableModel(data, columnNames){
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; // 편집 불가능하게 설정
	        }
	    };

	    // JTable 생성	    
	    table = new JTable(model);

	    // 스크롤 가능한 JTable 생성
	    JScrollPane scrollPane = new JScrollPane(table);

	    // JFrame 생성
	    //JFrame
	    frame = new JFrame("회원관리 시스템");
	    frame.getContentPane().add(scrollPane);
	    frame.getContentPane().add(new JScrollPane(table));
	    
	    
	    frame.setBounds(100, 100, 450, 300);// 위치 크기 
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    Container c = getContentPane();//컨텐트팬을 알아낸다
	    c.setLayout(null);
	    getContentPane().setLayout(null);
	    
	    
		JLabel nameLabel = new JLabel("이름:");
		JTextField nameField = new JTextField();
		JLabel phoneLabel = new JLabel("전화번호:");
		JTextField phoneField = new JTextField();
		
		//입력 필드 안보이게 하기
		nameLabel.setVisible(false);
		nameField.setVisible(false);
		phoneLabel.setVisible(false);
		phoneField.setVisible(false);
		
		
		
		//버튼
		JButton add =new JButton("회원 추가");
		JButton showButton = new JButton("정보출력");
		JButton remove = new JButton("정보삭제");
		JButton deleteButton = new JButton("제거");
		JButton saveButton = new JButton("저장");
		JButton saveButtonforadd = new JButton("입력");
		
		//버튼 숨기기
		deleteButton.setVisible(false);
		saveButton.setVisible(false);
		saveButtonforadd.setVisible(false);
		
		
		
		// 폼의 위치와 크기 지정
		nameLabel.setBounds(25, 45, 100, 30);
		nameField.setBounds(84, 46, 200, 30);
		phoneLabel.setBounds(12, 85, 100, 30);
		phoneField.setBounds(84, 85, 200, 30);
		showButton.setBounds(317, 204, 95, 40);

		// 폼에 추가
		getContentPane().add(nameLabel);
		getContentPane().add(nameField);
		getContentPane().add(phoneLabel);
		getContentPane().add(phoneField);
		getContentPane().add(showButton);

		
		//JTABLE
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(310, 21, 305, 172);
		getContentPane().add(scrollPane_1);
		
		table = new JTable(model);
		scrollPane_1.setViewportView(table);
		
		

	    //추가 버튼 이벤트
	    add.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                	deleteButton.setVisible(false); //제거 버튼 숨기기
	                	saveButton.setVisible(false);//저장버튼 숨기기
	                	
	                	//저장 필드 보이게 하기
	                	nameLabel.setVisible(true);
	            		nameField.setVisible(true);
	            		phoneLabel.setVisible(true);
	            		phoneField.setVisible(true);
	            		
	                	
	                	saveButtonforadd.setVisible(true);
	                	table.setVisible(false); //table이 안보이게함
	                                    }
                });
           
        add.setBounds(210, 203, 95, 42);
        c.add(add);

        
		
		//입력 버튼 좌표
		saveButtonforadd.setBounds(227, 120, 57, 40);
		getContentPane().add(saveButtonforadd);
		
        
        //입력 버튼 이벤트
        saveButtonforadd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        
     
                // 이름과 전화번호를 가져와서 회원 목록에 추가하는 코드를 작성
                String name = nameField.getText();
                String phone = phoneField.getText();
                                
                
             // 전화번호 길이와 문자열이 숫자인지 여부 검사
                if (phone.length() < 10 || phone.length() > 13 || !phone.matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(null, "전화번호는 10이상 14미만의 숫자를 입력해주세요.");
                    return;
                }
                
                //이름 혹은 전화번호 입력을 누락했을 경우
                if (name.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "이름과 전화번호를 모두 입력해주세요.");
                    return;
                }
                
                
                JOptionPane.showMessageDialog(null, "정보가 추가 되었습니다.");
                // 여기에 회원 목록에 추가하는 코드 작성
                model.addRow(new Object[]{name, phone}); 
                	
                saveDataToTextFile();
                nameField.setText("");
                phoneField.setText("");
              
            	            	
            }
        });
		
        
        
		// 출력 버튼 클릭 시 이벤트 처리
		showButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	//버튼 숨기기
		    	saveButtonforadd.setVisible(false);//입력버튼 숨기기
		    	saveButton.setVisible(false);//저장버튼 숨기기
		    	deleteButton.setVisible(false); //제거 버튼 숨기기
		    	//입력 필드 안보이게 하기
				nameLabel.setVisible(false);
				nameField.setVisible(false);
				phoneLabel.setVisible(false);
				phoneField.setVisible(false);
			

		    	
		    	table.setVisible(true); //table보여줌 
		    	
		   
		    	  	 
		    }
		});

		
		// 저장 버튼의 경로, 보이지 않게함
				saveButton.setBounds(230, 125, 57, 30);
				c.add(saveButton);
				saveButton.setVisible(false);
				
				
		
	   //저장버튼(수정이벤트) 이벤트 처리
	   saveButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    
        String name = nameField.getText(); 
        String phone = phoneField.getText();
        
     	int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {        
            return;
        }
	
        
        // 전화번호 길이와 문자열이 숫자인지 여부 검사
        if (phone.length() < 10 || phone.length() > 13 || !phone.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "전화번호는 10이상 14미만의 숫자를 입력해주세요.");
            return;
        }
        
        //이름 혹은 전화번호 입력을 누락했을 경우
        if (name.isEmpty() && phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "이름과 전화번호를 모두 입력해주세요.");
            return;
        }
        
      /*  // 회원 정보 수정
        model.setValueAt(name, selectedRow, 0);
        model.setValueAt(phone, selectedRow, 1);
        
        JOptionPane.showMessageDialog(null, "수정되었습니다.");
        nameField.setText("");
        phoneField.setText("");
        //행 선택 풀기
        table.clearSelection();
        saveDataToTextFile();
        */   	

		    }
		});
	
		
		
		
		
		//수정 버튼,이벤트
		JButton updateButton = new JButton("정보 수정");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				JOptionPane.showMessageDialog(null, "수정할 회원을 선택하고 수정 후 저장버튼을 누르세요");
				saveButton.setVisible(true);
				//다른 버튼 안보이게 하기
				saveButtonforadd.setVisible(false);//입력버튼 안보이게하기
				deleteButton.setVisible(false); //제거 버튼보이기
				
			//선택된 행 풀기
	        table.clearSelection();

			
			}
			
		});
		updateButton.setBounds(417, 204, 95, 40);
		getContentPane().add(updateButton);
		
	

		
		//삭제 버튼 좌표
		getContentPane().add(remove);
				remove.setBounds(515, 203, 100, 43);
				
		//제거 버튼 좌표		
				deleteButton.setBounds(227, 125, 57, 40);
				getContentPane().add(deleteButton);
			
				
				
		
		//제거 이벤트
		remove.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	deleteButton.setVisible(true); //제거 버튼 보이기
		    	//버튼 없애기
		    	saveButton.setVisible(false);
		    	
		    	//선택된 행 풀기
		    	table.clearSelection();
		    	
		    	JOptionPane.showMessageDialog(null, "삭제할 회원을 선택하세요.");
		    	
		        int selectedRow;
		        selectedRow= table.getSelectedRow();
		        if (selectedRow == -1) {
		     
		            return;
		        }
		        model.removeRow(selectedRow);
		        saveDataToTextFile();
		    }
		});
		
		
		//삭제 이벤트
		deleteButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	deleteButton.setVisible(true); //제거 버튼 보이기
		    	saveButton.setVisible(false);//저장 버튼 숨기기
		    	
		        int selectedRow;
		        selectedRow= table.getSelectedRow();
		        if (selectedRow == -1) {
		     
		            return;
		        }
		        model.removeRow(selectedRow);
		       
		    }
		});
		
		
		
		
	
		setSize(800,300); //창 크기 1000x200
		setVisible(true);
		
	}// 함수
}//class


