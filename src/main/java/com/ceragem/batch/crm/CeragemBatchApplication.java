package com.ceragem.batch.crm;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * 
 * @ClassName	CeragemBatchApplication
 * @author		κΉμ±ν
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	
 * @Company		Copyright β wigo.ai. All Right Reserved
 */
@ComponentScan("com.ceragem")
@EnableBatchProcessing
@SpringBootApplication
//(exclude = { DataSourceAutoConfiguration.class })
public class CeragemBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(CeragemBatchApplication.class, args);
    }

}
