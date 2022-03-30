package app.logs.generator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class LogEvent {

    public String host;

    public String app;

    public String path;

    public String severity;

    public String message;

    public String timestamp;
}
