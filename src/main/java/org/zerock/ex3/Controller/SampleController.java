package org.zerock.ex3.Controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.ex3.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {
    @GetMapping("/ex1")
    public void ex01(){
        log.info("ex1 ... ... ...");
    }

    @GetMapping({"/ex2","/exLink"})       //url부분을 {} 로 감싸면 여러개의 URL을 지정할 수 있음.
    public void exModel(Model model){
        //asLongStream() -> IntStream타입을 Long요소로 타입 변환해서 LongStream으로 생성
        //mapToObj  을 사용해서 LongStream을 List<SampleDTO>로 변환
        List<SampleDTO> list = IntStream.rangeClosed(1,20).asLongStream().mapToObj(i ->{
            SampleDTO dto = SampleDTO.builder()
                    .sno(i)
                    .first("First ..."+i)
                    .last("Last ..."+i)
                    .regTime(LocalDateTime.now())
                    .build();
            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("list",list);

    }


    @GetMapping({"/exInline"})          //RedirectAttribute는 url에 붙지 않는다. .addFlashAttribute는 리다이렉트 직전 Flash에 저장하고 리다이렉트 후 소멸한다.
    public String exInline(RedirectAttributes redirectAttributes){
        log.info("exInline  Method");

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("First....100")
                .last("Last...100")
                .regTime(LocalDateTime.now())
                .build();

        redirectAttributes.addFlashAttribute("result","success");
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex3";
    }

    @GetMapping("/ex3")
    public void ex3(){
        log.info("Ex3");
    }

    //thymeleaf의 replace
    @GetMapping({"/exLayout1","/exLayout2","/exTemplate","/exSidebar"})
    public void exLayout1(){
        log.info("exLayout..........");
    }
}
