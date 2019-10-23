package it.xpug.kata.birthday_greetings;

import static org.junit.Assert.*;

import org.junit.*;

import com.dumbster.smtp.*;

public class AcceptanceTest {

	private static final int NONSTANDARD_PORT = 9999;
	private SimpleSmtpServer mailServer;
	private SmtpMessageSender smtpMessageSender = new SmtpMessageSender("localhost",NONSTANDARD_PORT);
	private BirthdayService birthdayService = new BirthdayService(smtpMessageSender);

	@Before
	public void setUp() throws Exception {
		mailServer = SimpleSmtpServer.start(NONSTANDARD_PORT);
	}

	@After
	public void tearDown() throws Exception {
		mailServer.stop();
		Thread.sleep(200);
	}

	@Test
	public void willSendGreetings_whenItsSomebodysBirthday() throws Exception {

		birthdayService.sendGreetings("employee_data.txt", new XDate("2008/10/08"),smtpMessageSender);

		assertEquals("message not sent?", 1, mailServer.getReceivedEmailSize());
		SmtpMessage message = (SmtpMessage) mailServer.getReceivedEmail().next();
		assertEquals("Happy Birthday, dear John!", message.getBody());
		assertEquals("Happy Birthday!", message.getHeaderValue("Subject"));
		String[] recipients = message.getHeaderValues("To");
		assertEquals(1, recipients.length);
		assertEquals("john.doe@foobar.com", recipients[0].toString());
	}

	@Test
	public void willNotSendEmailsWhenNobodysBirthday() throws Exception {
		birthdayService.sendGreetings("employee_data.txt", new XDate("2008/01/01"),smtpMessageSender);

		assertEquals("what? messages?", 0, mailServer.getReceivedEmailSize());
	}
}
