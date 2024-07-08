package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.impl.IUserInfoService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserInfoController {
    private final IUserInfoService userInfoService;

    /**
     * 회원가입 화면으로 이동
     */
    @GetMapping(value = "/user/userRegForm")
    public String userRegForm() {
        log.info(this.getClass().getName() + ".user/userRegForm");

        return "/user/userRegForm";

    }

    @PostMapping(value = "/user/insertUserInfo")
    public String insertUserInfo(HttpServletRequest request, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + "insertUserInfo start!");
        int res;
        String msg = "";  //회원가입 결과에 대한 메시지를 전달할 변수
        String url = "";  //회원가입 결과에 대한 url을 전달할 변수

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;
        try {

            /*
             *##################################################################################
             *                 웹(회원정보 입력화면)애서 받는 정보를 String 변수에 저장 시작!!
             *
             *             무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #################################################################################
             */
            String user_id = CmmUtil.nvl(request.getParameter("user_id"));
            String user_name = CmmUtil.nvl(request.getParameter("user_name"));
            String password = CmmUtil.nvl(request.getParameter("password"));
            String email = CmmUtil.nvl(request.getParameter("email"));
            String addr1 = CmmUtil.nvl(request.getParameter("addr1"));
            String addr2 = CmmUtil.nvl(request.getParameter("addr2"));
            /*
             *##################################################################################
             *                 웹(회원정보 입력화면)애서 받는 정보를 String 변수에 저장 끝!!
             *
             *             무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #################################################################################
             */
            /*
             *##################################################################################
             *                 반드시, 값을 받았으면 꼭 로그를 찍어서
             *
             *             무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #################################################################################
             */
            log.info("user_id : " + user_id);
            log.info("user_name : " + user_name);
            log.info("password : " + password);
            log.info("email : " + email);
            log.info("addr1 : " + addr1);
            log.info("addr2 : " + addr2);
            /*
             *##################################################################################
             *                 반드시, 값을 받았으면 꼭 로그를 찍어서
             *
             *             무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #################################################################################
             */

            //
            pDTO = new UserInfoDTO();

            pDTO.setUser_id(user_id);
            pDTO.setUser_name(user_name);
            pDTO.setPassword(password);
            pDTO.setEmail(email);
            pDTO.setAddr1(addr1);
            pDTO.setAddr2(addr2);
            /*
             *##################################################################################
             *                 반드시, 값을 받았으면 꼭 로그를 찍어서
             *
             *             무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #################################################################################
             */
            //
            res = userInfoService.insertUserInfo(pDTO);

            log.info("회원가입 결과(res) : " + res);

            if (res == 1) {
                msg = "회원가입되었습니다.";
                url = "/main";
                //

            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
                url = "/user/userRegForm";
            }
        } catch (DuplicateKeyException e) { //
            msg = "이미 가입된 아이디입니다. 다른 아이디로 변경  후 다시 시도해주세요.";
            url = "/user/userRegForm";
            log.info(e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            //
            msg = "시스템 오류로 실패하였습니다. 다시 시도해주세요.";
            url = "/user/userRegForm";
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            log.info("출력할 메시지 : " + msg);
            log.info("이동할 경로 : " + url);
            model.addAttribute("msg", msg);
            model.addAttribute("url", url);

            log.info(this.getClass().getName() + ".insertUserInfo End!");
        }

        return "/redirect";
    }

    @GetMapping(value = "/user/login")
    public String login() {
        log.info(this.getClass().getName() + ".user/login Start!");
        log.info(this.getClass().getName() + ".user/login End!");
        return "/user/login";
    }

    @PostMapping(value = "/user/loginProc")
    public String loginProc(HttpServletRequest request, ModelMap model, HttpSession session) {

        log.info(this.getClass().getName() + ".LoginProc Start!");

        String msg = ""; //로그인 결과에 대한 메시지를 전달할 변수
        String url = "";
        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;

        try {

            String user_id = CmmUtil.nvl(request.getParameter("user_id"));
            String password = CmmUtil.nvl(request.getParameter("password"));

            log.info("user_id : " + user_id);
            log.info("password : " + password);

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUser_id(user_id);
            pDTO.setPassword(password);

            //로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 suerInfoService 호출하기
            UserInfoDTO rDTO = userInfoService.getLogin(pDTO);

            if (CmmUtil.nvl(rDTO.getUser_id()).length() > 0) { //로그인 성공

                session.setAttribute("SS_USER_ID", user_id);
                session.setAttribute("SS_USER_NAME", CmmUtil.nvl(rDTO.getUser_name()));

                msg = "로그인이 성공했습니다. \n" + rDTO.getUser_name() + "님 환영합니다.";
                url = "/main";
            } else {
                msg = "아이디와 비밀번호가 올바르지 않습니다.";
                url = "/user/login";
            }
        } catch (Exception e) {
            //저장이 실패하면 사용자에게 보여줄 메시지
            msg = "시스템 문제로 로그인이 실패했습니다.";
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            model.addAttribute("msg", msg);
            model.addAttribute("url", url);

            log.info(this.getClass().getName() + ".loginProc End!");
        }

        return "/redirect";


    }
}




