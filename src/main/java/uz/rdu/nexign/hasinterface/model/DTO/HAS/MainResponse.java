package uz.rdu.nexign.hasinterface.model.DTO.HAS;

import lombok.*;

import javax.xml.bind.annotation.XmlElement;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public abstract class MainResponse {

    @XmlElement(name = "HOSTNAME")
    private String hostname;

    @XmlElement(name = "CHANNEL")
    private String channel;

    @XmlElement(name = "SESSION_ID")
    private String sessionId;

    @XmlElement(name = "LOGIN")
    private String login;

    @XmlElement(name = "ACCESS_LEVEL_ID")
    private long accessLevelId;

    @XmlElement(name = "LANG_ID")
    private int langId;

    @XmlElement(name = "HAS_GET_USER_ATTRIBUTES")
    private UserAttributes userAttributes;

    @XmlElement(name = "SUBSCRIBER_MSISDN")
    private long subscriberMsisdn;

    @XmlElement(name = "ONLINE_MODE")
    private int onlineMode;

    public MainResponse(MainResponse mainResponse) {
        this.accessLevelId=mainResponse.getAccessLevelId();
        this.channel=mainResponse.getChannel();
        this.hostname=mainResponse.getHostname();
        this.langId=mainResponse.getLangId();
        this.login=mainResponse.getLogin();
        this.onlineMode=mainResponse.getOnlineMode();
        this.sessionId=mainResponse.getSessionId();
        this.subscriberMsisdn=mainResponse.getSubscriberMsisdn();
        this.userAttributes=mainResponse.getUserAttributes();
    }
}
