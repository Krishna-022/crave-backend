package com.crave.crave_backend.config;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.crave.crave_backend.entity.MenuCategory;
import com.crave.crave_backend.entity.MenuItem;
import com.crave.crave_backend.entity.Restaurant;
import com.crave.crave_backend.entity.User;
import com.crave.crave_backend.exception.InvalidImageException;
import com.crave.crave_backend.repository.MenuCategoryRepository;
import com.crave.crave_backend.repository.MenuItemRepository;
import com.crave.crave_backend.repository.RestaurantRepository;
import com.crave.crave_backend.repository.UserRepository;

@Component
public class DummyDataInit implements CommandLineRunner {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RestaurantRepository restaurantRepo;

	@Autowired
	private MenuCategoryRepository menuCategoryRepo;

	@Autowired
	private MenuItemRepository menuItemRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final Logger log = LoggerFactory.getLogger(DummyDataInit.class);

	@Override
	public void run(String... args) throws Exception {
		for (int i = 1; i <= 30; i++) {

			//USER
			User user = new User();
			user.setFirstName("User" + i);
			user.setMiddleName("me" + i);
			user.setLastName("Test");
			user.setContactNumber("9" + String.format("%09d", i));
			user.setEmail("user" + i + "@gmail.com");
			user.setPasswordHash(passwordEncoder.encode("Password@" + i));

			user = userRepo.save(user);

			//RESTAURANT
			Restaurant restaurant = new Restaurant();
			restaurant.setUserId(user.getId());
			restaurant.setName("Restaurant " + i);
			restaurant.setContactNumber("8" + String.format("%09d", i));
			restaurant.setEmail("restaurant" + i + "@gmail.com");
			restaurant.setBuildingNumber((long) i);
			restaurant.setCityName("City" + i);
			restaurant.setStateName("State" + i);
			restaurant.setPinCode("40000" + (i % 10));
			restaurant.setImage(loadImage("dummyImages/Dummy_Restaurant_Image.jpeg"));

			restaurant = restaurantRepo.save(restaurant);

			//MENU CATEGORY 
			MenuCategory category = new MenuCategory();
			category.setRestaurantId(restaurant.getId());
			category.setName("Main Course");
			category.setMenuItemCount(1);

			category = menuCategoryRepo.save(category);

			//MENU ITEM
			MenuItem item = new MenuItem();
			item.setMenuCategoryId(category.getId());
			item.setName("Item " + i);
			item.setPrice(new BigDecimal("199.99"));
			item.setDescription("Test item description");
			item.setImage(loadImage("dummyImages/Menu_Item_Image.jpg"));

			menuItemRepo.save(item);
		}
		log.info("Seeded 30 users, restaurants, categories, and items.");
	}
	
	private byte[] loadImage(String path) {
	    try (InputStream is =
	         getClass().getClassLoader().getResourceAsStream(path)) {

	        if (is == null) {
	            throw new InvalidImageException("Image not found");
	        }
	        return is.readAllBytes();

	    } catch (IOException e) {
	        throw new InvalidImageException("Failed to load image");
	    }
	}
}
