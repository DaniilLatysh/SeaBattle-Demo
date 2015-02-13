import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.MarshalException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import java.net.InetAddress;
import java.util.Properties;
import java.util.Date;
import java.util.Vector;

import javax.mail.*;
import javax.mail.internet.*;

import com.mysql.jdbc.Statement;
import com.sun.mail.smtp.*;
import com.sun.org.apache.bcel.internal.classfile.Code;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import jdk.nashorn.internal.scripts.JO;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;
import javax.mail.*;
import javax.swing.JOptionPane;

public class Login extends JFrame {

	private JLabel Login, Password, TableName;
	private JTextField LoginIn;
	private JPasswordField PasswordIn;
	private JButton LoginButt, NewPass, Registration;
	private JTable Table;
	private DB db;
	public static String i;

	Login() {
		JFrame jf = new JFrame();
		setTitle("Login User");
		setLocation(700, 350);
		setSize(420, 380);
		setResizable(false);

		Login = new JLabel("Введите ваш логин: ");
		Login.setBounds(100, 30, 300, 20);

		LoginIn = new JTextField();
		LoginIn.setBounds(100, 50, 200, 20);

		Password = new JLabel("Введите ваш пароль: ");
		Password.setBounds(100, 100, 200, 20);

		PasswordIn = new JPasswordField();
		PasswordIn.setBounds(100, 130, 200, 20);

		LoginButt = new JButton("Войти");
		LoginButt.setBounds(30, 180, 100, 30);

		Registration = new JButton("Регистрация");
		Registration.setBounds(150, 180, 100, 30);

		NewPass = new JButton("Восстановление пароля");
		NewPass.setBounds(270, 180, 120, 30);

		TableName = new JLabel("Список пользователей:");
		TableName.setBounds(30, 180, 360, 100);
		Table = new JTable();
		Table.setBounds(30, 240, 360, 100);

		add(Login);
		add(LoginIn);
		add(Password);
		add(PasswordIn);
		add(LoginButt);
		add(NewPass);
		add(Registration);
		add(TableName);
		add(Table);

		try {
			db = new DB("jdbc:mysql://localhost/", "authorization", "root", "5813");
		} catch (SQLException e1) {
			System.out.println("Ошибка подключения к базе данных!");
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		Registration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean checkEmail = Checker.email(LoginIn.getText());
				boolean checkPass = Checker.password(PasswordIn.getText());
				if (!checkEmail && !checkPass)
					JOptionPane.showMessageDialog(null,
							"Ваш Логин и Пароль не корректны!", "Error", 1);
				else {
					if (!checkEmail)
						JOptionPane.showMessageDialog(null,
								"Ваш email не корректен", "Error", 1);
					if (!checkPass)
						JOptionPane.showMessageDialog(null,
								"1234 - это не пароль!", "Error", 1);

				}

				if (checkEmail && checkPass) {
					try {
						db.update("use authorization");
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
					try {
						db.update("INSERT INTO user(login, password) VALUES ('"
								+ LoginIn.getText() + "','"
								+ PasswordIn.getText() + "');");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null,
							"Регистрация прошла успешно!", "Successfully", 2);
					try {
						db.showResultSet(db.query("SELECT * FROM user;"));
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
			}
		});

		LoginButt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					db.update("use authorization");
					ResultSet data = db.query("SELECT *FROM user Where login='"
							+ LoginIn.getText() + "' AND password='"
							+ PasswordIn.getText() + "';");

					if (data.next()) {
						JOptionPane.showMessageDialog(
								null,
								"Вы успешно вошли на аккаунт:\n"
										+ data.getString(2));
						PlaceShip pl1 = new PlaceShip(data.getString("login"));
					} else {
						JOptionPane.showMessageDialog(null,
								"Неверный логин или пароль");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		NewPass.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					i = JOptionPane.showInputDialog("Введите ваш email: \n");
					GoToMail.sendMail(i, GoToMail.randomPassword());
				} catch (Exception w) {
					JOptionPane.showMessageDialog(null, "Err");
				}

			}
		});

		setLayout(null);
		setVisible(true);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}
}
// Вход в систему
// регистрация логина
// замена