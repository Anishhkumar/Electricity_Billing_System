
package electricity.billing.system;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Signup extends JFrame implements ActionListener {
    
    JTextField username, name, password, meter; 
    Choice accountType; 
    JButton create, back;
    Signup() {
       
        setBounds(450, 150, 700, 400);
         getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(30, 30, 650,300);
        panel.setBorder(new TitledBorder(new LineBorder(Color.black, 2), "Create-Account", TitledBorder.LEADING,TitledBorder.TOP, null, Color.black));
        panel.setBackground(Color.WHITE);
        panel.setForeground(new Color(34, 139, 34));
        panel.setLayout(null);
        add(panel);
        
        JLabel heading  = new JLabel("Create Account As:");
        heading.setForeground(Color.BLACK);
        heading.setBounds(100, 50, 140, 20);
        heading.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(heading);
        
        accountType = new Choice();
        accountType.add("Admin");
        accountType.add("Customer");
        accountType.setBounds(260, 50, 150, 20);
        panel.add(accountType);
        
        
        
        JLabel lblmeter  = new JLabel("Meter Number :");
        lblmeter.setForeground(Color.BLACK);
        lblmeter.setBounds(100, 90, 140, 20);
        lblmeter.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblmeter.setVisible(false);
        panel.add(lblmeter);
        
        meter = new JTextField();
        meter.setBounds(260, 90, 150, 20 );
        meter.setVisible(false);
        panel.add(meter);
        
        JLabel lblusername  = new JLabel("Username :");
        lblusername.setForeground(Color.BLACK);
        lblusername.setBounds(100, 130, 140, 20);
        lblusername.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblusername);
        
        username = new JTextField();
        username.setBounds(260, 130, 150, 20 );
        panel.add(username);
        
        JLabel lblname  = new JLabel("Name :");
        lblname.setForeground(Color.BLACK);
        lblname.setBounds(100, 170, 140, 20);
        lblname.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblname);
        
        name = new JTextField();
        name.setBounds(260, 170, 150, 20 );
        panel.add(name);
        
        
        meter.addFocusListener(new FocusListener() {
           @Override
            public void focusGained(FocusEvent fe) {}
            
            @Override
            public void focusLost(FocusEvent fe) {
            
                try {
                   Conn c = new Conn();
                   ResultSet rs = c.s.executeQuery("select * from login where meter_no = '"+meter.getText()+"'");
                   while(rs.next()) {
                       name.setText(rs.getString("name"));
                   }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        
        
        JLabel lblpassword  = new JLabel("Password :");
        lblpassword.setForeground(Color.BLACK);
        lblpassword.setBounds(100, 210, 140, 20);
        lblpassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblpassword);
        
        password = new JTextField();
        password.setBounds(260, 210, 150, 20 );
        panel.add(password);
        
        accountType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ae) {
                String user = accountType.getSelectedItem();
                if (user.equals("Customer")) {
                    lblmeter.setVisible(true);
                    meter.setVisible(true);
                    name.setEditable(false);
                }else {
                  lblmeter.setVisible(false);
                  meter.setVisible(false);
                  name.setEditable(true);
                }
            }
        });
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
        Image i2 = i1.getImage().getScaledInstance(16, 16,Image.SCALE_DEFAULT);
        create = new JButton("Create", new ImageIcon(i2));
        create.setBounds(140, 260, 120, 25);
        create.addActionListener(this);
        panel.add(create);
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
        Image i4 = i3.getImage().getScaledInstance(16, 16,Image.SCALE_DEFAULT);
        back = new JButton("Back", new ImageIcon(i4));
        back.setBounds(300, 260, 120, 25);
        back.addActionListener(this);
        panel.add(back);
        
        ImageIcon i5 = new ImageIcon(ClassLoader.getSystemResource("icon/signupImage.png"));
        Image i6 = i5.getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT);
        ImageIcon i7 = new ImageIcon(i6);
        JLabel image = new JLabel(i7);
        image.setBounds(415, 30, 250, 250);
        panel.add(image);
        
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae) {
      if (ae.getSource() == create) {
          String atype = accountType.getSelectedItem();
          String susername = username.getText();
          String sname = name.getText();
          String spassword = password.getText();
          String smeter = meter.getText();
          
          try {
            Conn c = new Conn();
            
            String query = null; 
            if (atype.equals("Admin")) {
                 query = "insert into login values('"+smeter+"', '"+susername+"', '"+sname+"', '"+spassword+"', '"+atype+"')";
            } else {
                query = "update login set username = '"+susername+"', password = '"+spassword+"', user = '"+atype+"'where meter_no = '"+smeter+"'";
            }
          c.s.executeUpdate(query);
          
          JOptionPane.showMessageDialog(null, "Account Created Successfully");
          
          setVisible(false);
          new Login();
          }catch (Exception e) {
              e.printStackTrace();
          }
          
      } else if (ae.getSource() == back) {
          setVisible(false);
          
          
          new Login();
          
          
        }
      
    }
    public static void main(String args[]) {
        new Signup();
    }
    
}
