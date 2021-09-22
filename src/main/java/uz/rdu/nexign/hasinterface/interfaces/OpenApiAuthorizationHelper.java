package uz.rdu.nexign.hasinterface.interfaces;

import uz.rdu.nexign.hasinterface.model.DTO.OAPI.auth.CredentialAuthResponse;

public interface OpenApiAuthorizationHelper {

    CredentialAuthResponse getSessionId();
}
