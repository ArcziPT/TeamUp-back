package com.arczipt.teamup.controller;

import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.security.AuthenticationProvider;
import com.arczipt.teamup.service.UserService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;
    private AuthenticationProvider authProvider;

    @Autowired
    UserController(UserService userService,
                   AuthenticationProvider authProvider){
        this.authProvider = authProvider;
        this.userService = userService;
    }

    /**
     * Search for users by specified parameter.
     *
     * @param searchBy - search parameter
     * @param pattern - search parameter's value
     * @param sortBy - sorting parameter
     * @param order - order of result
     * @return list of matching users
     */
    @GetMapping("/search")
    public SearchResult<UserMinDTO> search(@RequestParam(defaultValue = "") String searchBy,
                                   @RequestParam(defaultValue = "") String pattern,
                                   @RequestParam(defaultValue = "") String sortBy,
                                   @RequestParam(defaultValue = "") String order,
                                   @RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "0") Integer size){

        if(searchBy.equals("") || pattern.equals("") || sortBy.equals("") || order.equals(""))
            return null; //TODO

        PageRequest pageRequest;
        switch (order){
            case "asc" -> pageRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            case "des" -> pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
            default -> throw new IllegalStateException("Unexpected value: " + order);
        }

        SearchResult<UserMinDTO> users;
        switch (searchBy) {
            case "username" -> users = userService.findWithUsernameLike(pattern, pageRequest);
            case "skill" -> users = userService.findBySkillName(pattern, pageRequest);
            default -> throw new IllegalStateException("Unexpected value: " + searchBy);
        }

        return users;
    }

    /**
     * Returns public data (profile) of user.
     *
     * @param id - user's id
     * @return users' info
     */
    @GetMapping("/{id}")
    public UserDTO getProfile(@PathVariable Long id){
        return userService.findById(id);
    }

    /**
     * Increase the rating.
     * User calling this method must have not rated the user.
     *
     * @param id - id of a user, whose rating will be increased
     * @return - success "Y", error "N"
     */
    @PostMapping("/{id}/rate")
    public ResponseEntity<?> rate(@PathVariable Long id){
        String raterUsername = authProvider.getUsername();
        return ResponseEntity.ok(new StatusDTO(userService.rateUser(id, raterUsername)));
    }

    /**
     * Decrease the rating.
     * User calling this method must have rated the user.
     *
     * @param id - id of a user, whose rating will be decreased
     * @return - success "Y", error "N"
     */
    @PostMapping("/{id}/unrate")
    public ResponseEntity<?> unrate(@PathVariable Long id){
        String raterUsername = authProvider.getUsername();
        return ResponseEntity.ok(new StatusDTO(userService.unrateUser(id, raterUsername)));
    }

    /**
     * Checks if specified user was rated by caller.
     *
     * @param id - user to check
     * @return "Y" - rated, "N" - not rated
     */
    @GetMapping("/{id}/isRated")
    public ResponseEntity<?> isRated(@PathVariable Long id){
        String raterUsername = authProvider.getUsername();
        return ResponseEntity.ok(new StatusDTO(userService.isRated(id, raterUsername)));
    }

    @GetMapping("/")
    public UserDTO userView(){
        return userService.findByUsername(authProvider.getUsername());
    }

    @GetMapping("/applications")
    public ArrayList<JobApplicationDTO> getApplications(){
        String username = authProvider.getUsername();
        return userService.getJobApplications(username);
    }

    @GetMapping("/invitations")
    public ArrayList<ProjectInvitationDTO> getInvitations(@RequestParam(defaultValue = "true") Boolean waiting){
        String username = authProvider.getUsername();

        return userService.getInvitations(username, waiting);
    }

    @PutMapping("/invitations/{id}")
    public ResponseEntity<?> updateInvitationStatus(@PathVariable Long id, @RequestParam Boolean accepted){
        if(accepted == null)
            return ResponseEntity.badRequest().build();

        String username = authProvider.getUsername();

        return ResponseEntity.ok(new StatusDTO(userService.updateInvitationStatus(username, id, accepted)));
    }

    @GetMapping("/{id}/projects")
    public ArrayList<ProjectMemberDTO> getProjects(@PathVariable Long id){
        return userService.getProjects(id);
    }

    @GetMapping("/{id}/managedProjects")
    public ArrayList<IdAndNameDTO> getManagedProjects(@PathVariable Long id){
        return userService.getManagedProjects(id);
    }
}
