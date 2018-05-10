package com.springboot.base.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-12-13.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PermissionDTO {

    @NotNull
    private Long id;

    private String name;

    private boolean available;

    private Long parentId;

    private String code;

    List<PermissionDTO> children;
}
