package localCoin.model;

import java.math.BigDecimal;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String address = "";
	@Column(scale = 8, precision = 16)
	private BigDecimal balance = BigDecimal.ZERO;
	private String type = "bitcoin";
	@ManyToOne
	private User user;

	public void updateBalance(BigDecimal ammount) {
		// MathContext context = new MathContext(8, RoundingMode.HALF_UP);
		// balance = balance.add(ammount, context);
		balance = balance.add(ammount);
		System.out.println(balance + "" + ammount);
	}

	@Override
	public String toString() {
		return address + "  " + balance;
	}

	public Account() {

	}

	public Account(User user, int balance) {
		super();
		generateAddress();
		this.balance = BigDecimal.valueOf(balance);
		this.user = user;
	}

	public Account(User user) {
		super();
		generateAddress();
		this.user = user;
	}

	private void generateAddress() {
		Random random = new Random();
		for (int i = 0; i < 34; i++) {
			int number = (random.nextInt(122 - 49) + 48);
			if ((number >= 57 && number <= 65) || (number >= 90 && number <= 97)) {
				i--;
			} else {
				this.address += (char) number;
			}

		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
