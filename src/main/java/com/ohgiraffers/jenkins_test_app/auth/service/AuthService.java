package com.ohgiraffers.jenkins_test_app.auth.service;

import com.ohgiraffers.jenkins_test_app.auth.Enum.UserRole;
import com.ohgiraffers.jenkins_test_app.auth.dto.UsersDTO;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**회원가입 - db에 저장*/
    public Object signup(UsersDTO usersDTO) {

        // 유효성 검증 2차
        if(usersDTO.getUserId().isEmpty() || usersDTO.getUserId() == null){
            return "ID가 비어있음";
        }
        if(usersDTO.getPassword().isEmpty() || usersDTO.getPassword() == null){
            return "PASSWORD가 비어있음";
        }
        if(usersDTO.getNickname().isEmpty() || usersDTO.getNickname() == null){
            return "닉네임이 비어있음";
        }

        // ID, 닉네임 중복 검사
        if(userRepository.findByUserId(usersDTO.getUserId()) != null){
            return "중복된 아이디 입니다. \n다시 입력해주세요.";
        }
        if(userRepository.findByNickname(usersDTO.getNickname()) != null){
            return "중복된 닉네임 입니다. \n다시 입력해주세요.";
        }

        Users user = new Users();
        user.setUserId(usersDTO.getUserId());
        user.setNickname(usersDTO.getNickname());
        user.setPassword(bCryptPasswordEncoder.encode(usersDTO.getPassword()));
        user.setRole(UserRole.USER);
        user.setOwnBadge(0);
        user.setStatus(1);
        user.setCreatedAt(LocalDateTime.now());
        if(usersDTO.getProfilePic() != null && !usersDTO.getProfilePic().isEmpty()){
            user.setProfilePic(usersDTO.getProfilePic());
        }

        Users result = userRepository.save(user);
        return result;
    }

    /**userId가 있는지 확인
     * @return boolean
     * */
    public boolean isUserIdExists(String userId) {
        Users user = userRepository.findByUserId(userId);
        // boolean 을 리턴해주는 existsbyUserId 로 대체 가능(성능 개선)

        if(Objects.isNull(user)){
            return false;
        }
        return true;
    }

    /**nickname이 있는지 확인
     * @return boolean
     * */
    public boolean isUserNameExists(String nickname) {
        Users user = userRepository.findByNickname(nickname);

        if(Objects.isNull(user)){
            return false;
        }
        return true;
    }

    /**userId가 있는지 확인
     * 있으면 객체 봔환
     * @return Optional<Users>
     * */
    public Optional<Users> findUser(String userId) {
        Optional<Users> user = Optional.ofNullable(userRepository.findByUserId(userId));

        return user;
    }

    /**로그인시 db에 refresh 토큰 저장
     * @param Users user, String refreshToken
     * @return boolean true: 성공 / false: 실패
     * */
    public boolean addRefreshTokentoUser(Users user, String refreshToken) {
        user.setRefreshToken(refreshToken);
        Users updatedUser = userRepository.save(user);
        if(!Objects.isNull(updatedUser)){
            return true;
        }else {
            return false;
        }
    }
}
