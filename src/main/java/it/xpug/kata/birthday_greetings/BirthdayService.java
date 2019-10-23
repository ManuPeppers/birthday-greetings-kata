package it.xpug.kata.birthday_greetings;

import com.sun.mail.smtp.SMTPMessage;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BirthdayService {

	private SmtpMessageSender messageSender;

	public BirthdayService(SmtpMessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public void sendGreetings(String fileName, XDate xDate, SmtpMessageSender messageSender) throws IOException, ParseException, AddressException, MessagingException {

		EmployeeRepository fileEmployeeRepository = new FileEmployeeRepository(fileName);

		List<Employee> employeesWithBirthdayToday = fileEmployeeRepository.getEmployeesWhoseBirthdayIsToday(xDate);

		for(Employee employee: employeesWithBirthdayToday){

			String recipient = employee.getEmail();
			String body = "Happy Birthday, dear %NAME%".replace("%NAME%", employee.getFirstName()+"!");
			String subject = "Happy Birthday!";
			sendMessage(messageSender, subject, body, recipient);

		}
	}

	private void sendMessage(SmtpMessageSender smtpMessageSender, String subject, String body, String recipient) throws AddressException, MessagingException {

		this.messageSender = new SmtpMessageSender(smtpMessageSender.getSmtpHost(),smtpMessageSender.getSmtpPort());

		// Create a mail session
		Session session = createMailSession(smtpMessageSender.getSmtpHost(),smtpMessageSender.getSmtpPort());

		// Construct the message
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("sender@here.com"));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		msg.setSubject(subject);
		msg.setText(body);

		// Send the message
		Transport.send(msg);
	}

	public Session createMailSession(String smtpHost, int smtpPort) {
		java.util.Properties props = new java.util.Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", "" + smtpPort);
		return Session.getInstance(props, null);
	}
}
