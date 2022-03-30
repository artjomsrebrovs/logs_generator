package app.logs.generator.model.random;

import app.logs.generator.model.LogData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties("generator.log.events")
public final class Host implements LogData {

    private List<String> hosts;

    @Override
    public int getSize() {
        return hosts.size();
    }

    @Override
    public String getValue(int index) {
        return hosts.get(index);
    }
}
