package localCoin.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import localCoin.model.Account;
import localCoin.model.User;
import localCoin.repository.AccountRepository;
import localCoin.repository.UserRepository;

@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	AccountRepository accountRepository;

	@ModelAttribute(name = "user")
	public User userModel() {
		return new User();
	}

	@PostMapping("/login")
	public String login(HttpSession sess, @RequestParam String userName, @RequestParam String password) {
		User user = new User();
		user = getUser(userName);
		if (user != null && BCrypt.checkpw(password, user.getPassword())) {
			sess.setAttribute("logged", true);
			sess.setAttribute("user", userName);
			return "redirect:/account";
		}

		sess.setAttribute("logged", false);
		return "redirect:/?message=Incorrect login or password";

	}

	// private User getUser(String userName) {
	// try {
	// return userRepository.findOneByUserNameOrEmai(userName);
	// } catch (Exception e) {
	// return null;
	// }
	//
	// }
	private User getUser(String userName) {
		User user = null;
		try {
			user = userRepository.findOneByUserName(userName);
		} catch (Exception e) {

		}
		if (user == null) {
			try {
				user = userRepository.findOneByEmail(userName);
			} catch (Exception e) {

			}
		}
		return user;
	}

	@RequestMapping("/register")
	public String getRegister() {
		return "registerForm";
	}

	@PostMapping("/register")
	public String postRegister(@Valid User user, BindingResult result, @RequestParam String passwordConfirm) {
		
		if(getUser(user.getUserName()) != null  || getUser(user.getEmail()) != null) {
			return "redirect:/user/register?message=User already exists";
		}
		
		if (!result.hasErrors() && passwordConfirm.equals(user.getPassword())) {
			user.setPassword(BCrypt.hashpw(passwordConfirm, BCrypt.gensalt()));
			userRepository.save(user);
			new Account(user, 10);
			accountRepository.save(new Account(user, 10));

			return "redirect:/?message=Now you can log in to your account";
		}
		return "redirect:/user/register?message=Incorrect data";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession sess) {
		sess.setAttribute("logged", false);
		return "redirect:/";
	}

}
