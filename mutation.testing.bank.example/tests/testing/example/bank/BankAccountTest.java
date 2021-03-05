package testing.example.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class BankAccountTest {

	private static final int AMOUNT = 3;

	private static final int INITIAL_BALANCE = 10;

	@Test
	public void testIdIsAutomaticallyAssignedAsPositiveNumber() {
		// setup
		BankAccount bankAccount = new BankAccount();
		// verify
		assertTrue("Id should be positive", bankAccount.getId() > 0);
	}

	@Test
	public void testIdsAreIncremental() {
		assertTrue("Ids should be incremental",
			new BankAccount().getId() < new BankAccount().getId());
	}

	@Test
	public void testDepositWhenAmountIsCorrectShouldIncreaseBalance() {
		// setup
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(INITIAL_BALANCE);
		// exercise
		bankAccount.deposit(AMOUNT);
		// verify
		assertEquals(INITIAL_BALANCE+AMOUNT, bankAccount.getBalance(), 0);
	}

	@Test
	public void testDepositWhenAmountIsNegativeShouldThrow() {
		// setup
		BankAccount bankAccount = new BankAccount();
		try {
			// exercise
			bankAccount.deposit(-1);
			fail("Expected an IllegalArgumentException to be thrown");
		} catch (IllegalArgumentException e) {
			// verify
			assertEquals("Negative amount: -1.0", e.getMessage());
			assertEquals(0, bankAccount.getBalance(), 0);
		}
	}

	@Test
	public void testDepositWhenAmountIsNegativeShouldThrowWithAssertThrows() {
		BankAccount bankAccount = new BankAccount();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			() -> bankAccount.deposit(-1));
		// perform assertions on the thrown exception
		assertEquals("Negative amount: -1.0", e.getMessage());
		// and we can perform further assertions...
		assertEquals(0, bankAccount.getBalance(), 0);
	}

	@Test
	public void testWithdrawWhenAmountIsNegativeShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			() -> bankAccount.withdraw(-1));
		assertEquals("Negative amount: -1.0", e.getMessage());
		assertEquals(0, bankAccount.getBalance(), 0);
	}

	@Test
	public void testWithdrawWhenBalanceIsUnsufficientShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			() -> bankAccount.withdraw(10));
		assertEquals("Cannot withdraw 10.0 from 0.0", e.getMessage());
		assertEquals(0, bankAccount.getBalance(), 0);
	}

	@Test
	public void testWithdrawWhenBalanceIsSufficientShouldDecreaseBalance() {
		// setup
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(INITIAL_BALANCE);
		// exercise
		bankAccount.withdraw(AMOUNT); // the method we want to test
		// verify
		assertEquals(INITIAL_BALANCE-AMOUNT, bankAccount.getBalance(), 0);
	}

	@Test
	public void testDepositWhenAmountIsZeroShouldBeAllowed() {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(INITIAL_BALANCE);
		bankAccount.deposit(0);
		assertEquals(INITIAL_BALANCE, bankAccount.getBalance(), 0);
	}

	@Test
	public void testWithdrawWhenAmountIsZeroShouldBeAllowed() {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(INITIAL_BALANCE);
		bankAccount.withdraw(0);
		assertEquals(INITIAL_BALANCE, bankAccount.getBalance(), 0);
	}

	@Test
	public void testWithdrawWhenBalanceIsEqualsToAmountShouldBeAllowed() {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(INITIAL_BALANCE);
		bankAccount.withdraw(INITIAL_BALANCE);
		assertEquals(0, bankAccount.getBalance(), 0);
	}
}
