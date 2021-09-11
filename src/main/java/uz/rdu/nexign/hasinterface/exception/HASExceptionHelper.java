package uz.rdu.nexign.hasinterface.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.rdu.nexign.hasinterface.model.rest.Error;
import uz.rdu.nexign.hasinterface.model.rest.RestResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@ControllerAdvice
public class HASExceptionHelper {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RestResponse handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        AtomicReference<String> message = new AtomicReference<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            message.set(message.get() + fieldName + ":" + errorMessage);
        });
        RestResponse response = new RestResponse();
        Error errorBody = new Error(1,message.get());

        response.setError(errorBody);
        response.setData(errors);
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHasDataFoundException.class)
    @ResponseBody
    public RestResponse handleFlightException(NoHasDataFoundException exception){
        RestResponse response = new RestResponse();
        Error errorBody = new Error(2,exception.getLocalizedMessage());
        response.setError(errorBody);
        return response;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServerException.class)
    @ResponseBody
    public RestResponse handleServerException(ServerException exception){
        RestResponse response = new RestResponse();
        Error errorBody = new Error(3,exception.getLocalizedMessage());
        response.setError(errorBody);
        return response;
    }
}
