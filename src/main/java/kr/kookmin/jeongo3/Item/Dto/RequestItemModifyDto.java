package kr.kookmin.jeongo3.Item.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RequestItemModifyDto {

    private String itemId;
    private String name;
    private MultipartFile image;
    private int price;
}
