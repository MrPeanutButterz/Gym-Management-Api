package com.novi.gymmanagementapi.utilties;

import com.novi.gymmanagementapi.dto.ErrorMessageDto;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FieldErrors {

    public static List<ErrorMessageDto> catchFieldErrors(BindingResult br) {
        List<ErrorMessageDto> errorMessageDtoList = new ArrayList<>();
        for (var i = 0; i < br.getErrorCount(); i++) {
            ErrorMessageDto em = new ErrorMessageDto();
            em.setStatus("BAD_REQUEST");
            em.setField(Objects.requireNonNull(br.getFieldErrors().get(i).getField()));
            em.setMessage(Objects.requireNonNull(br.getFieldErrors().get(i).getDefaultMessage()));
            errorMessageDtoList.add(em);
        }
        return errorMessageDtoList;
    }
}
