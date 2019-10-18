package it.xpug.kata.birthday_greetings;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.Date;
import java.util.GregorianCalendar;


public class XDateTest {
	@Test
	public void getters() throws Exception {
		XDate date = new XDate("1789/01/24");
		assertEquals(1, date.getMonth());
		assertEquals(24, date.getDay());
	}

	@Test
	public void isSameDate() throws Exception {
		XDate date = new XDate("1789/01/24");
		XDate sameDay = new XDate("2001/01/24");
		XDate notSameDay = new XDate("1789/01/25");
		XDate notSameMonth = new XDate("1789/02/25");

		assertTrue("same", date.isSameDay(sameDay));
		assertFalse("not same day", date.isSameDay(notSameDay));
		assertFalse("not same month", date.isSameDay(notSameMonth));
	}

	@Test
	public void equality() throws Exception {
		XDate base = new XDate("2000/01/02");
		XDate same = new XDate("2000/01/02");
		XDate different = new XDate("2000/01/04");

		assertNotEquals(null, base);
		assertNotEquals("", base);
		assertEquals(base, base);
		assertEquals(base, same);
		assertNotEquals(base, different);
	}
}