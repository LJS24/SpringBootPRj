package kopo.poly.service.impl;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mapper.IUserInfoMapper;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoService implements IUserInfoService{

    private final IUserInfoMapper userInfoMapper; //회원관련 SQL 사용하기 위한 Mapper 가져오기

    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo Start!");

        //회원가입 성공: 1, 기타 에러 발생: 0
        int res = 0;

        //회원가입
        res = userInfoMapper.insertUserInfo(pDTO);


        log.info(this.getClass().getName() + ".insertUserInfo End!");

        return res;
    }
    @Override
    public UserInfoDTO getLogin(UserInfoDTO pDTO) throws Exception{

        log.info(this.getClass().getName() + ".getLogin Start!");

        //
        //
        UserInfoDTO rDTO = Optional.ofNullable(userInfoMapper.getLogin(pDTO)).orElseGet(UserInfoDTO::new);

        log.info("로그인 가져오는 정보" + rDTO);

        /*
        *
        *
        *
        *
        *
         */
        if (CmmUtil.nvl(rDTO.getUser_id()).length() > 0){

            log.info("로그인 성공");
        }

        log.info(this.getClass().getName() + ".getLogin End!");

        return rDTO;
    }

}
