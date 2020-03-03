package com.tennetcn.free.web.demo.apis;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-03-03 10:44
 * @comment
 */

@Data
public class TestDateReq {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date testDate;
}
