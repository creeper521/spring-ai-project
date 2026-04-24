package com.example.springaidemo.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/sse")
public class SseController {
    @RequestMapping("/data")
    public void data(HttpServletResponse response) throws IOException, InterruptedException{
        log.info("发出请求：data");
        response.setContentType("text/event-stream;charset=utf-8");//确定是sse类型
        PrintWriter writer = response.getWriter();
        for (int i = 0;i < 10;i++){
            String s = "data: " + new Date() + "\n\n";
            writer.write(s);
            writer.flush();
            Thread.sleep(1000L);
        }
    }

    /**
     * 控制重连时间
     * @param response
     * @throws IOException
     */
    @RequestMapping("/retry")
    public void retry(HttpServletResponse response) throws IOException{
        log.info("发起请求：retry");
        response.setContentType("text/event-stream;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String s = "retry: 2000\n";
        s += "data: " + new Date() + "\n\n";
        writer.write(s);
        writer.flush();
    }

    /**
     * 自定义事件
     * @param response
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping("/event")
    public void event(HttpServletResponse response) throws IOException, InterruptedException {
        log.info("发送请求：event");
        response.setContentType("text/event-stream;charset=utf-8");
        PrintWriter writer = response.getWriter();
        for(int i = 0;i < 10;i++){
            String s = "event: foo\n";
            s += "data: " + new Date() + "\n\n";
            writer.write(s);
            writer.flush();
            Thread.sleep(1000L);
        }
    }

    /**
     * 终止事件
     * @param response
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping("/end")
    public void end(HttpServletResponse response) throws IOException, InterruptedException {
        log.info("发起请求: event");
        response.setContentType("text/event-stream;charset=utf-8");
        PrintWriter writer = response.getWriter();
        for (int i = 0; i < 10; i++) {
            String s = "event: foo\n";
            s += "data: " + new Date() + "\n\n";
            writer.write(s);
            writer.flush();
            Thread.sleep(1000L);
        }
        //定义end事件, 表示当前流传输结束
        writer.write("event: end\ndata: EOF\n\n");
        writer.flush();
    }

    /**
     * 流式打印时间
     * @return
     */
    @RequestMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(){
        return Flux.interval(Duration.ofSeconds(1)).map(s->new Date().toString());
    }
}
