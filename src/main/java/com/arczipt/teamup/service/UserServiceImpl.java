package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.mapper.*;
import com.arczipt.teamup.model.InvitationStatus;
import com.arczipt.teamup.model.ProjectMember;
import com.arczipt.teamup.model.Skill;
import com.arczipt.teamup.model.User;
import com.arczipt.teamup.repo.ProjectInvitationRepository;
import com.arczipt.teamup.repo.ProjectMemberRepository;
import com.arczipt.teamup.repo.SkillRepository;
import com.arczipt.teamup.repo.UserRepository;
import com.arczipt.teamup.repo.specifications.UserSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specification.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import static com.arczipt.teamup.repo.specifications.UserSpecifications.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ProjectInvitationRepository projectInvitationRepository;
    private ProjectMemberRepository projectMemberRepository;
    private SkillRepository skillRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ProjectInvitationRepository projectInvitationRepository,
                           ProjectMemberRepository projectMemberRepository,
                           SkillRepository skillRepository,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.projectInvitationRepository = projectInvitationRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.skillRepository = skillRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public SearchResult<UserMinDTO> search(String username, ArrayList<String> projects, ArrayList<String> skills, Pageable pageable) {
        username += '%';
        Page<User> page = userRepository.findAll(withUsernameLike(username).and(hasSkill(skills)).and(isMemberOfProject(projects)), pageable);

        SearchResult<UserMinDTO> result = new SearchResult<>();
        result.setTotalPages(page.getTotalPages());
        result.setResult(page.map(UserMapper.INSTANCE::mapToUserMinDTO).toList());

        return result;
    }

    @Transactional
    @Override
    public boolean register(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setBriefDescription(userRegisterDTO.getBriefDescription());
        user.setSkills(userRegisterDTO.getSkills().stream().map(name -> {
            Skill skill = skillRepository.findByName(name);

            if(skill != null)
                return skill;

            skill = new Skill();
            skill.setName(name);
            skillRepository.save(skill);
            return skill;
        }).collect(Collectors.toList()));
        user.setUrls(userRegisterDTO.getUrls());
        user.setRatedUsers(new ArrayList<>());
        user.setRatedBy(new ArrayList<>());
        user.setProjectMember(new ArrayList<>());
        user.setProjectInvitations(new ArrayList<>());
        user.setDescription(userRegisterDTO.getDescription());
        user.setApplications(new ArrayList<>());
        user.setHash(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRating(0);
        user.setPrivileges(new ArrayList<>());

        userRepository.save(user);

        return true;
    }

    @Transactional
    @Override
    public UserDTO findById(Long id) {
        Optional<User> op = userRepository.findById(id);

        if(op.isEmpty())
            return null;

        return UserMapper.INSTANCE.mapToUserDTO(op.get());
    }

    @Transactional
    @Override
    public UserDTO findByUsername(String username) {
        return UserMapper.INSTANCE.mapToUserDTO(userRepository.findUserByUsername(username));
    }

    /**
     * Find users with usernames matching pattern.
     *
     * @param pattern - username pattern
     * @return list of users
     */
    @Transactional
    @Override
    public SearchResult<UserMinDTO> findWithUsernameLike(String pattern, Pageable pageable) {
        pattern += "%";
        Page<User> page = userRepository.findUsersWithUsernameLike(pattern, pageable);

        SearchResult<UserMinDTO> result = new SearchResult<>();
        result.setResult(page.stream().map(UserMapper.INSTANCE::mapToUserMinDTO).collect(Collectors.toList()));
        result.setTotalPages(page.getTotalPages());

        return result;
    }

    @Transactional
    @Override
    public SearchResult<UserMinDTO> findBySkillName(String skillName, Pageable pageable) {
        Page<User> page = userRepository.findUsersBySkillName(skillName, pageable);
        SearchResult<UserMinDTO> result = new SearchResult<>();
        result.setResult(page.stream().map(UserMapper.INSTANCE::mapToUserMinDTO).collect(Collectors.toList()));
        result.setTotalPages(page.getTotalPages());

        return result;
    }

    private boolean isRatedBy(User user, String raterUsername){
        return user.getRatedBy().stream().anyMatch(u -> u.getUsername().equals(raterUsername));
    }

    @Transactional
    @Override
    public boolean rateUser(Long id, String raterUsername){
        Optional<User> op = userRepository.findById(id);
        User rater = userRepository.findUserByUsername(raterUsername);

        //TODO: error handling
        //TODO: create specialized query
        if(op.isEmpty())
            return false;

        User user = op.get();

        if(isRatedBy(user, raterUsername)){
            return false;
        }else{
            user.setRating(user.getRating() + 1);
            user.getRatedBy().add(rater);
            userRepository.save(user);
            return true;
        }
    }

    @Transactional
    @Override
    public boolean unrateUser(Long id, String raterUsername){
        Optional<User> op = userRepository.findById(id);

        if(op.isEmpty())
            throw new NullPointerException();

        User user = op.get();

        if(isRatedBy(user, raterUsername)){
            user.setRating(user.getRating() - 1);
            user.setRatedBy(user.getRatedBy().stream().filter(u -> !u.getUsername().equals(raterUsername)).collect(Collectors.toList()));
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    @Override
    public boolean isRated(Long id, String raterUsername){
        Optional<User> op = userRepository.findById(id);

        if(op.isEmpty())
            return false;

        User user = op.get();
        return isRatedBy(user, raterUsername);
    }

    @Transactional
    @Override
    public ArrayList<JobApplicationDTO> getJobApplications(String username){
        User user = userRepository.findUserByUsername(username);
        return (ArrayList<JobApplicationDTO>) user.getApplications().stream().map(JobApplicationMapper.INSTANCE::mapToJobApplicationDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ArrayList<ProjectMemberDTO> getProjects(Long id) {
        Optional<User> op = userRepository.findById(id);
        return (ArrayList<ProjectMemberDTO>) (op.map(user -> user.getProjectMember().stream().map(ProjectMemberMapper.INSTANCE::mapToProjectMemberDTO).collect(Collectors.toList())).orElse(null));
    }

    @Transactional
    @Override
    public ArrayList<ProjectInvitationDTO> getInvitations(String username, Boolean waiting) {
        Stream<ProjectInvitationDTO> stream = userRepository.findUserByUsername(username).getProjectInvitations().stream().map(ProjectInvitationMapper.INSTANCE::mapToProjectInvitationDTO);

        if(waiting)
            stream = stream.filter(inv -> inv.getStatus().equals(InvitationStatus.WAITING.name()));

        return (ArrayList<ProjectInvitationDTO>) stream.collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean updateInvitationStatus(String username, Long id, Boolean accepted) {
        AtomicBoolean ret = new AtomicBoolean(false);

        projectInvitationRepository.findById(id).filter(inv -> inv.getUser().getUsername().equals(username)).ifPresent(inv -> {
            ret.set(true);

            if(accepted){
                inv.setStatus(InvitationStatus.ACCEPTED);

                ProjectMember pm = new ProjectMember();
                pm.setRole(inv.getRole());
                pm.setProject(inv.getProject());
                pm.setUser(inv.getUser());
                pm.setAdmin(false);
                projectMemberRepository.save(pm);
            }
            else
                inv.setStatus(InvitationStatus.DECLINED);

            projectInvitationRepository.save(inv);
        });

        return ret.get();
    }

    @Transactional
    @Override
    public ArrayList<IdAndNameDTO> getManagedProjects(Long id) {
        return (ArrayList<IdAndNameDTO>) userRepository.findById(id).map(user -> user.getProjectMember().stream().filter(ProjectMember::getAdmin).map(ProjectMember::getProject).map(ProjectMapper.INSTANCE::mapToNameAndLinkDTO).collect(Collectors.toList())).orElse(null);
    }
}
