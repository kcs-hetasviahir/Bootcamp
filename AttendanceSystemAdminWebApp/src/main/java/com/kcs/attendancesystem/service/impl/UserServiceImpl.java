package com.kcs.attendancesystem.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kcs.attendancesystem.enums.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kcs.attendancesystem.core.User;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.UserVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.repository.UserRepository;
import com.kcs.attendancesystem.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public ResponseVO<Page<UserVO>> findUsers(int pageNo, int pageSize) {
        try {
            PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

            Page<User> userPage = userRepository.findAll(pageRequest);

            Page<UserVO> userVOSPage = userPage.map(UserVO::convert);

            return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
                    ResponseCode.SUCCESSFUL.getName(), userVOSPage);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
                    ResponseCode.FAIL.getName(), null);
        }
    }

    @Override
    public ResponseVO<List<UserVO>> findAll() {
        List<User> users = userRepository.findAll();
        return ResponseVO.create(200, users.stream().map(UserVO::convert).collect(Collectors.toList()));
    }

    @Override
    public ResponseVO<UserVO> findById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.map(user -> ResponseVO.create(200, UserVO.convert(user))).orElseGet(() -> ResponseVO.create(200, ResponseCode.SUCCESSFUL.getName(), "User with provide id not exist", null));

    }

    @Override
    public ResponseVO<UserVO> save(UserVO vo) {
        Optional<User> userOptional = userRepository.findOneByUsername(vo.getUsername());
        if (!userOptional.isPresent()) {
            User user = new User();
            BeanUtils.copyProperties(vo, user);
            user.setUserRole(UserRole.valueOf(vo.getUserRole()));
			user.setPassword(encoder.encode(vo.getPassword()));
            User savedUser = userRepository.save(user);
            return ResponseVO.create(200, UserVO.convert(savedUser));
        } else {
            return ResponseVO.create(400, "User with provide name already exist",
                    ResponseCode.BAD_REQUEST.getName(), null);
        }
    }

    @Override
    public ResponseVO<UserVO> update(UserVO vo) {
        Optional<User> userOptional = userRepository.findById(vo.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            BeanUtils.copyProperties(vo, user);
            user.setUserRole(UserRole.valueOf(vo.getUserRole()));
            user.setPassword(encoder.encode(vo.getPassword()));
            User savedUser = userRepository.save(user);
            return ResponseVO.create(200, UserVO.convert(savedUser));
        } else {
            return ResponseVO.create(400, "User with provide name already exist",
                    ResponseCode.BAD_REQUEST.getName(), null);
        }

    }

    @Override
    public ResponseVO<Void> delete(Long id) {
        try {
            Optional<User> userOpt = userRepository.findById(id);

            if (userOpt.isPresent()) {
                userRepository.delete(userOpt.get());

                return ResponseVO.create(200, "User deleted successfully");
            }

            return ResponseVO.create(400, "User with provide id not exist");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return ResponseVO.create(500, "Error in deleting User");
        }
    }
}
