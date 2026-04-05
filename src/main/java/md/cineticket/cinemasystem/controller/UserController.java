package md.cineticket.cinemasystem.controller;

import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.ChangePasswordRequest;
import md.cineticket.cinemasystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserEmailById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEmailById(id));
    }

}
