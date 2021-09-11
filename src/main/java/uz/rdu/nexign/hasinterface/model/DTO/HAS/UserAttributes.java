package uz.rdu.nexign.hasinterface.model.DTO.HAS;


import lombok.*;

import javax.xml.bind.annotation.XmlElement;

@NoArgsConstructor
@Getter
@ToString
public class UserAttributes {

    @XmlElement(name = "CLNT_RT_HISTORIES_GET")
    private String clntRtHistoriesGet;

    @XmlElement(name = "GET_OFFLINE_INFO")
    private String getOfflineInfo;

    @XmlElement(name = "CHECK_CUSTOM_ACCESS")
    private CheckCustomAccess checkCustomAccess;

    @XmlElement(name = "SC_CLNT_ATTR_FOR_CONTEXT")
    private ClntAttrForContext clntAttrForContext;

    @XmlElement(name = "ACCOUNT")
    private String account;

    @XmlElement(name = "P_AUTH_MODE")
    private String authMode;

    @XmlElement(name = "P_EXPIRE_DATE")
    private String expireDate;

    @XmlElement(name = "P_LANG_ID")
    private int langId;

    @XmlElement(name = "P_LOGIN")
    private String login;

    @XmlElement(name = "P_SACS_ID")
    private long sacsId;

    @XmlElement(name = "P_STRICT_EXPIRE_DATE")
    private String strictExpireDate;

    @XmlElement(name = "P_SUSR_ID")
    private int sursId;

    @XmlElement(name = "P_SUTP_ID")
    private int sutpId;

    @XmlElement(name = "P_USER_ACUG_ID")
    private int userAcugId;

    @XmlElement(name = "SUBSCRIBER_MSISDN")
    private String msisdn;

}
