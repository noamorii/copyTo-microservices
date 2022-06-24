package fel.cvut.eureka;

import com.hazelcast.config.Config;
import com.hazelcast.eureka.one.EurekaOneDiscoveryStrategyFactory;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;


@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }

    @Autowired
    EurekaInstanceConfigBean eurekaInstanceConfigBean;

    @Bean
    @Primary
    public Config getHazelCastConfig(@Autowired EurekaClient eurekaClient) {
        EurekaOneDiscoveryStrategyFactory.setEurekaClient(eurekaClient);

        /*Attention - HERE BE DRAGONS - it took me ~8 hours of reading the source code of hazelcast to figure this thing out. Figuring out the ip address was especially tricky.
         The thing was loading it by itself and loaded it (this container's ip) hilariously wrong. Be VERY careful touching this thing. It was utter nightmare.*/
        String ipAddress = eurekaInstanceConfigBean.getHostname();
        Config config = Config.load();
        config.getNetworkConfig().setPublicAddress(ipAddress);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getEurekaConfig()
                .setEnabled(true)
                .setProperty("self-registration", "true")
                .setProperty("namespace", "hazelcast")
                .setProperty("use-metadata-for-host-and-port", "true");
        return config;
    }
}
