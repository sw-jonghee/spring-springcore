
package com.sparta.springcore.controller;

import com.sparta.springcore.model.User;
import com.sparta.springcore.model.UserTime;
import com.sparta.springcore.repository.UserTimeRepository;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.util.NaverShopSearch;
import com.sparta.springcore.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // JSON으로 응답함을 선언합니다.
public class SearchRequestController {
    @Autowired
    private NaverShopSearch naverShopSearch;

//    @Autowired
//    private UserTimeRepository userTimeRepository;

    @GetMapping("/api/search")
    public List<ItemDto> getItems(@RequestParam String query) {
        //, @AuthenticationPrincipal UserDetailsImpl userDetails
        // 측정 시작 시간
//        long startTime = System.currentTimeMillis();
//
//        try {
//            String resultString = naverShopSearch.search(query);
//            return naverShopSearch.fromJSONtoItems(resultString);
//        } finally {
//            //측정 종료 시간
//            long endTime = System.currentTimeMillis();
//            //수행시간 = 종료시간 - 시작시간
//            long runTime = endTime - startTime;
//
//            //로그인한 유저
//            User loginUser = userDetails.getUser();
//
//            //수행시간 및 db에 기록
//            UserTime userTime = userTimeRepository.findByUser(loginUser);
//
//            if(userTime != null) {
//                //로그인 회원의 기록이 있으면
//                long totalTime = userTime.getTotalTime();
//                totalTime = totalTime + runTime;
//                userTime.updateTotalTime(totalTime);
//            } else {
////                로그인한 회원의 기록이 없으면
//                userTime = new UserTime(loginUser, runTime);
//            }
//
//            System.out.println("[User Time] User : " + userTime.getUser().getUsername() + ", Total Time : " + userTime.getTotalTime() + " ms");
//            userTimeRepository.save(userTime);
//        }
        String resultString = naverShopSearch.search(query);
        return naverShopSearch.fromJSONtoItems(resultString);

    }
}