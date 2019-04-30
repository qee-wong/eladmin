package me.zhengjie;

import me.zhengjie.utils.SpringContextHolder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.mybatis.spring.annotation.MapperScan;
/**
 * @author jie
 * @date 2018/11/15 9:20:19
 */
//@SpringBootApplication //写在同一个me.zhengjie的包名下可以识别写在其他包名下需手动指向
@SpringBootApplication(scanBasePackages={"com","me.zhengjie"})
@MapperScan("com.*.*.mapper")
@EnableTransactionManagement
@EnableWebSocketMessageBroker
public class AppRun {

    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
