package com.example.springboot.controller;

import com.example.springboot.dto.CarDto;
import com.example.springboot.model.Favorite;
import com.example.springboot.model.User;
import com.example.springboot.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@CrossOrigin(origins = {"http://localhost:4160","https://sortably-nonaffiliating-my.ngrok-free.dev"})
@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;
	@Autowired private com.example.springboot.repository.UserRepository userRepository;

	private User currentUser(UserDetails principal) {
		return userRepository.findByemail(principal.getUsername()).orElseThrow();
	}

    @PostMapping
    public ResponseEntity<Void> addFavorite(@AuthenticationPrincipal UserDetails principal, @RequestParam Long carId) {
		favoriteService.addFavorite(currentUser(principal), carId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{carId}")
	public ResponseEntity<Void> removeFavorite(@AuthenticationPrincipal UserDetails principal, @PathVariable Long carId) {
		favoriteService.removeFavorite(currentUser(principal), carId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<CarDto>> getUserFavorites(@AuthenticationPrincipal UserDetails principal) {
		List<CarDto> favorites = favoriteService.getUserFavoriteCars(currentUser(principal));
		return ResponseEntity.ok(favorites);
	}
}


