package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello~!");
        return "hello";
        // resource의 templates 하위에 hello 라는 html로 rendering.
    }

    @GetMapping("hello-mvc")
    // /hello-mvc 경로로 오는 traffic을 control. (return은 hello-template이므로 hello-template.html 의 템플릿 엔진이 처리하도록 rendering)
    public String helloMvc(@RequestParam(value="name") String yourname, Model model){
        model.addAttribute("name", yourname);
        // model 의 key가 name이고 value가 yourname인 key-value data를 삽입.
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody // 반드시 필요!! 해당 어노테이션을 이용하면 http요청 body를 Java 객체로 전달받을 수 있음.
    // (여기서는 Java의 String을 그대로 받음.)
    public String helloString(@RequestParam("name") String anyString){
        return "hello " + anyString;
        // ResponseBody란, http의 body에 내가 이 Data를 직접 넣어주겠다는 의미.
        // API방식은 화면에 html을 띄우기 위한 View같은 것이 없음. 그냥 이 문자가 그대로 내려감.
    }

    @GetMapping("hello-api")
    @ResponseBody
    // ResponseBody가 붙어있으면 Template Engine에 던지는 게 아니라 이걸 바로 그냥 http response로
    // 응답을 넘겨야 되겠구나! 라고 반응을 하게 됨.
    // HttpMessageConverter가 동작.
    // 문자열이면 그냥 넘기면 되는데(String Converter), 객체라면 Default로 JSON객체로 만들어서 반환하게 됨(JSON Converter).
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

}
