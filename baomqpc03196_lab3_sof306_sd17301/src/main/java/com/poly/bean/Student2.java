package com.poly.bean;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student2 {

    @NotBlank(message = "Không để trống email")
    @Email(message = "Không đúng định dạng email")
    String email;

    // NotBlank chỉ áp dụng cho chuỗi
    @NotBlank(message = "Không để trống họ và tên")
    String fullname;

    @NotNull(message = "Không để trống điểm")
    @PositiveOrZero(message = "Điểm phải lớn hơn hoặc bằng 0")
    @Max(value = 10, message = "Điểm phải nhỏ hơn hoặc bằng 10")
    Double marks;

    @NotNull(message = "Chọn giới tính")
    Boolean gender;

    @NotBlank(message = "Chọn quốc tịch")
    String country;

}
