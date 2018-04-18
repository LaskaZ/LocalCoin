package localCoin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import localCoin.model.Account;
import localCoin.model.Transfer;
import localCoin.model.User;
import localCoin.repository.AccountRepository;
import localCoin.repository.TransferRepository;
import localCoin.repository.UserRepository;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TransferRepository transferRepository;

	@RequestMapping("")
	public String accountHomepage(Model model, HttpSession sess) throws IOException {
		if (checkLog(sess)) {
			BigDecimal ammountInBTC = BigDecimal.ZERO;
			BigDecimal ammountInUSD = BigDecimal.ZERO;
			List<Account> accounts = accountRepository.findByUser(getUserFromSession(sess));
			for (Account account : accounts) {
				ammountInBTC = ammountInBTC.add(account.getBalance());
			}
			JSONObject json = getJsonFromUrl();
			ammountInUSD = ammountInBTC.multiply(BigDecimal.valueOf(Double.parseDouble(json.get("last").toString())));
			model.addAttribute("ammountInBTC", ammountInBTC);
			model.addAttribute("ammountInUSD", ammountInUSD);
			model.addAttribute("transfers", transferRepository.fidByUser(getUserFromSession(sess)));
			model.addAttribute("user", getUserFromSession(sess));

			return "accountHomePage";
		}
		return "redirect:/?message=You must be logged in";
	}

	public boolean checkLog(HttpSession sess) {
		try {
			if ((Boolean) sess.getAttribute("logged")) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	@ModelAttribute(name="transfer")
	public Transfer setTransferModel() {
		return new Transfer();
	}

	@RequestMapping("/send")
	public String send(Model model, HttpSession sess) {
		if (checkLog(sess)) {
			model.addAttribute("transfer", new Transfer());
			model.addAttribute("accounts", accountRepository.findByUser(getUserFromSession(sess)));
			return "sendForm";
		}
		return "redirect:/?message=You must be logged in";
	}

	@PostMapping("/send")
	public String sendPost(@Valid Transfer transfer, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("transfer", transfer);
			return "redirect:/account/send";
		}
		
		try {
			accountRepository.findOneByAddress(transfer.getReciver().getAddress()); //jesli niebedzie takie adresu to wyrzuci blad

			Account sender = transfer.getSender();
			
			MathContext context = new MathContext(8, RoundingMode.HALF_UP);
			BigDecimal ammount = transfer.getAmmount();
			BigDecimal fee = ammount.multiply(new BigDecimal(transfer.getFee() * 0.01), context);
			BigDecimal ammoundAndFee = ammount.add(fee);
			sender.updateBalance(BigDecimal.ZERO.subtract(ammoundAndFee));
			
			if(BigDecimal.ZERO.compareTo(sender.getBalance()) > 0) {
				model.addAttribute("transfer", transfer);
				return "redirect:/account/send?message1=Not enough founds";
			}

			sendFeeToMiners(fee);
			accountRepository.save(sender);
			
			Account reciver = transfer.getReciver();
			reciver.updateBalance(ammount);
			accountRepository.save(reciver);

			transferRepository.save(transfer);

			return "redirect:/account";
		} catch (Exception e) {
			return "redirect:/account/send?message=Incorrect address";
		}
	}
	
	
	private void sendFeeToMiners(BigDecimal fee) {
		Account account = accountRepository.findOneByUserId(6);
		System.out.println(fee + " feeeeeeeeeeeee");
		account.updateBalance(fee);
		accountRepository.save(account);
	}
	
	@RequestMapping("/recive")
	public String recive(Model model, HttpSession sess) {
		if (checkLog(sess)) {
			model.addAttribute("accounts", accountRepository.findByUser(getUserFromSession(sess)));
			return "reciveForm";
		}
		return "redirect:/?message=You must be logged in";
	}

	@PostMapping("/recive")
	public String postRecive(@Valid Transfer transfer, BindingResult result, HttpServletRequest request) {

		sendEmail(transfer, request.getParameter("email"));

		return "redirect:/account";
	}

	@RequestMapping("/addAddress")
	public String addAddress(HttpSession sess) {
		accountRepository.save(new Account(getUserFromSession(sess)));
		return "redirect:/account";
	}

	public User getUserFromSession(HttpSession sess) {
		return userRepository.findOneByUserNameOrEmai(sess.getAttribute("user").toString());
	}

	public void sendEmail(Transfer transfer, String email) {
		String fromEmail = "wojzabr@gmail.com";
		String toEmail = email; //transfer.getSender().getUser().getEmail()
		System.out.println(toEmail);

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("wojzabr", "02022020");
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject("Payment request from user " + transfer.getReciver().getUser().getEmail());
			message.setText(transfer.toString());
			Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String readAllFromUrl(Reader reader) throws IOException {
		StringBuilder text = new StringBuilder();
		int token;
		while ((token = reader.read()) != -1) {
			text.append((char) token);
		}
		String textString = text.toString();
		System.out.println(textString);
		return textString;
	}

	private JSONObject getJsonFromUrl() throws IOException, JSONException {
		// InputStream inputStream = new
		// URL("https://api.coindesk.com/v1/bpi/currentprice.json").openStream();
		URLConnection inputStream = new URL("https://www.bitstamp.net/api/ticker/").openConnection();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream.getInputStream(), Charset.forName("UTF-8")));
		JSONObject json = new JSONObject(readAllFromUrl(reader));
		reader.close();
		return json;

	}

}
